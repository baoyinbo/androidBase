/**
 *
 */
package com.gxb.gxbcompanyintegrity.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.gxb.gxbcompanyintegrity.GSApplication;
import com.gxb.gxbcompanyintegrity.permission.PermissionUtils;
import com.gxb.lazynetlibrary.logger.LazyLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/***
 * 设备帮助类
 *
 * @author 江钰锋 0152
 * @version [版本号, 2015年4月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DeviceHelper {

    /**
     * 获取设备 SDK版本号
     */
    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备的IMEI号
     */
    public static String getIMEI() {
        TelephonyManager teleMgr = (TelephonyManager) GSApplication.getApplication()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getDeviceId();
    }

    /**
     * 获取设备的IMSI号
     */
    public static String getIMSI() {
        TelephonyManager teleMgr = (TelephonyManager) GSApplication.getApplication()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getSubscriberId();
    }

    /**
     * 获取网络运营商
     *
     * @return
     */
    public static String getNetworkOperator() {
        TelephonyManager teleMgr = (TelephonyManager) GSApplication.getApplication()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (teleMgr == null)
            return "UNKNOWN";

        String IMSI = teleMgr.getSubscriberId();
        if (TextUtils.isEmpty(IMSI))
            return "UNKNOWN";

        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            return "CMCC";
        } else if (IMSI.startsWith("46001")) {
            return "CUCC";
        } else if (IMSI.startsWith("46003")) {
            return "CTCC";
        } else {
            return "UNKNOWN";
        }
    }

    /**
     * 获取屏幕分辨率
     *
     * @param activity
     * @return
     */
    public static int[] getScreenDispaly(Activity activity) {
        WindowManager winManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = winManager.getDefaultDisplay();
        int result[] = {display.getWidth(), display.getHeight()};
        return result;
    }

    public static String getDeviceInfoForUMeng() {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) GSApplication.getApplication()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            WifiManager wifi = (WifiManager) GSApplication.getApplication()
                    .getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(
                        GSApplication.getApplication().getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备号 wifi mac + imei + cpu serial
     *
     * @return 设备号
     */
    public static String getMobileUUID() {
        String uuid = "";
        // 先获取mac
        WifiManager wifiMgr = (WifiManager) GSApplication.getApplication().getSystemService(Context.WIFI_SERVICE);
        /* 获取mac地址 */
        if (wifiMgr != null) {
            WifiInfo info = wifiMgr.getConnectionInfo();
            if (info != null && info.getMacAddress() != null) {
                uuid = info.getMacAddress().replace(":", "");
            }
        }
        if (PermissionUtils.checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            // 再加上imei
            TelephonyManager teleMgr = (TelephonyManager) GSApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            String imei = teleMgr.getDeviceId();
            uuid += imei;
        }
        // 最后再加上cpu
        String str = "", strCPU = "", cpuAddress = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process pp = cmd.start();
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("Serial") > -1) {
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        uuid += cpuAddress;

        // 如果三个加在一起超过64位的话就截取
        if (uuid != null && uuid.length() > 64) {
            uuid = uuid.substring(0, 64);
        }
        return uuid;
    }

    public static String getLocalMacAddressFromIp() {
        String mac_s = "";
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getNetworkIP()));
            mac = ne.getHardwareAddress();
            mac_s = StringUtils.byte2hex(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mac_s;
    }


    /**
     * 获取渠道名
     *
     * @return
     */
    public static String getAppChannelName() {
        String channel = "";
        try {
            ApplicationInfo info = GSApplication.getApplication().getPackageManager()
                    .getApplicationInfo(GSApplication.getApplication().getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                channel = info.metaData.getString("UMENG_CHANNEL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 获取app版本名称
     *
     * @return
     */
    public static String getAppVersion() {
        String version = "";
        PackageManager packageManager = GSApplication.getApplication().getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(GSApplication.getApplication().getPackageName(), 0);
            version = info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 获取app版本code
     *
     * @return
     */
    public static int getAppVersionCode() {
        int version = 0;
        PackageManager packageManager = GSApplication.getApplication().getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(GSApplication.getApplication().getPackageName(), 0);
            version = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 获取ip
     *
     * @return
     */
    public static String getNetworkIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LazyLogger.e(ex, "WifiPreference IpAddress");
        }
        return null;
    }

    /***
     * 获取网络类型
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getNetworkType() {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) GSApplication.getApplication().
                getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取设备id
     */
//    public static String getDeviceId() {
//        return JPushInterface.getRegistrSationID(GSApplication.getApplication());
//    }
}

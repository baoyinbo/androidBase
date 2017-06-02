/**
 * 文 件 名:  WJApplication.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/5
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import com.gxb.gxbcompanyintegrity.config.GSAppConfing;
import com.gxb.gxbcompanyintegrity.config.GSUrlDefines;
import com.gxb.gxbcompanyintegrity.model.GSUserInfoModel;
import com.gxb.gxbcompanyintegrity.permission.PermissionUtils;
import com.gxb.gxbcompanyintegrity.receiver.UpdatePageBroadcastReceiver;
import com.gxb.gxbcompanyintegrity.ui.base.GSActivityStackManager;
import com.gxb.gxbcompanyintegrity.ui.debug.GSDebugActivity;
import com.gxb.gxbcompanyintegrity.util.AppUtils;
import com.gxb.gxbcompanyintegrity.util.GSPreferenceConfig;
import com.gxb.gxbcompanyintegritylibrary.LazyApplication;
import com.gxb.lazynetlibrary.logger.AndroidLogTool;
import com.gxb.lazynetlibrary.logger.LazyLogger;
import com.gxb.lazynetlibrary.logger.Log4JTool;
import com.gxb.lazynetlibrary.logger.LogLevel;
import com.gxb.lazynetlibrary.logger.LogTool;
import com.gxb.lazynetlibrary.logger.PrinterType;
import com.gxb.lazynetlibrary.net.http.HttpRequestManager;
import com.gxb.lazynetlibrary.net.state.NetworkStateReceiver;
import com.gxb.lazynetlibrary.utils.StringUtils;
import com.gxb.lazynetlibrary.utils.config.DataConfig;
import com.karumi.dexter.Dexter;
import org.apache.log4j.Level;


/**
 * Application
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GSApplication extends LazyApplication {
    private static GSApplication application;
    /**
     * activity部分栈管理
     */
    private GSActivityStackManager ssManager;
    
//    private LockPatternUtils mLockPatternUtils;
    /**
     * 配置器
     */
    private DataConfig mCurrentConfig;
    /**
     * 用户信息
     */
    private GSUserInfoModel userinfoModel;
    /**
     * 网络数据请求管理
     */
    private HttpRequestManager httpDataManager;

    @Override
    public void onCreate() {
        application = this;
        String processName = AppUtils.getProcessName(this,
                android.os.Process.myPid());
        if (!StringUtils.isEmpty(processName)) {
            boolean defaultProcess = processName
                    .equals(AppUtils.getAppPackageName(this));
            if (defaultProcess) {
                //必要的初始化资源操作
                super.onCreate();
                initApp();
            }
        }
    }

    public Activity getTopActivity() {
        if (ssManager != null) return ssManager.topActivity();
        return null;
    }


    /**
     * 初始化app的一些配置
     */
    private void initApp() {
        Dexter.initialize(this);
        NetworkStateReceiver.checkNetworkState(this);

//        MobclickAgent.setDebugMode(WJAppConfing.IS_DEBUG);//友盟
//        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式
        GSUrlDefines.initUrl();//初始化服务器url
//        initJGPush();
//        PlatformConfig.setWeixin("wx1abcf2970f0bfbbf", "5e790fbc69da6d1421e4fcfbbbe9988e");
//        PlatformConfig.setSinaWeibo("777596268", "b89d73f5551af923382e1bf18abc0955");
//        PlatformConfig.setQQZone("1105055538", "MfemXZDQYcJXGhau");
    }

    /**
     * 获取Application
     *
     * @return
     */
    public static GSApplication getApplication() {
        return application;
    }

    /**
     * 初始化极光推送
     */
//    private void initJGPush() {
//        JPushInterface.setDebugMode(WJAppConfing.IS_DEBUG);
//        JPushInterface.init(this);
//        //设置通知保留条数为3条
//        JPushInterface.setLatestNotificationNumber(this, 3);
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
//        String model = android.os.Build.MODEL+"";
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && model.startsWith("ZTE")){
//            builder.statusBarDrawable = R.mipmap.jpush_notification_icon;
//         } else {
//            builder.statusBarDrawable = R.mipmap.ic_launcher;
//         }
//
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
//        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
//        JPushInterface.setDefaultPushNotificationBuilder(builder);
//
//    }

    /***
     * 初始化日志系统
     *
     * @throws
     * @see [类、类#方法、类#成员]
     */
    @Override
    protected void initLogger() {
        LogLevel logLevel = LogLevel.OFF;
        LogTool logTool = new AndroidLogTool();
        if (GSAppConfing.IS_DEBUG) {
            logLevel = LogLevel.ALL;
            if (PermissionUtils.checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && PermissionUtils.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                logTool = new Log4JTool(Level.ALL, "GxbShare/log", "xxdclog");
            }
        }
        LazyLogger.init(/* PrinterType.FORMATTED */PrinterType.ORDINARY) // 打印类型
                .methodCount(3) // default 2
                .hideThreadInfo() // default shown
                .logLevel(logLevel) // default LogLevel.ALL(设置全局日志等级)
                .methodOffset(2) // default 0
                .logTool(logTool); // Log4j中的Level与本框架的LogLevel是分开设置的(Level只用来设置log4j的日志等级)
    }

    @Override
    protected void registerUncaughtExceptionHandler() {
        if (GSAppConfing.IS_DEBUG) {
            super.registerUncaughtExceptionHandler();
        }
    }

    @Override
    protected void initCache() {
        if (GSAppConfing.IS_DEBUG) {
            super.initCache();
        }
    }

    @Override
    public synchronized GSActivityStackManager getActivityStackManager() {
        if (ssManager == null) {
            ssManager = new GSActivityStackManager();
        }
        return ssManager;
    }

    /**
     * 获取图形密码加密工具
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
//    public LockPatternUtils getLockPatternUtils() {
//        if (mLockPatternUtils == null) {
//            mLockPatternUtils = new LockPatternUtils(this);
//        }
//        return mLockPatternUtils;
//    }

    /**
     * 获取数据请求管理者
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
//    @Override
//    public synchronized HttpRequestManager getHttpDataManager() {
//        if (httpDataManager == null) {
//            httpDataManager = LazyHttpRequestManager.getInstance(this);
//        }
//        return httpDataManager;
//    }

    /***
     * 获取用户信息
     *
     * @return
     */
    public GSUserInfoModel getUserinfoModel() {
        return userinfoModel;
    }

    /**
     * 设置用户信息
     *
     * @param userinfoModel
     */
    public void setUserinfoModel(GSUserInfoModel userinfoModel) {
        this.userinfoModel = userinfoModel;
    }

    /**
     * 根据类型得到对应的配置器
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public DataConfig getPreferenceConfig() {
        if (mCurrentConfig == null) {
            mCurrentConfig = GSPreferenceConfig.getPreferenceConfig(this);
            if (!mCurrentConfig.isLoadConfig()) {
                mCurrentConfig.loadConfig();
            }
        }
        return mCurrentConfig;
    }

    @Override
    public void exitApp(Boolean isBackground) {
        if (GSDebugActivity.floatbtnDebug != null) {
            WindowManager wm = (WindowManager) getApplicationContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            wm.removeViewImmediate(GSDebugActivity.floatbtnDebug);
            GSDebugActivity.floatbtnDebug = null;
        }
        UpdatePageBroadcastReceiver.unregisterPageRefreshBroadcast(this);
        UpdatePageBroadcastReceiver.close();

//        if (!isBackground) {
//            MobclickAgent.onKillProcess(this);
//        }
//        if (mLockPatternUtils != null) {
//            mLockPatternUtils = null;
//        }

        if (mCurrentConfig != null) {
            mCurrentConfig.close();
            mCurrentConfig = null;
        }
        if (ssManager != null) {
            ssManager.appExit(this, isBackground);
            ssManager = null;
        }
        super.exitApp(isBackground);
    }
}

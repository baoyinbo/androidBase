/**
 * 文 件 名:  WJAppConfing.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.config;

import com.gxb.gxbcompanyintegrity.BuildConfig;
import com.gxb.gxbcompanyintegrity.GSApplication;

/**
 * app的一些配置
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GSAppConfing {
    /***
     * app运行模式,是否debug模式
     */
    public final static boolean IS_DEBUG = BuildConfig.DEBUG;

    /**ApplicationId*/
    public final static String APPLICATION_ID = BuildConfig.APPLICATION_ID;

//    /**build时间*/
//    public final static String BUILD_TIME = BuildConfig.BUILD_TIME;
//
//    /**当前打包的主机名称*/
    public final static String HOST_NAME=BuildConfig.HOST_NAME;
//

//    /**服务器API地址*/
    public final static String BASE_API_URL=BuildConfig.BASE_API_URL;
//
//    /**服务器h5地址*/
    public final static String BASE_H5_URL=BuildConfig.BASE_H5_URL;

    /**
     * 服务端和app端约定好的密匙
     */
    public final static String XXTEA_KET = "JXXDCqudG8uCeae4JcMTl2en9zv7xvFa";//J58l2qudG8uCeae4JcWBl2en9zv7xvFa

    /**
     * 客户端授权编号
     */
    public final static String CLIENT_ID = "AA89D6D64FB94F79B4A8060165A41A51";

    /***
     * 是否有显示过引导
     *
     * @return
     */
    public static boolean isShowGuideDone() {
        return GSApplication.getApplication().getPreferenceConfig().getBoolean("isShowGuide", false);
    }

    /***
     * 保存
     */
    public static void saveShowGuide() {
        GSApplication.getApplication().getPreferenceConfig().setBoolean("isShowGuide", true);
    }

//    /**
//     * 是否存在手势密码
//     *
//     * @return
//     */
//    public static boolean isExistsGestureLock() {
//        return GSApplication.getApplication().getLockPatternUtils().savedPatternExists();
//    }
//
//    /**
//     * 删除手势密码
//     */
//    public static void deleteGestureLock(){
//        GSApplication.getApplication().getLockPatternUtils().clearLock();
//    }

//    /**
//     * 是否打开手势设置对话框
//     * 更换设备、升级时登录打开 手势设置对话框
//     */
//    public static boolean isOpenGestureDialog() {
//        return GSApplication.getApplication().getPreferenceConfig().getBoolean(WJUserInfoConfig.getUserinfo().getUserId(), true);
//    }
//
//    /**
//     * 保存
//     */
//    public static void saveOpenGestrueDialog() {
//        GSApplication.getApplication().getPreferenceConfig().setBoolean(WJUserInfoConfig.getUserinfo().getUserId(), false);
//    }

    /**
     * 保存版本
     */
    public static void saveVersion(String version) {
        GSApplication.getApplication().getPreferenceConfig().setString("version", version);
    }

    /**
     * 获取版本号
     * @return
     */
    public static String getVersion() {
        return GSApplication.getApplication().getPreferenceConfig().getString("version", "");
    }
}

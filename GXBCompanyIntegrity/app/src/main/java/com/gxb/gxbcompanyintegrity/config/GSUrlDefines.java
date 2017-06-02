package com.gxb.gxbcompanyintegrity.config;

import android.text.TextUtils;

import com.gxb.gxbcompanyintegrity.GSApplication;


public class GSUrlDefines {
    //	public static String BASE_API_URL_RELEASE = "http://172.16.4.180:8080/";
//	public static String BASE_API_URL_RELEASE = "http://172.16.4.194:8080/";
//    public static String BASE_API_URL_RELEASE = "http://172.16.4.211:8080/";
    public static String BASE_API_URL_RELEASE = GSAppConfing.BASE_API_URL/*"http://app.matanfax.com/"*/;
    //    public static String BASE_API_URL_RELEASE = "http://172.16.1.12:8080/";
//    public static String BASE_API_URL_RELEASE = "http://172.16.1.13:8080/";
    public static String BASE_H5_URL_RELEASE = GSAppConfing.BASE_H5_URL/*"http://h5.matanfax.com/"*/;


    /**
     * 初始化url
     */
    public static void initUrl() {
        if (!GSAppConfing.IS_DEBUG) {
            return;
        }
        String BASE_API_URL = GSApplication.getApplication().getPreferenceConfig().getString("KEY_DEBUG_RUL_ADDRESS", BASE_API_URL_RELEASE);
        if (BASE_API_URL != null && !TextUtils.isEmpty(BASE_API_URL)) {
            BASE_API_URL_RELEASE = BASE_API_URL;
        }
        String BASE_API_H5 = GSApplication.getApplication().getPreferenceConfig().getString("KEY_DEBUG_H5_ADDRESS", BASE_H5_URL_RELEASE);
        if (BASE_API_H5 != null && !TextUtils.isEmpty(BASE_API_H5)) {
            BASE_H5_URL_RELEASE = BASE_API_H5;
        }
    }
}

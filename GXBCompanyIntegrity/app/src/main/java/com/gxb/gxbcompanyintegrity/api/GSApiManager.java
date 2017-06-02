package com.gxb.gxbcompanyintegrity.api;


import com.gxb.gxbcompanyintegrity.GSApplication;
import com.gxb.gxbcompanyintegrity.R;
import com.gxb.gxbcompanyintegrity.model.GSTestModel;
import com.gxb.gxbcompanyintegrity.net.WJRequestParam;
import com.gxb.gxbcompanyintegrity.net.WJResponseListener;
import com.gxb.gxbcompanyintegrity.net.WrapResponseListener;
import com.gxb.gxbcompanyintegrity.net.http.cache.WJCacheResponseListener;
import com.gxb.gxbcompanyintegrity.net.http.cache.WrapCacheResponseListener;
import com.gxb.gxbcompanyintegrity.ui.common.DefaultLoadingView;
import com.gxb.gxbcompanyintegrity.ui.common.GSDefaultLoadingView;
import com.gxb.gxbcompanyintegrity.ui.common.GSLoadingView;
import com.gxb.gxbcompanyintegrity.util.DeviceHelper;
import com.gxb.lazynetlibrary.net.http.HttpRequestManager;
import com.gxb.lazynetlibrary.net.http.LoadingViewInterface;
import com.gxb.lazynetlibrary.net.http.RequestLifecycleContext;
import com.gxb.lazynetlibrary.net.http.cache.HttpCacheLoadType;

import java.util.HashMap;
import java.util.Map;


/**
 * api请求管理
 * Created by baoyb on 2017/5/25.
 */

public class GSApiManager {
    //缓存大小
    private static final long maxCacheAge = 60 * 24 * 7L;
    private static GSApiManager apiManager = new GSApiManager();
    /***
     * 请求管理者
     */
    private HttpRequestManager requestManager;
    /***
     * 请求头,公共部分,所有接口都需要传的字段(其实也是放在请求体中)
     */
    private Map<String, String> headers;

    public static GSApiManager instance() {
        return apiManager;
    }

    private GSApiManager() {
        requestManager = GSApplication.getApplication().getHttpDataManager();
        headers = new HashMap<String, String>();
        headers.putAll(initHeader());
    }

    /***
     * 初始化请求头
     *
     * @return
     */
    private Map initHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("appversion", DeviceHelper.getAppVersion());//应用程序版本号
//        map.put("osVersion", DeviceHelper.getOSVersion());//系统版本号
        map.put("devicetype", "ANDROID");//客户端类型(IOS，ANDROID)
        map.put("channelId", "GXB");//客户端渠道(不同的渠道传不同的ID)DeviceHelper.getAppChannelName()
//        map.put("termId", DeviceHelper.getMobileUUID());//客户终端号
//        map.put("deviceId", DeviceHelper.getDeviceId());//推送终端号(登录后必传)
        return map;
    }

    /**
     * 得到公共参数
     *
     * @return
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * 无缓存例子
     * @param lifecycleContext
     * @param mblNo            手机号
     * @param clientId         客户端授权编号
     * @param listener
     */
    public void test(RequestLifecycleContext lifecycleContext, String mblNo,
                     String clientId, WJResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_lizi, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
        param.addSendData("mblNo", mblNo);
        param.addSendData("clientId", clientId);
        requestManager.sendHttpPostRequest(lifecycleContext,
                param,
                new DefaultLoadingView(lifecycleContext.getCurrContext()),
                new WrapResponseListener<GSTestModel>(listener) {
                });
    }

    /**
     * 有缓存例子
     *
     * @param lifecycleContext
     * @param loadingView
     * @param listener
     */
    public void getIndexMsg(RequestLifecycleContext lifecycleContext, GSDefaultLoadingView loadingView,
                            WJCacheResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_lizi, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
        HttpCacheLoadType loadType = HttpCacheLoadType.NOT_USE_CACHE_UPDATE_CACHE;
        LoadingViewInterface loadingViewInterface = null;
        if (loadingView != null) {
            loadType = HttpCacheLoadType.USE_CACHE_AND_NET_UPDATE_CHCHE;
            loadingViewInterface = new GSLoadingView(loadingView);
        }
        requestManager.sendCacheHttpPostRequest(lifecycleContext,
                param,
                loadingViewInterface,
                new WrapCacheResponseListener<GSTestModel>(listener) {
                }, loadType, maxCacheAge);
    }

    /**
     * 登录
     * @param lifecycleContext
     * @param mobile
     * @param code
     * @param listener
     */
    public void login(RequestLifecycleContext lifecycleContext, String mobile,
                      String code, WJResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_login, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
//        param.addSendData("mobile", mobile);
//        param.addSendData("code", code);
        param.addSendData("username", "111111");
        requestManager.sendHttpPostRequest(lifecycleContext,
                param,
                new DefaultLoadingView(lifecycleContext.getCurrContext()),
                new WrapResponseListener<GSTestModel>(listener) {
                });
    }
}

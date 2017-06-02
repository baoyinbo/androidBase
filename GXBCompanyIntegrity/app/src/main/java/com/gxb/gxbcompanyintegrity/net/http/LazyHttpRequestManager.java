/**
 * 文 件 名:  LazyHttpRequestManager.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net.http;

import android.content.Context;


import com.gxb.gxbcompanyintegrity.net.WJRequestParam;
import com.gxb.gxbcompanyintegrity.net.http.cache.LazyCacheAsyncJsonResponseCallback;
import com.gxb.lazynetlibrary.net.http.HttpRequestManager;
import com.gxb.lazynetlibrary.net.http.LoadingViewInterface;
import com.gxb.lazynetlibrary.net.http.RequestLifecycleContext;
import com.gxb.lazynetlibrary.net.http.ResponseListener;
import com.gxb.lazynetlibrary.net.http.cache.CacheResponseListener;
import com.gxb.lazynetlibrary.net.http.cache.HttpCacheLoadType;
import com.gxb.lazynetlibrary.net.http.core.HttpRequestMethod;
import com.gxb.lazynetlibrary.net.http.core.RequestParam;
import com.gxb.lazynetlibrary.net.state.NetWorkUtil;

import java.io.Serializable;

/**
 * @author 江钰锋 00501
 * @version [版本号, 16/7/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LazyHttpRequestManager extends HttpRequestManager {
    /***
     * 单例
     */
    private static LazyHttpRequestManager adrManager;

    private LazyHttpRequestManager(Context context) {
        super(context);
    }

    public static LazyHttpRequestManager getInstance(Context context) {
        if (adrManager == null) {
            synchronized (HttpRequestManager.class) {
                if (adrManager == null) {
                    adrManager = new LazyHttpRequestManager(context);
                    adrManager.setThreadCorePoolSize(2);
                    adrManager.setThreadMaxPoolSize(4);
                }
            }
        }
        return adrManager;
    }

    @Override
    public <T extends Serializable, E extends Serializable> void sendHttpPostRequest(
            RequestLifecycleContext requestContext,
            RequestParam requestParam,
            LoadingViewInterface loadingView, ResponseListener<T, E> listener) {
        boolean isSuccess = doPost(requestParam, new LazyAsyncJsonResponseCallback<T, E>(listener, loadingView));
        if (isSuccess) {
            super.addContextRequest(requestContext, requestParam.getMessageId());
        }
    }

    @Override
    protected <T extends Serializable, E extends Serializable> boolean doCacheJSONHttpRequest(
            HttpRequestMethod httpMethod, RequestLifecycleContext requestContext,
            RequestParam requestParam, LoadingViewInterface loadingView, CacheResponseListener<T, E> listener, HttpCacheLoadType cacheLoadType,
            long maxCacheAge) {
        boolean isSuccess = false;
        LazyCacheAsyncJsonResponseCallback<T, E> responseHandler = null;
        if (cacheLoadType != null) {
            if (maxCacheAge > 0) {
                responseHandler = new LazyCacheAsyncJsonResponseCallback<T, E>(listener,
                        loadingView, getHttpCacheLoaderManager(),
                        cacheLoadType, maxCacheAge);
            } else {
                responseHandler = new LazyCacheAsyncJsonResponseCallback<T, E>(listener,
                        loadingView, getHttpCacheLoaderManager(),
                        cacheLoadType);
            }
        } else {
            if (maxCacheAge > 0) {
                responseHandler = new LazyCacheAsyncJsonResponseCallback<T, E>(listener,
                        loadingView, getHttpCacheLoaderManager(),
                        HttpCacheLoadType.USE_CACHE_UPDATE_CACHE, maxCacheAge);
            } else {
                responseHandler = new LazyCacheAsyncJsonResponseCallback<T, E>(listener,
                        loadingView, getHttpCacheLoaderManager(),
                        HttpCacheLoadType.USE_CACHE_UPDATE_CACHE);
            }
        }
        if (requestParam instanceof WJRequestParam) {
            responseHandler.setIsUseEncrypt(((WJRequestParam) requestParam).isUseEncrypt());
        }
        if (httpMethod == HttpRequestMethod.HTTP_GET) {
            isSuccess = doGet(requestParam, responseHandler);
        } else if (httpMethod == HttpRequestMethod.HTTP_POST) {
            isSuccess = doPost(requestParam, responseHandler);
        }
        return isSuccess;
    }

    @Override
    protected void addContextRequest(RequestLifecycleContext requestContext, int messageId) {
        if (requestContext == null) return;
        super.addContextRequest(requestContext, messageId);
    }

    @Override
    public void cancelConetxtRequest(RequestLifecycleContext requestContext) {
        if(requestContext==null)return;
        super.cancelConetxtRequest(requestContext);
    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {
//        super.onConnect(type);
    }

    @Override
    public void close(boolean isNow) {
        super.close(isNow);
        adrManager = null;
    }
}

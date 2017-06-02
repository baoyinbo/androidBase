package com.gxb.gxbcompanyintegrity.net.http.cache;

import com.gxb.lazynetlibrary.net.http.LoadingViewInterface;
import com.gxb.lazynetlibrary.net.http.cache.CacheResponseListener;
import com.gxb.lazynetlibrary.net.http.cache.HttpCacheLoadType;
import com.gxb.lazynetlibrary.net.http.cache.HttpCacheLoaderManager;
import com.gxb.lazynetlibrary.net.http.cache.callback.AsyncCacheJsonResponseCallback;
import com.gxb.lazynetlibrary.net.http.core.HttpResponseHandler;

import java.io.Serializable;

/**
 * 异步的json请求回调
 *
 * @param <T>
 * @param <E>
 * @author 江钰锋
 * @version [版本号, 2015年6月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LazyCacheAsyncJsonResponseCallback<T extends Serializable, E extends Serializable>
        extends
        AsyncCacheJsonResponseCallback<T, E> {
    /**
     * byte数组类型缓存加载管理者
     */
    private HttpCacheLoaderManager httpCacheLoader;
    private HttpCacheLoadType httpCacheLoadType;
    /**
     * 缓存最大的有效时间
     */
    private long maxCacheAge;

    /**
     * 是否使用加密
     */
    private boolean isUseEncrypt;

    public LazyCacheAsyncJsonResponseCallback(CacheResponseListener<T, E> listener, LoadingViewInterface loadingView, HttpCacheLoaderManager httpCacheLoader, HttpCacheLoadType httpCacheLoadType, long maxCacheAge) {
        super(listener, loadingView, httpCacheLoader, httpCacheLoadType, maxCacheAge);
        this.httpCacheLoader = httpCacheLoader;
        this.httpCacheLoadType = httpCacheLoadType;
        this.maxCacheAge = maxCacheAge;
    }

    public LazyCacheAsyncJsonResponseCallback(CacheResponseListener<T, E> listener, LoadingViewInterface loadingView, HttpCacheLoaderManager httpCacheLoader, HttpCacheLoadType httpCacheLoadType) {
        super(listener, loadingView, httpCacheLoader, httpCacheLoadType);
        this.httpCacheLoader = httpCacheLoader;
        this.httpCacheLoadType = httpCacheLoadType;
    }

    @Override
    public HttpResponseHandler getHttpResponseHandler() {
        LazyCacheJSONHttpResponseHandler httpResponseHandler = null;
        if (maxCacheAge > 0) {
            httpResponseHandler = new LazyCacheJSONHttpResponseHandler<T, E>(this,
                    httpCacheLoader, httpCacheLoadType, maxCacheAge);
        }else{
            httpResponseHandler = new LazyCacheJSONHttpResponseHandler<T, E>(this,
                    httpCacheLoader, httpCacheLoadType);
        }
        httpResponseHandler.setIsUseEncrypt(isUseEncrypt);
        return httpResponseHandler;
    }

    /**
     * 设置是否使用加密
     * @param isUseEncrypt
     */
    public void setIsUseEncrypt(boolean isUseEncrypt) {
        this.isUseEncrypt = isUseEncrypt;
    }
}

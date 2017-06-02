package com.gxb.gxbcompanyintegrity.net.http;
import com.gxb.lazynetlibrary.net.http.AsyncJsonResponseCallback;
import com.gxb.lazynetlibrary.net.http.LoadingViewInterface;
import com.gxb.lazynetlibrary.net.http.ResponseListener;
import com.gxb.lazynetlibrary.net.http.core.HttpResponseHandler;

import java.io.Serializable;

/**
 * 异步的json请求回调
 *
 * @param <T> 请求回调的数据的实际类型
 * @param <E> 请求错误的回调数据类型
 * @author 江钰锋
 * @version [版本号, 2015年6月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LazyAsyncJsonResponseCallback<T extends Serializable, E extends Serializable>
        extends AsyncJsonResponseCallback<T, E> {
    public LazyAsyncJsonResponseCallback(ResponseListener<T, E> listener) {
        super(listener);
    }

    public LazyAsyncJsonResponseCallback(ResponseListener<T, E> listener, LoadingViewInterface loadingView) {
        super(listener, loadingView);
    }

    @Override
    public HttpResponseHandler getHttpResponseHandler() {
        return new LazyJSONHttpResponseHandler<T,E>(this);
    }
}

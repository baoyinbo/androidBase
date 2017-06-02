package com.gxb.gxbcompanyintegrity.net.http.cache;


import com.gxb.gxbcompanyintegrity.config.GSAppConfing;
import com.gxb.gxbcompanyintegrity.util.XXTEA;
import com.gxb.lazynetlibrary.net.http.cache.CacheJSONHttpResponseHandler;
import com.gxb.lazynetlibrary.net.http.cache.HttpCacheLoadType;
import com.gxb.lazynetlibrary.net.http.cache.HttpCacheLoaderManager;
import com.gxb.lazynetlibrary.net.http.cache.callback.CacheJSONResponseCallback;
import com.gxb.lazynetlibrary.net.http.core.HttpError;
import com.gxb.lazynetlibrary.net.http.core.RequestParam;
import com.gxb.lazynetlibrary.net.state.NetworkStateReceiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * 带缓存功能的http网络请求文本数据管理者(返回的数据类型为json和发送类型都为json)
 *
 * @author 江钰锋
 * @version [版本号, 2015年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LazyCacheJSONHttpResponseHandler<T extends Serializable, E extends Serializable>
        extends
        CacheJSONHttpResponseHandler<T, E> {

    /**
     * 是否使用加密
     */
    private boolean isUseEncrypt;

    public LazyCacheJSONHttpResponseHandler(CacheJSONResponseCallback<T, E> responseCallback, HttpCacheLoaderManager httpCacheLoader, HttpCacheLoadType httpCacheLoadType, long maxCacheAge) {
        super(responseCallback, httpCacheLoader, httpCacheLoadType, maxCacheAge);
    }

    public LazyCacheJSONHttpResponseHandler(CacheJSONResponseCallback<T, E> responseCallback, HttpCacheLoaderManager httpCacheLoader, HttpCacheLoadType httpCacheLoadType) {
        super(responseCallback, httpCacheLoader, httpCacheLoadType);
    }

    @Override
    protected void sendhttpRequest(HttpURLConnection urlConnection, RequestParam request) {
        super.sendhttpRequest(urlConnection, request);
    }

    @Override
    public String getResponseString(byte[] stringBytes, String charset) {
        String response = super.getResponseString(stringBytes, charset);
        if (isUseEncrypt) {
            try {
                response = new String(XXTEA.decryptWithBase64(response.getBytes(), GSAppConfing.XXTEA_KET.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public void sendFailMessage(int messageId, int statusCode, Map<String, List<String>> headers, byte[] responseErrorByteData) {
        if ((statusCode == HttpError.CONNECT_ERROR || statusCode == HttpError.DNS_PARSE_ERROR)
                && !NetworkStateReceiver.isNetwork()) {
            statusCode = HttpError.NOT_NETWORK;
        }
        super.sendFailMessage(messageId, statusCode, headers, responseErrorByteData);
    }

    /**
     * 设置是否使用加密
     * @param isUseEncrypt
     */
    public void setIsUseEncrypt(boolean isUseEncrypt) {
        this.isUseEncrypt = isUseEncrypt;
    }

    @Override
    public void clean() {
        super.clean();
    }

}

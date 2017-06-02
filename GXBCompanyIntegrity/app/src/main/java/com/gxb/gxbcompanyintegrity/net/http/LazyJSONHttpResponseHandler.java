/**
 * 文 件 名:  LazyJSONHttpResponseHandler.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net.http;

import com.gxb.gxbcompanyintegrity.config.GSAppConfing;
import com.gxb.gxbcompanyintegrity.net.WJRequestParam;
import com.gxb.gxbcompanyintegrity.util.XXTEA;
import com.gxb.lazynetlibrary.net.http.core.HttpError;
import com.gxb.lazynetlibrary.net.http.core.JSONHttpResponseHandler;
import com.gxb.lazynetlibrary.net.http.core.RequestParam;
import com.gxb.lazynetlibrary.net.http.core.callback.JSONResponseCallback;
import com.gxb.lazynetlibrary.net.state.NetworkStateReceiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * http网络请求文本数据管理者(返回的数据类型为json和发送类型都为json)
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LazyJSONHttpResponseHandler<T extends Serializable, E extends Serializable> extends JSONHttpResponseHandler<T, E> {
    /**
     * 是否使用加密
     */
    private boolean isUseEncrypt;

    public LazyJSONHttpResponseHandler(JSONResponseCallback<T, E> responseCallback) {
        super(responseCallback);
    }

    @Override
    protected void sendhttpRequest(HttpURLConnection urlConnection, RequestParam request) {
        if (request instanceof WJRequestParam) {
            isUseEncrypt = ((WJRequestParam) request).isUseEncrypt();
        }
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
}

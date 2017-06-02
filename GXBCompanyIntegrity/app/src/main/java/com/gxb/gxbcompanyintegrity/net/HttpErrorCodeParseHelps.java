/**
 * 文 件 名:  HttpErrorCodeParseHelps.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/8/19
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net;


import com.gxb.lazynetlibrary.net.http.core.HttpError;

/**
 * http请求错误码转换类
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/8/19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HttpErrorCodeParseHelps {

    /**
     * 通过错误码错误信息
     *
     * @param mErrorCode 状态码
     * @return
     */
    public static String getMessageByStatusCode(int mErrorCode)
    {
        switch (mErrorCode) {
            case HttpError.NOT_NETWORK:
                return "星主，网络不给力呀～";
            case HttpError.CONNECT_ERROR:
                return "星主，网络不给力呀～";
            case HttpError.CONNECT_TIME_OUT:
                return "服务器开了个小差～";
            case HttpError.READ_TIME_OUT:
                return "服务器开了个小差～";
            case HttpError.UNKNOW_HTTP_ERROR:
                return "未知错误";
            case HttpError.USER_CANCEL:
                return "用户取消了请求";
            case HttpError.URL_ERROR:
                return "服务器开了个小差～";
            case HttpError.DNS_PARSE_ERROR:
                return "服务器开了个小差～";
            case HttpError.PROTOCOL_EXCEPTION:
                return  "服务器开了个小差～";
            case HttpError.SECURITY_ERROR:
                return "服务器开了个小差～";
            case HttpError.BIND_ERROR:
                return "服务器开了个小差～";
            case HttpError.UNKNOW_SERVICE_ERROR:
                return "服务器开了个小差～";
            case HttpError.FILE_NOT_FOUND_EXCEPTION:
                return "服务器开了个小差～";
            case HttpError.FIEL_EXIST:
                return  "文件已存在";
            case HttpError.UPLOAD_FIEL_NOT_EXIST:
                return  "需要上传的文件不存在";
            case HttpError.SSL_EXCEPTION:
                return  "SSL错误";
            case HttpError.DATA_CONVERT_EXCEPTION:
                return  "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_200:
                return  "(成功)服务器已成功处理了请求";
            case HttpError.RESPONSE_CODE_201:
                return  "(已创建)请求成功并且服务器创建了新的资源";
            case HttpError.RESPONSE_CODE_202:
                return  "(已接受)服务器已接受请求，但尚未处理";
            case HttpError.RESPONSE_CODE_203:
                return  "(非授权信息)服务器已成功处理了请求，但返回的信息可能来自另一来源";
            case HttpError.RESPONSE_CODE_204:
                return  "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_205:
                return  "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_206:
                return  "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_300:
                return "(多种选择)针对请求，服务器可执行多种操作";
            case HttpError.RESPONSE_CODE_301:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_302:
                return "(临时移动)服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来响应以后的请求";
            case HttpError.RESPONSE_CODE_303:
                return "(查看其他位置)请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码";
            case HttpError.RESPONSE_CODE_304:
                return "(未修改)自从上次请求后，请求的网页未修改过";
            case HttpError.RESPONSE_CODE_305:
                return "(使用代理)请求者只能使用代理访问请求的网页";
            case HttpError.RESPONSE_CODE_306:
                return "(未使用)";
            case HttpError.RESPONSE_CODE_307:
                return "(临时重定向)服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来响应以后的请求";
            case HttpError.RESPONSE_CODE_400:
                return "(错误请求)服务器不理解请求的语法";
            case HttpError.RESPONSE_CODE_401:
                return "(未授权)请求要求身份验证";
            case HttpError.RESPONSE_CODE_402:
                return "需要付款,表示计费系统已有效";
            case HttpError.RESPONSE_CODE_403:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_404:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_405:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_406:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_407:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_408:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_409:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_410:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_411:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_412:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_413:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_414:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_415:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_416:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_417:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_500:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_501:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_502:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_503:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_504:
                return "服务器开了个小差～";
            case HttpError.RESPONSE_CODE_505:
                return "服务器开了个小差～";
            default:
                break;
        }
        return "未知错误";
    }
}

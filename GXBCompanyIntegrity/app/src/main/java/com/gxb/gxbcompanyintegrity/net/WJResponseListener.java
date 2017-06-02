/**
 * 文 件 名:  WJResponseListener.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net;


import com.gxb.gxbcompanyintegrity.model.BaseResponseModel;

/**
 * 反馈监听
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface WJResponseListener {
    /**
     * 请求数据下发后成功的回调方法
     *
     * @param messageId 设置的请求ID（用于多个请求回调识别）
     * @param data      回调数据
     */
    void onSuccess(int messageId, BaseResponseModel data);

    /**
     * 请求数据下发后失败的回调方法
     *
     * @param messageId  设置的请求ID（用于多个请求回调识别）
     * @param statusCode 网络请求返回状态码(所有状态都封装在HttpError中)
     * @param error       回调的错误数据
     */
    void onFail(int messageId, String statusCode, String error);
}

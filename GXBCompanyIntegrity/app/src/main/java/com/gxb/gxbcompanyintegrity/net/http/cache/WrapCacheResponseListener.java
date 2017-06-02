/**
 * 文 件 名:  WrapResponseListener.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net.http.cache;

import com.gxb.gxbcompanyintegrity.model.BaseResponseModel;
import com.gxb.gxbcompanyintegrity.net.HttpErrorCodeParseHelps;
import com.gxb.gxbcompanyintegrity.net.WJResponseErrorCode;
import com.gxb.lazynetlibrary.net.http.cache.CacheResponseListener;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 反馈监听从新包装
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WrapCacheResponseListener<T extends Serializable> implements CacheResponseListener<T, String> {

    private WJCacheResponseListener mResponseListener;

    public WrapCacheResponseListener() {
    }

    public WrapCacheResponseListener(WJCacheResponseListener responseListener) {
        this.mResponseListener = responseListener;
    }

    /**
     * 设置监听器
     *
     * @param mResponseListener
     */
    public void setResponseListener(WJCacheResponseListener mResponseListener) {
        this.mResponseListener = mResponseListener;
    }

    @Override
    public void onStart(int messageId) {

    }

    @Override
    public void onLoadCache(int messageId, Map<String, List<String>> headers, T cacheData) {
        if (mResponseListener != null) {
            if (cacheData != null) {
                if (cacheData instanceof BaseResponseModel) {
                    BaseResponseModel model = (BaseResponseModel) cacheData;
                    if ("OK".equals(model.getCode())) {
                        if (!model.isEmpty()) {//如果数据是空的
                            mResponseListener.onLoadCache(messageId, model);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onSuccess(int messageId, Map<String, List<String>> headers, T data) {
        if (mResponseListener != null) {
            if (data != null) {
                if (data instanceof BaseResponseModel) {
                    BaseResponseModel model = (BaseResponseModel) data;
                    if ("OK".equals(model.getCode())) {
                        if (model.isEmpty()) {//如果数据是空的
                            mResponseListener.onFail(messageId, String.valueOf(WJResponseErrorCode.ERROR_EMPTY_DATA), "后台返回的数据为空");
                        } else {
                            mResponseListener.onSuccess(messageId, model);
                        }
                    } else {
                        onSuccessErrorManage(messageId, model.getCode(), model.getMsg());
                    }
                } else {
                    mResponseListener.onFail(messageId, String.valueOf(WJResponseErrorCode.ERROR_DIFFER_DATA), "返回数据与app端定义数据不一致");
                }
            } else {
                mResponseListener.onFail(messageId, String.valueOf(WJResponseErrorCode.ERROR_EMPTY_DATA), "后台返回的数据为空");
            }
        }
    }

    @Override
    public void onFail(int messageId, int statusCode, Map<String, List<String>> headers, String data) {
        if (mResponseListener != null) {
            data = HttpErrorCodeParseHelps.getMessageByStatusCode(statusCode);
            mResponseListener.onFail(messageId, String.valueOf(statusCode), data);
        }
    }

    /**
     * 获取到数据后错误的处理
     *
     * @param messageId
     * @param statusCode
     * @param error
     */
    private void onSuccessErrorManage(int messageId, String statusCode, String error) {
//        if (HiMsgCdConstants.URM_CHKLOGIN_FAIL.equals(statusCode)
//                || HiMsgCdConstants.URM_CHKUSR_NOEXIST.equals(statusCode)
//                || HiMsgCdConstants.URM_CHKUSR_NOMATCH.equals(statusCode)) {//用户登入状态失效
//            WJDefines.showToast(error, Toast.LENGTH_SHORT);
//            WJUserInfoConfig.deteleUserInfo();
//            WJMainActivity mainActivity = WJApplication.getApplication().getActivityStackManager().findActivity(WJMainActivity.class);
//            if (mainActivity != null) {
//                mainActivity.backToLogout(true);
//                WJLoginStateWrapper.starLogintActivity(mainActivity);
//            }
//        } else if (HiMsgCdConstants.SINGLE_LOGIN.equals(statusCode)) {
//            Activity activity = WJApplication.getApplication().getActivityStackManager().topActivity();
//            WJDefines.kickLogout(activity);
//        } else {
//            mResponseListener.onFail(messageId, statusCode, error);
//        }
    }
}

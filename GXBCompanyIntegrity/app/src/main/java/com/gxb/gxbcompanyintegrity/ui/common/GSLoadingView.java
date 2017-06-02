/**
 * 文 件 名:  WJLoadingView.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/19
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.ui.common;

import android.support.annotation.NonNull;

import com.gxb.gxbcompanyintegrity.model.BaseResponseModel;
import com.gxb.gxbcompanyintegrity.net.HttpErrorCodeParseHelps;
import com.gxb.lazynetlibrary.net.http.LoadingViewInterface;

/**
 * 显示加载中的view
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GSLoadingView implements LoadingViewInterface<BaseResponseModel> {
    /**
     * app中定制的loadigview
     */
    private GSDefaultLoadingView loadingView;

    public GSLoadingView(@NonNull GSDefaultLoadingView loadingView) {
        this.loadingView = loadingView;
        if (this.loadingView != null) {
            this.loadingView.loading();
        }
    }

    @Override
    public void loadStart(int loadId) {
        if (loadingView != null && !loadingView.isLoading()) {
            loadingView.loading();
        }
    }

    @Override
    public void loadSuccess(int loadId, BaseResponseModel responseData) {
        if (loadingView != null) {
            if (responseData != null) {
                if ("OK".equals(responseData.getCode())) {
                    if (responseData.isEmpty()) {
//                        setEmptyLoadingView(loadId);
                    } else {
                        loadingView.content();
                    }
                }
//                else  if(HiMsgCdConstants.SINGLE_LOGIN.equals(responseData.getRspCd())){
//                    loadingView.showEmpty(ResourceUtils.getDrawable(loadingView.getContext(),
//                            R.mipmap.blank), "", "");
//                }
            else{
//                    List<Integer> ids = new ArrayList<>();
//                    ids.add(R.id.errorStateContentTextView);
//                    loadingView.error(null, responseData.getRspInf(), null, ids);
                loadingView.error();
                }
            } else {
                loadingView.empty();
//                        "返回数据为空", "返回数据各式与app端不一至,导致json解析失败,或者服务根本就没有返回数据,请与服务端开发沟通");
            }
        }
    }

    @Override
    public void loadFail(int loadId, int failCode, String failMessage) {
        failMessage = HttpErrorCodeParseHelps.getMessageByStatusCode(failCode);
        if (loadingView != null) {
//            List<Integer> ids = new ArrayList<>();
//            ids.add(R.id.errorStateContentTextView);
//            loadingView.showError(null, failMessage, null, ids);
        }
    }

//    private void setEmptyLoadingView(int loadId) {
//        if (loadId == R.string.url_usr_get_org_bank_list) {
//            loadingView.showEmpty(null, "暂无银行卡", "");
//        } else if (loadId == R.string.url_p2p_order_list) {
//            loadingView.showEmpty(null, "暂无记录", "");
//        } else if (loadId == R.string.url_ticket_list || loadId == (R.string.url_ticket_list + 100))  {
//            loadingView.showEmpty(null, "无优惠券", "");
//        } else if (loadId == R.string.url_usr_msg_list) {
//            loadingView.showEmpty(null, "暂无消息", "");
//        } else if (loadId == R.string.url_get_recently_income || loadId == R.string.url_get_has_income_list ) {
//            loadingView.showEmpty(null, "暂无记录，去投资吧～", "");
//            loadingView.setEmptyStateButtonVisiable();
//        } else {
//            loadingView.showEmpty(null, "暂无记录", "");
//        }
//    }
}

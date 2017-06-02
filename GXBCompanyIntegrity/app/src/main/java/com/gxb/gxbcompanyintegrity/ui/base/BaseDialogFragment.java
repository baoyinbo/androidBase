/**
 * 文 件 名:  BaseDialogFragment.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.ui.base;

import android.app.Activity;

import com.gxb.gxbcompanyintegritylibrary.LazyAppCompatActivity;
import com.gxb.gxbcompanyintegritylibrary.LazyDialogFragment;


/**
 * DialogFragment基类
 *
 */
public abstract class BaseDialogFragment extends LazyDialogFragment {
    /***
     * 开启退出activity的动画
     */
    protected void openExitTransition() {
        Activity activity=getActivity();
        if(activity instanceof LazyAppCompatActivity){
            ((LazyAppCompatActivity) activity).setOpenExitTransition(true);
        }
    }

    /**
     * finish掉当前activity
     */
    protected void finishActivity(){
        if(getActivity()!=null&&!getActivity().isFinishing()){
            getActivity().finish();
        }
    }

    /**
     * finish掉当前activity(带动画效果的)
     */
    protected void finishActivityAfterTransition(){
        openExitTransition();
        finishActivity();
    }
}

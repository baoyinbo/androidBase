package com.gxb.gxbcompanyintegrity.ui.login.wrap;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gxb.gxbcompanyintegrity.config.GSUserInfoConfig;
import com.gxb.gxbcompanyintegrity.ui.base.BaseActivity;
import com.gxb.gxbcompanyintegrity.ui.base.BaseFragment;
import com.gxb.gxbcompanyintegrity.ui.base.CommonActivity;
import com.gxb.gxbcompanyintegrity.ui.base.LaunchBody;
import com.gxb.gxbcompanyintegrity.ui.login.GSLoginFragment;

/**
 * 登录模块管理
 * Created by baoyb on 2017/5/31.
 */

public class GSLoginStatusWrap {
    private static OnLoginResponseCallback loginResponseCallback;

    public static void checkLogin(BaseFragment fragment,
                                  BaseActivity activity,
                                  OnLoginResponseCallback callback) {
        loginResponseCallback=callback;
        if (GSUserInfoConfig.isLogin()) {
            callback.onLoginSuccess(Activity.RESULT_OK, null, null);
        } else {
            //未登录状态下，跳转到登录界面
            if (fragment != null) {
                Bundle bundle = new Bundle();
                LaunchBody.Builder builder;
                builder = new LaunchBody.Builder(fragment, GSLoginFragment.class);
                builder.bundle(bundle);
                builder.launchType(LaunchBody.LaunchType.SINGLE_TASK);
                CommonActivity.launch(builder);

            } else if (activity != null) {
                Bundle bundle = new Bundle();
                LaunchBody.Builder builder;
                builder = new LaunchBody.Builder(activity, GSLoginFragment.class);
                builder.bundle(bundle);
                builder.launchType(LaunchBody.LaunchType.SINGLE_TASK);
                CommonActivity.launch(builder);
            }
        }
    }


    /**
     * 登录接口回调函数
     */
    public interface OnLoginResponseCallback {
        void onLoginSuccess(int result, @Nullable BaseActivity activity, Bundle data);
    }

    public static OnLoginResponseCallback getLoginResponseCallback() {
        return loginResponseCallback;
    }

    public static void setLoginResponseCallback(OnLoginResponseCallback loginResponseCallback) {
        GSLoginStatusWrap.loginResponseCallback = loginResponseCallback;
    }
}

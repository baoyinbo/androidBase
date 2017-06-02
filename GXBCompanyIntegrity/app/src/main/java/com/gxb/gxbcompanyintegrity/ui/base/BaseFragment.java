package com.gxb.gxbcompanyintegrity.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.gxb.gxbcompanyintegrity.util.StatusBarUtil;
import com.gxb.gxbcompanyintegritylibrary.LazyAppCompatActivity;
import com.gxb.gxbcompanyintegritylibrary.LazyFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Fragment 基类
 * Created by baoyb on 2017/5/24.
 */

public abstract class BaseFragment extends LazyFragment {

    /***
     * 开启退出activity的动画
     */
    protected void openExitTransition() {
        Activity activity = getActivity();
        if (activity instanceof LazyAppCompatActivity) {
            ((LazyAppCompatActivity) activity).setOpenExitTransition(true);
        }
    }

    /**
     * finish掉当前activity
     * @param resultCode 结果码
     * @param data 数据
     */
    public void finishActivity(int resultCode,Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode,data);
        }
        finishActivity();
    }

    /**
     * finish掉当前activity
     * @param resultCode
     */
    public void finishActivity(int resultCode) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode);
        }
        finishActivity();
    }

    /**
     * finish掉当前activity
     */
    public void finishActivity() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().finish();
        }
    }

    /**
     * finish掉当前activity(带动画效果的)
     * @param resultCode 结果码
     * @param data
     */
    public void finishActivityAfterTransition(int resultCode,Intent data) {
        openExitTransition();
        finishActivity(resultCode,data);
    }

    /***
     * finish掉当前activity(带动画效果的)
     * @param resultCode 结果码
     */
    public void finishActivityAfterTransition(int resultCode) {
        openExitTransition();
        finishActivity(resultCode);
    }

    /**
     * finish掉当前activity(带动画效果的)
     */
    public void finishActivityAfterTransition() {
        openExitTransition();
        finishActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /** 设置状态栏颜色 */
    public void initSystemBarTint(boolean isTranslucent, int colorId) {
        Window window = this.getActivity().getWindow();
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        SystemBarTintManager tintManager = new SystemBarTintManager(this.getActivity());
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        //首先使 ChildView 不预留空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        int statusBarHeight = config.getStatusBarHeight();
        if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
            //移除假的 View.
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //清除 ChildView 的 marginTop 属性
            if (lp != null) {
                lp.topMargin = statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
        if (isTranslucent) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            StatusBarUtil.statusBarLightMode(this.getActivity());
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
            this.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(colorId));
        }
    }
}

package com.gxb.gxbcompanyintegrity.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.jude.swipbackhelper.SwipeBackPage;
import com.jude.swipbackhelper.SwipeListener;

/**
 * Created by baoyb on 2017/5/24.
 */

public abstract class BaseActivityFragment extends BaseFragment {
    private ActionBar actionBar;

    /**
     * 当前滑动返回页
     */
    private SwipeBackPage swipeBackPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * 当启动模式为SINGLE_TASK会被调用
     *
     * @param intent
     */
    protected void onNewIntent(Intent intent) {

    }

    /***
     * 获取当前activity
     */
    protected ActionBar getActionBar() {
        if (actionBar == null) {
            Activity activity = getActivity();
            if (activity != null && activity instanceof AppCompatActivity) {
                actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            }
        }
        return actionBar;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(CharSequence title) {
        if (getActionBar() == null) throw new NullPointerException("actionBar is null");
        getActionBar().setTitle(title);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    protected void setTitle(@StringRes int resId) {
        if (getActionBar() == null) throw new NullPointerException("actionBar is null");
        getActionBar().setTitle(resId);
    }

    /**
     * Activity的ContentView
     *
     * @return
     */
    public int inflateActivityContentView() {
        return -1;
    }

    /**
     * 点击了返回键的反馈
     *
     * @return 是否停止向下传递点击事件
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 添加滑动监听器
     *
     * @param listener
     */
    protected void addSwipeListener(SwipeListener listener) {
        getCurrentPage().addListener(listener);
    }

    /***
     * 设置是否能够滑动退出activity
     *
     * @param enable
     */
    protected void setSwipeBackEnable(boolean enable) {
        getCurrentPage().setSwipeBackEnable(enable);
    }

    /**
     * 获取当前SwipeBack页面
     *
     * @return
     */
    protected SwipeBackPage getCurrentPage() {
        if (swipeBackPage == null) {
            if (getActivity() instanceof BaseActivity) {
                swipeBackPage = ((BaseActivity) getActivity()).getCurrentPage();
            }
        }
        return swipeBackPage;
    }

    /***
     * 设置状态栏是否透明的
     *
     * @param on 透明状态开关
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//点击了返回键
            if (!onBackPressed()) {
                finishActivity();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        swipeBackPage = null;
    }
}

package com.gxb.gxbcompanyintegrity.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;


import com.gxb.gxbcompanyintegrity.R;

import java.lang.reflect.Method;

/**
 * Created by baoyb on 2017/3/24.
 */

public class CommonActivity extends BaseActivity{
    private static final String CONTENTID = "contentId";
    private static final String CLASSNAME = "className";
    private int contentId;
    private String fragmentClassName;

    @Override
    public void doCreate(Bundle savedInstanceState) {
        contentId = savedInstanceState == null ? R.layout.gs_comm_ui_container
                : savedInstanceState.getInt(CONTENTID);
        fragmentClassName = savedInstanceState == null ? null
                : savedInstanceState.getString(CLASSNAME);
        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                fragmentClassName = getIntent().getStringExtra(CLASSNAME);
                if (TextUtils.isEmpty(fragmentClassName)) {
                    finish();
                    return;
                }

                Bundle values = getIntent().getBundleExtra("args");
                Class clazz = Class.forName(fragmentClassName);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[]{Bundle.class});
                        method.invoke(fragment, values);
                    } catch (Exception e) {
                    }
                }
                // 重写Activity的contentView
                try {
                    Method method = clazz.getMethod("inflateActivityContentView");
                    if (method != null) {
                        int fragmentConfigId = Integer.parseInt(method.invoke(fragment).toString());
                        if (fragmentConfigId > 0) {
                            contentId = fragmentConfigId;
                        }
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                finish();
                return;
            }
        }
        setContentView(contentId);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragmentContainer, fragment, fragmentClassName).
                    commitAllowingStateLoss();
        }
    }

    @Override
    public void initView() {
        initSystemBarTint(false, R.color.white);
        if (contentId == R.layout.gs_comm_ui_container) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if(toolbar!=null){
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            contentId = savedInstanceState.getInt(CONTENTID);
            fragmentClassName = savedInstanceState.getString(CLASSNAME);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CONTENTID, contentId);
        outState.putString(CLASSNAME, fragmentClassName);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClassName);
        if (fragment != null && fragment instanceof BaseActivityFragment) {
            ((BaseActivityFragment) fragment).onNewIntent(intent);
        }
    }

    /***
     * activity获取根Fragment
     */
    public String getRootFragmentClassName() {
        return fragmentClassName;
    }

    /***
     * 开启一个activity(fragment作为内容,activity作为容器)
     *
     * @param builder
     */
    public static void launch(@NonNull LaunchBody.Builder builder) {
        LaunchBody launchBody = builder.build();
        launchBody.launchActivity(CommonActivity.class);
    }

    /***
     * 开启一个activity(fragment作为内容,activity作为容器)
     *
     * @param requestCode
     * @param builder
     */
    public static void launchForResult(int requestCode, @NonNull LaunchBody.Builder builder) {
        LaunchBody launchBody = builder.build();
        launchBody.launchActivityForResult(requestCode, CommonActivity.class);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClassName);
        if (fragment != null && fragment instanceof BaseActivityFragment) {
            if (!((BaseActivityFragment) fragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

}

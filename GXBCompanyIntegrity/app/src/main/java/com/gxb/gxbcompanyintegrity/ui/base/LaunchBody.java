/**
 * 文 件 名:  LaunchBody.java
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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.gxb.gxbcompanyintegrity.GSApplication;


/**
 *
 */
public class LaunchBody {
    /**
     * 当前activity
     */
    private Activity from;
    /**
     * 当前fragment
     */
    private Fragment fragment;
    /**
     * 被activity包裹的fragment
     */
    private Class<? extends BaseActivityFragment> clazz;
    /**
     * 启动activity传的参数
     */
    private Bundle args;
    /**
     * 启动activity类型
     */
    private LaunchType launchType;

    private LaunchBody(Builder builder) {
        this.from = builder.from;
        this.fragment = builder.fragment;
        this.clazz = builder.clazz;
        this.args = builder.args;
        this.launchType = builder.launchType;
    }

    /**
     * 启动一个activity(这个activity用于包裹fragment,实际显示的界面是activity)
     *
     * @param activityClazz
     */
    protected void launchActivity(Class<? extends CommonActivity> activityClazz) {
        if (!isLaunch(clazz, launchType)) {
            return;
        }
        Intent intent = null;
        if (fragment != null) {
            if (fragment.getActivity() == null) {
                return;
            } else {
                intent = new Intent(fragment.getActivity(), activityClazz);
                intent.putExtra("className", clazz.getName());
                if (args != null) {
                    intent.putExtra("args", args);
                }
                fragment.startActivity(intent);
            }
        } else if (from != null) {
            intent = new Intent(from, activityClazz);
            intent.putExtra("className", clazz.getName());
            if (args != null) {
                intent.putExtra("args", args);
            }
            from.startActivity(intent);
        }
    }

    /**
     * 启动一个activity,带反馈的(这个activity用于包裹fragment,实际显示的界面是activity)
     *
     * @param requestCode   请求码
     * @param activityClazz
     */
    protected void launchActivityForResult(int requestCode, Class<? extends CommonActivity> activityClazz) {
        if (!isLaunch(clazz, launchType)) {
            return;
        }
        Intent intent = null;
        if (fragment != null) {
            if (fragment.getActivity() == null) {
                return;
            } else {
                intent = new Intent(fragment.getActivity(), activityClazz);
                intent.putExtra("className", clazz.getName());
                if (args != null) {
                    intent.putExtra("args", args);
                }
                fragment.startActivityForResult(intent, requestCode);
            }
        } else if (from != null) {
            intent = new Intent(from, activityClazz);
            intent.putExtra("className", clazz.getName());
            if (args != null) {
                intent.putExtra("args", args);
            }
            from.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 返回是否继续开启activity
     *
     * @param clazz
     * @param launchType
     * @return
     */
    private boolean isLaunch(@NonNull Class<? extends Fragment> clazz, LaunchType launchType) {
        if (launchType == null) {
            return true;
        }
        GSActivityStackManager activityStackManager = GSApplication.getApplication().getActivityStackManager();
        switch (launchType) {
            case STANDARD:
                break;
            case SINGLE_TOP:
                Activity activity = activityStackManager.topActivity();
                if (clazz != null && activity instanceof CommonActivity) {
                    if (clazz.getName().equals(((CommonActivity) activity).getRootFragmentClassName())) {
                        return false;
                    }
                }
                break;
            case SINGLE_TASK:
                if (activityStackManager instanceof GSActivityStackManager) {
                    GSActivityStackManager stackManager = (GSActivityStackManager) activityStackManager;
                    Intent intent = new Intent();
                    if (args != null)
                        intent.putExtras(args);
                    if (stackManager.popFragmentTopActicity(clazz, intent)) {
                        return false;
                    }
                }
                break;
            default:
        }
        return true;
    }

    /***
     * new 一个新的LaunchBody构造器
     *
     * @return
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /***
     * activity启动方式枚举
     */
    public enum LaunchType {
        /**
         * 默认模式，可以不用写配置。在这个模式下，都会默认创建一个新的实例。
         * 因此，在这种模式下，可以有多个相同的实例，也允许多个相同Activity叠加
         */
        STANDARD,
        /***
         * 可以有多个实例，但是不允许多个相同Activity叠加。
         * 即，如果Activity在栈顶的时候，启动相同的Activity，不会创建新的实例，而会调用其onNewIntent方法
         */
        SINGLE_TOP,
        /***
         * 只有一个实例。在同一个应用程序中启动他的时候，若Activity不存在，则会在当前task创建一个新的实例，若存在，
         * 则会把task中在其之上的其它Activity destory掉并调用它的onNewIntent方法。
         * <p/>
         * 如果是在别的应用程序中启动它，则会新建一个task，并在该task中启动这个Activity，singleTask允许别的Activity与其在一个task中共存，
         * 也就是说，如果我在这个singleTask的实例中再打开新的Activity，这个新的Activity还是会在singleTask的实例的task中
         */
        SINGLE_TASK;
    }

    public static class Builder {
        /**
         * 当前activity
         */
        private Activity from;
        /**
         * 当前fragment
         */
        private Fragment fragment;
        /**
         * 被activity包裹的fragment
         */
        private Class<? extends BaseActivityFragment> clazz;
        /**
         * 启动activity传的参数
         */
        private Bundle args;
        /**
         * 启动activity类型
         */
        private LaunchType launchType;

        public Builder(@NonNull LaunchBody launchBody) {
            this.from = launchBody.from;
            this.fragment = launchBody.fragment;
            this.clazz = launchBody.clazz;
            this.args = launchBody.args;
            this.launchType = launchBody.launchType;
        }

        public Builder(@NonNull Activity from, @NonNull Class<? extends BaseActivityFragment> clazz) {
            this.from = from;
            this.clazz = clazz;
        }

        public Builder(@NonNull Fragment fragment, @NonNull Class<? extends BaseActivityFragment> clazz) {
            this.fragment = fragment;
            this.clazz = clazz;
        }


        /***
         * 设置要传的参数
         *
         * @param bundle 参数
         * @return
         */
        public Builder bundle(Bundle bundle) {
            this.args = bundle;
            return this;
        }

        /**
         * 设置开启activity的模式
         *
         * @param launchType
         * @return
         */
        public Builder launchType(LaunchType launchType) {
            this.launchType = launchType;
            return this;
        }

        /**
         * 构建LaunchBody
         *
         * @return
         */
        public LaunchBody build() {
            return new LaunchBody(this);
        }
    }
}

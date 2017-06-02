/**
 * 文 件 名:  WJActivityStackManager.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;


import com.gxb.gxbcompanyintegritylibrary.ActivityStackManager;

import java.util.Iterator;
import java.util.Stack;

/**
 * Activity堆栈
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GSActivityStackManager extends ActivityStackManager {

    /**
     * 推出Fragment之上的所有activity
     *
     * @param clazz
     * @param intent
     * @return 是否成功
     */
    public boolean popFragmentTopActicity(Class<? extends Fragment> clazz, Intent intent) {
        Stack<Activity> activityStack = getActivityStack();
        if (activityStack == null) throw new NullPointerException("activityStack is null");
        if (!containsFramgent(activityStack, clazz)) {
            return false;
        }
        for (Activity activity = activityStack.pop(); activity != null; activity = activityStack.pop()) {
            if (activity instanceof CommonActivity) {
                CommonActivity commonActivity = (CommonActivity) activity;
                if (clazz.getName().equals(commonActivity.getRootFragmentClassName())) {
                    commonActivity.onNewIntent(intent);
                    pushActivity(commonActivity);
                    return true;
                } else {
                    activity.finish();
                }
            } else {
                activity.finish();
            }
        }
        return false;
    }

    /**
     * 判断当前栈中是否包含此Framgent
     *
     * @param stack
     * @param clazz
     * @return
     */
    private boolean containsFramgent(Stack<Activity> stack, Class<? extends Fragment> clazz) {
        for (Activity activity : stack) {
            if (activity instanceof CommonActivity) {
                CommonActivity commonActivity = (CommonActivity) activity;
                if (clazz.getName().equals(commonActivity.getRootFragmentClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 去掉CommonActivity中对应的fragment
     * @param clazz
     *
     * @return 是否成功
     */
    public boolean finishFragment(Class<? extends Fragment> clazz) {
        Stack<Activity> activityStack = getActivityStack();
        if (activityStack == null) throw new NullPointerException("activityStack is null");
        if (!containsFramgent(activityStack, clazz)) {
            return false;
        }
        for(Iterator iter = activityStack.iterator(); iter.hasNext();) {
            Activity activity = (Activity)iter.next();
            if (activity instanceof CommonActivity) {
                CommonActivity commonActivity = (CommonActivity) activity;
                if (clazz.getName().equals(commonActivity.getRootFragmentClassName())) {
                    iter.remove();
                    activity.finish();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 去掉CommonActivity中对应的fragment
     * @param clazz
     * @param requestCode
     * @return 是否成功
     */
    public boolean finishFragmentForResult(Class<? extends Fragment> clazz, int requestCode) {
        Stack<Activity> activityStack = getActivityStack();
        if (activityStack == null) throw new NullPointerException("activityStack is null");
        if (!containsFramgent(activityStack, clazz)) {
            return false;
        }
        for(Iterator iter = activityStack.iterator(); iter.hasNext();) {
            Activity activity = (Activity)iter.next();
            if (activity instanceof CommonActivity) {
                CommonActivity commonActivity = (CommonActivity) activity;
                if (clazz.getName().equals(commonActivity.getRootFragmentClassName())) {
                    iter.remove();
                    activity.finishActivity(requestCode);
                    return true;
                }
            }
        }
        return false;
    }

}

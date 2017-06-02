/**
 * 文 件 名:  UpdatePageBroadcastReceiver.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/8/8
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 更新页面的广播接收器
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/8/8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UpdatePageBroadcastReceiver extends BroadcastReceiver {
    public final static String PAGE_REFRESH_ACTION = "com.weiji.fin.ui.PAGE_REFRESH";
    private static HashMap<PageType, List<RefreshPageObserver>> refreshPageObservers;
    /**页面刷新广播接收器*/
    private static BroadcastReceiver receiver;
    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            synchronized (UpdatePageBroadcastReceiver.class) {
                if (receiver == null) {
                    receiver = new UpdatePageBroadcastReceiver();
                }
            }
        }
        return receiver;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(PAGE_REFRESH_ACTION)) {
            PageType pageType = (PageType) intent.getSerializableExtra(PageType.class.getName());
            notifyObserver(pageType, intent.getExtras());
        }
    }

    /**
     * 通知所有观察者做刷新操作
     *
     * @see [类、类#方法、类#成员]
     */
    private void notifyObserver(PageType pageType, Bundle bundle) {
        if (refreshPageObservers == null || pageType == null) return;
        if (refreshPageObservers.containsKey(pageType)) {
            List<RefreshPageObserver> list = refreshPageObservers.get(pageType);
            if (list != null && !list.isEmpty()) {
                for (RefreshPageObserver observer : list) {
                    if (observer != null) {
                        observer.onPageRefresh(bundle);
                    }
                }
            }
        }
    }

    /**
     * 注册页面刷新的观察者
     *
     * @param pageType 对应的页面
     * @param observer observerKey
     */
    public static void registerObserver(PageType pageType, RefreshPageObserver observer) {
        if (refreshPageObservers == null) {
            refreshPageObservers = new HashMap<>();
        }
        if (refreshPageObservers.containsKey(pageType) && refreshPageObservers.get(pageType) != null) {
            refreshPageObservers.get(pageType).add(observer);
        } else {
            List<RefreshPageObserver> list = new ArrayList<>();
            list.add(observer);
            refreshPageObservers.put(pageType, list);
        }
    }

    /**
     * 注销页面刷新的观察者
     *
     * @param pageType 对应的页面
     * @param observer observerKey
     */
    public static void unregisterObserver(PageType pageType, RefreshPageObserver observer) {
        if (refreshPageObservers != null
                && refreshPageObservers.containsKey(pageType)) {
            if (refreshPageObservers.get(pageType) == null
                    || refreshPageObservers.get(pageType).isEmpty()
                    || refreshPageObservers.get(pageType).size() <= 1) {
                refreshPageObservers.remove(pageType);
            } else {
                if (refreshPageObservers.get(pageType).contains(observer)) {
                    refreshPageObservers.get(pageType).remove(observer);
                }
            }
        }
    }

    /**
     * 注册页面刷新广播
     *
     * @param mContext
     */
    public static void registerPageRefreshBroadcast(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PAGE_REFRESH_ACTION);
        mContext.getApplicationContext()
                .registerReceiver(getReceiver(), filter);
    }

    /**
     * 注销页面刷广播
     *
     * @param mContext
     */
    public static void unregisterPageRefreshBroadcast(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 刷新对应页面
     *
     * @param mContext
     */
    public static void refreshPage(Context mContext, @NonNull PageType pageType) {
        refreshPage(mContext,pageType,null);
    }

    /**
     * 刷新对应页面
     *
     * @param mContext
     */
    public static void refreshPage(Context mContext, @NonNull PageType pageType, @Nullable Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(PAGE_REFRESH_ACTION);
        intent.putExtra(PageType.class.getName(),pageType);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        mContext.sendBroadcast(intent);
    }

    /**
     * 清除广播数据
     *
     * @see [类、类#方法、类#成员]
     */
    public static void close() {
        if (refreshPageObservers != null) {
            refreshPageObservers.clear();
            refreshPageObservers = null;
        }
        if (receiver != null) {
            receiver = null;
        }
    }

    /**
     * 页面需要刷新的观察者
     */
    public interface RefreshPageObserver {
        /**
         * 刷新页面的方法
         *
         * @param bundle 接收到的数据
         */
        void onPageRefresh(@Nullable Bundle bundle);
    }

    /**
     * 页面类型
     */
    public enum PageType {

        /**
         * 理财列表页
         */
        PAGE_FINANCE_FRAGMENT,
        /**
         * 我的页
         */
        PAGE_MINE_FRAGMENT,
        /**
         * 理财详情页
         */
        PAGE_FINANCE_DETAIN_FRAGMENT,
        /**
         * 发现页
         */
        PAGE_FIND_FRAGMENT,

        /**
         * 首页
         */
        PAGE_HOME_FRAGMENT,

        /**
         * 消息中心
         */
        PAGE_MESSAGE_CENTER
    }
}

/**
 * 文 件 名:  ScreenBroadcastReceiver.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/22
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 屏幕状态观察者
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
//        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
//        } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
//            Activity activity=WJApplication.getApplication().getActivityStackManager().topActivity();
//            ActivityManager am = (ActivityManager) context
//                    .getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//            ComponentName topActivity = tasks.get(0).topActivity;
//            /**先判断是否在前台运行*/
//            if (topActivity.getPackageName().equals(context.getPackageName())) {
//                if (activity!=null&&!(activity instanceof WJGestureVerifyActivity)) {
//                    if (WJUserInfoConfig.isLogin() && WJAppConfing.isExistsGestureLock()) {
//                        Intent intent1 = new Intent(context,
//                                WJGestureVerifyActivity.class);
//                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent1);
//                    }
//                }
//            }
//        }
    }

}

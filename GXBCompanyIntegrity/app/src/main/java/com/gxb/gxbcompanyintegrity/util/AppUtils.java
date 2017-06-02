package com.gxb.gxbcompanyintegrity.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.List;

public class AppUtils {
	private static final int MAX_RECENT_TASKS = 20;

	/**
	 * 获取栈顶activity
	 * 
	 * @param context
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ComponentName getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity;
		else
			return null;
	}

	/**
	 * 打开后台app
	 * 
	 * @param context
	 * @see [类、类#方法、类#成员]
	 */
	public static void openApp(Context context) {
		// 这个activity的信息是我们的launcher
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RecentTaskInfo> recentTasks = am.getRecentTasks(
				MAX_RECENT_TASKS + 1, 0x0002);
		for (int i = 0; i < recentTasks.size(); i++) {
			final ActivityManager.RecentTaskInfo info = recentTasks.get(i);
			Intent intent = new Intent(info.baseIntent);
			if (info.origActivity != null) {
				intent.setComponent(info.origActivity);
			}
			// 设置intent的启动方式为 创建新task()【并不一定会创建】
			intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			/**
			 * 如果找到是launcher，直接continue，后面的appInfos.add操作就不会发生了
			 */
			if (context.getPackageName().equals(
					intent.getComponent().getPackageName())) {
				intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
				context.startActivity(intent);
				break;
			}
		}
		recentTasks.clear();
		recentTasks = null;
	}

	/**
     * @return 获取当前程序进程的名称
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
    
    /*  
     *获取程序的包名    
     */    
    public static String getAppPackageName(Context cxt){
	      PackageManager pm=cxt.getPackageManager();
	      try {    
	    	  ApplicationInfo info = pm.getApplicationInfo(cxt.getPackageName(), 0);
	          return info.packageName;    
	        } catch (NameNotFoundException e) {
	            e.printStackTrace();    
	          return null;     
	        }    
    }    

}
package com.example.ussd19;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class AndroidUtils {
    private static final String RECENT_ACTIVITY;

    static {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            RECENT_ACTIVITY = "com.android.systemui.recents.RecentsActivity";
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            RECENT_ACTIVITY = "com.android.systemui.recent.RecentsActivity";
        } else {
            RECENT_ACTIVITY = "com.android.internal.policy.impl.RecentApplicationDialog";
        }
    }

    /**
     * To get the Device recent screen activity
     *
     * @param className
     * @return activity
     */
    public static boolean isRecentActivity(String className) {
        if (RECENT_ACTIVITY.equalsIgnoreCase(className)) {
            return true;
        }

        return false;
    }

    public static String getRecentApps(Context context) {
        String topPackageName = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            long time = System.currentTimeMillis();

            UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 150, System.currentTimeMillis());
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
            }

            if (event != null && !TextUtils.isEmpty(event.getPackageName()) && event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                if (AndroidUtils.isRecentActivity(event.getClassName())) {
                    return event.getClassName();
                }
                return event.getPackageName();
            } else {
                topPackageName = "";
            }
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;

            if (AndroidUtils.isRecentActivity(componentInfo.getClassName())) {
                return componentInfo.getClassName();
            }

            topPackageName = componentInfo.getPackageName();
        }


        return topPackageName;
    }

    public static List<String> getRecentActivity(Context context) {
        List<String> eventList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            long time = System.currentTimeMillis();

            UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 150, System.currentTimeMillis());


            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);

                if (event != null && !TextUtils.isEmpty(event.getPackageName()) && event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    eventList.add(event.getClassName());
                } else {
                    // topActivityName = "";
                }
            }

        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;

            eventList.add(componentInfo.getClassName());
        }


        return eventList;
    }
}
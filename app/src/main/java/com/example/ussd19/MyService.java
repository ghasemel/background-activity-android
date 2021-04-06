package com.example.ussd19;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

public class MyService extends Service {

    UsageStatsManager usageStatsManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

        final long now = System.currentTimeMillis();
        usageStatsManager = (UsageStatsManager)  getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
        final List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 1000 * 10, now);
        if (stats != null && !stats.isEmpty()) {
            for (int i = 0; i < stats.size(); i++) {
                Log.i("raouf:*****", "onStartCommand: " + stats.get(i).getPackageName());
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
}

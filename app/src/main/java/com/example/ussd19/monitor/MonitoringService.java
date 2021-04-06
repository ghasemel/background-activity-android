package com.example.ussd19.monitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MonitoringService extends Service {

    private static final String ACTION_FETCH = "action_fetch";
    private static final int REPEAT_DATALOAD_INTERVAL_MS = 10 * 60 * 1000; // 10 Min

    private static PendingIntent makeSelfPendingIntent(Context context) {
        PendingIntent intent = PendingIntent.getService(
                context, 0, makeSelfIntent(context), 0);
        return intent;
    }

    private static Intent makeSelfIntent(Context context) {
        Intent intent = new Intent(context, MonitoringService.class);
        intent.setAction(ACTION_FETCH);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_FETCH)) {
                loadDataFromContentProviderDoWhateverYouWantThen();
                setAlarmToRedoLoad();
                stopSelf();

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setAlarmToRedoLoad() {
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + REPEAT_DATALOAD_INTERVAL_MS,
                makeSelfPendingIntent(getApplicationContext()));
    }

    private void loadDataFromContentProviderDoWhateverYouWantThen(){
       /* check this link how to load data from content provider.
        as your games app are content providers. It should be loaded easily.
                then do whatever you want display, upload anything*/

        //http://developer.android.com/guide/topics/providers/content-provider-basics.html
    }

    // Call this method from onCreate of your monitoring app
    public static void start(Context context) {
        Intent intent = makeSelfIntent(context);
        context.startService(intent);
    }

}
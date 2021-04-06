package com.example.ussd19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ussd2.ussdRequestResponse(this);
        ActivityManager activityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++){
            Log.i("raouf****", "processName: " + procInfos.get(i).processName);
            if(procInfos.get(i).processName.equals("com.android.browser")) {
                Toast.makeText(getApplicationContext(), "Browser is running", Toast.LENGTH_LONG).show();
            }
        }


        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(100);
        for(int i = 0; i < runningTasks.size(); i++){
            Log.i("raouf2****", "processName: " + runningTasks.get(i).topActivity.getClassName());
        }

        //ComponentName cn = am.getRunningTasks(1).get(0).topActivity;



       // ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();

        for (ActivityManager.AppTask task : tasks) {
            Log.d("raouf3****", "stackId: " + task.getTaskInfo().baseActivity.getClassName());
        }
    }


}
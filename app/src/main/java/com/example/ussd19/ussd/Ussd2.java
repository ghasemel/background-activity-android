package com.example.ussd19.ussd;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.ussd19.R;

public class Ussd2 {


    public static void ussdRequestResponse(Activity activity) {
        activity.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                Handler handler = new Handler();

                TelephonyManager.UssdResponseCallback responseCallback = new TelephonyManager.UssdResponseCallback() {
                    @Override
                    public void onReceiveUssdResponse(TelephonyManager telephonyManager, String request, CharSequence response) {
                        super.onReceiveUssdResponse(telephonyManager, request, response);

                        Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReceiveUssdResponseFailed(TelephonyManager telephonyManager, String request, int failureCode) {
                        super.onReceiveUssdResponseFailed(telephonyManager, request, failureCode);

                        Toast.makeText(activity, String.valueOf(failureCode), Toast.LENGTH_SHORT).show();
                    }
                };

                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
                }
                telephonyManager.sendUssdRequest("*123#", responseCallback, handler);
            }
        });
    }
}

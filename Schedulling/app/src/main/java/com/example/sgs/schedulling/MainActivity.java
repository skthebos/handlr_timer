package com.example.sgs.schedulling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.tv1);



        /*handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this, "jsdiof jdiof", Toast.LENGTH_SHORT).show();
                count++;
                textView.setText("count = " + count);

                if(count == 10)
                    return;
                handler.postDelayed(runnable, 2000);
            }
        };

        handler.postDelayed(runnable, 2000);
        */

/*        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("count = " + count);
                    }
                });


                if(count == 10)
                    return;
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000, 5000);*/


        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("count = " + count);
                    }
                });


                if(count == 10)
                    return;
            }
        }, 5, TimeUnit.SECONDS);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }


    public void startAlarm(View view){
        int seconds = 10;
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 342424, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (seconds * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + seconds + " seconds",Toast.LENGTH_LONG).show();
    }
}

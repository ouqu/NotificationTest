package com.example.hahaha.notificationtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //  create activity with back stack
    private PendingIntent backStackPendingIntent(Class activity){
        Intent intent=new Intent(MainActivity.this,activity);
        return TaskStackBuilder.create(MainActivity.this)
                .addParentStack(activity)
                .addNextIntent(intent)
                .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

    }

    private PendingIntent normalPendingIntent(Class activity){
        Intent intent=new Intent(MainActivity.this,activity);
        return PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.button_norm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("to Acticity4")
                        .setContentText("to Acticity4")
                        .setContentIntent(normalPendingIntent(Activity4.class))
                        .setAutoCancel(true);
                NotificationManager notificationManager=
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());
            }
        });

        button= (Button) findViewById(R.id.button_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {

                        NotificationManager notificationManager=
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        NotificationCompat.Builder builder_load=new NotificationCompat.Builder(MainActivity.this);
                        builder_load.setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("loading")
                                    .setContentText("text:loading");
                        for(int incr=0;incr<=100;incr+=10){
                            builder_load.setProgress(100,incr,false);   //
                            notificationManager.notify(0,builder_load.build());
                            try{
                                Thread.sleep(1000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }


                        NotificationCompat.Builder builder_done=new NotificationCompat.Builder(MainActivity.this);
                        builder_done.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("to Acticity4")
                                .setContentText("to Acticity4")
                                .setContentIntent(backStackPendingIntent(Activity4.class))
                                .setAutoCancel(true);
                        notificationManager.notify(0,builder_done.build());

                    }
                };
                new Thread(runnable).start();
            }
        });

    }
}

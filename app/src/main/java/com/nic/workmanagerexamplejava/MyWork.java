package com.nic.workmanagerexamplejava;

import static com.nic.workmanagerexamplejava.MainActivity.TASK_KEY;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;


import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {
    public static final String RECIVE_TASK_KEY="recive_massage";
    public MyWork( @androidx.annotation.NonNull Context context,  @androidx.annotation.NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @androidx.annotation.NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        Notifactionshow("mr.appbuilder",data.getString(TASK_KEY));

        Data data1=new Data.Builder().putString(RECIVE_TASK_KEY,"mr.appbuilder Recive data Successfully and Thank you ").build();

        return Result.success(data1);
    }
    public  void Notifactionshow(String title,String desc){
        NotificationManager notificationManager=(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("mr.appbuilder", "mr.appbuilder", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "mr.appbuilder")
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, builder.build());

    }
}

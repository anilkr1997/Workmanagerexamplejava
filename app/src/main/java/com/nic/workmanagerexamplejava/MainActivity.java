package com.nic.workmanagerexamplejava;

import static com.nic.workmanagerexamplejava.MyWork.RECIVE_TASK_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


import android.os.Bundle;

import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_KEY="Input_TASK_KEY";
AppCompatTextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        Data data=new Data.
                Builder().
        putString(TASK_KEY,"mr.appbuilder this massage send by data !").build();

        Constraints constraints=new Constraints.Builder().setRequiresCharging(true).build();

        OneTimeWorkRequest oneTimeWorkRequest=new OneTimeWorkRequest.Builder(MyWork.class).setInputData(data).setConstraints(constraints).build();


        findViewById(R.id.notifactionsend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged( @androidx.annotation.Nullable WorkInfo workInfo) {
                try {
                    if(workInfo.getState().isFinished()){

                        textView.append(workInfo.getOutputData().getString(RECIVE_TASK_KEY));
                    }else {
                        String status = workInfo.getState().name();
                        textView.append(status + "\n");
                    }

                }catch (NullPointerException e){

                    Log.e("TAG", "onChanged: "+e.getLocalizedMessage() );
                }


            }
        });
    }
}
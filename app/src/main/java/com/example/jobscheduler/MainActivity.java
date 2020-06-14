package com.example.jobscheduler;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    public static final String TAG = "MainActivity";
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        button1 = findViewById( R.id.schedule_Job );
        button2 = findViewById( R.id.Cancel_Job );
        
        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName componentName = new ComponentName( getApplicationContext(), ExampleJobService.class );
                JobInfo info = new JobInfo.Builder(123, componentName)
//                        .setRequiresCharging( true )
                        .setRequiredNetworkType( JobInfo.NETWORK_TYPE_UNMETERED )
                        .setPersisted( true )
                        .setPeriodic( 5000 )
                        .build();
                JobScheduler scheduler = (JobScheduler)getSystemService( JOB_SCHEDULER_SERVICE );
                int resultCode = scheduler.schedule( info );
                if (resultCode == JobScheduler.RESULT_SUCCESS){
                    Log.d( TAG, "Job Scheduled" );
                    Toast.makeText( MainActivity.this, "Job Scheduled", Toast.LENGTH_SHORT ).show();

                }else {
                    Log.d( TAG, "Job Scheduling Failed" );
                    Toast.makeText( MainActivity.this, "Job Scheduling Failed", Toast.LENGTH_SHORT ).show();

                }
            }
        } );

        button2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler scheduler = (JobScheduler) getSystemService( JOB_SCHEDULER_SERVICE );
                scheduler.cancel( 123);
                Log.d( TAG, "Job Cancelled" );
                Toast.makeText( MainActivity.this, "Job Cancelled", Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}

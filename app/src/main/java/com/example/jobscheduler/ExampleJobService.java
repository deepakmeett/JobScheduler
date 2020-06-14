package com.example.jobscheduler;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
public class ExampleJobService extends JobService {

    public static final String TAG = "ExampleJobService";
    public boolean jobCancelled = false;
    MediaPlayer mediaPlayer;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d( TAG, "Job started" );
        doBackGroundWork( jobParameters );
        return true;
    }

    private void doBackGroundWork(final JobParameters jobParameters) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create( getApplicationContext(), Settings.System.DEFAULT_NOTIFICATION_URI );
                mediaPlayer.setLooping( true );
                mediaPlayer.start();
                for (int i = 0; i < 10; i++) {
                    Log.d( TAG, "run: " + i );
                    Toast.makeText( ExampleJobService.this, "run: " + i, Toast.LENGTH_SHORT ).show();
              
                    if (jobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep( 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d( TAG, "job finished" );
                Toast.makeText( getApplicationContext(), "job finished", Toast.LENGTH_SHORT ).show();
                jobFinished( jobParameters, false );
            }
        } ).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d( TAG, "job cancelled before completion" );
        Toast.makeText( getApplicationContext(), "job cancelled before completion", Toast.LENGTH_SHORT ).show();
        jobCancelled = true;
        mediaPlayer.stop();
        return true;
    }
}

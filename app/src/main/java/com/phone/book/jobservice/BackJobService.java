package com.phone.book.jobservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.derry.serialportlibrary.T;

import com.phone.book.activity.DialingActivity;
import com.phone.book.common.utils.LogUtil;
import com.phone.book.common.utils.PrefUtils;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2022/1/26
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BackJobService extends JobService {
    public static final int ELLISONS_JOB_ID = 0;
    public static final int ELLISONS_JOB_OVERDIDE_DEADLINE = 1000;
    private static final String TAG = T.TAG;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "BackJobService onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "BackJobService destroyed.");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w(TAG, "BackJobService onStartJob()");
        Helpers.doHardWork(this, params);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(mRunnable, 5000L);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "BackJobService stopped & wait to restart params:" + params);
        return true;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            Log.w(TAG, "doHardWork mHandler.postDelayed " + currentTime);
            mHandler.removeCallbacksAndMessages(null);
            if (PrefUtils.INSTANCE.readLong("startServiceTime") > 0 && currentTime - PrefUtils.INSTANCE.readLong("startServiceTime")
                    > PrefUtils.INSTANCE.readLong("awakeTime")) {
                PrefUtils.writeLong("startServiceTime", 0L);
//                Intent intent = new Intent(BackJobService.this, DialingActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                DialingActivity.Companion.startDialingFragment(BackJobService.this,"");
                mHandler.postDelayed(mRunnable, 5000L);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.w(TAG, "open again....");
                        DialingActivity.Companion.startDialingFragment(BackJobService.this,"");
                    }
                },4000L);
            } else {
                mHandler.postDelayed(mRunnable, 5000L);
            }
        }
    };
}

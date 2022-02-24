package com.phone.book.jobservice;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.derry.serialportlibrary.Device;
import com.derry.serialportlibrary.SerialPortFinder;
import com.derry.serialportlibrary.SerialPortManager;
import com.derry.serialportlibrary.T;
import com.derry.serialportlibrary.listener.OnOpenSerialPortListener;
import com.derry.serialportlibrary.listener.OnSerialPortDataListener;
import com.phone.book.utils.ThreadUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * description :
 * author : Andy.Guo
 * email : 495311081@qq.com
 * data : 2022/1/26
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BackJobService extends JobService {
    public static final int ELLISONS_JOB_ID = 1;
    public static final int ELLISONS_JOB_OVERDIDE_DEADLINE = 1000;
    private static final String TAG = T.TAG;
//    private SerialPortManager mSerialPortManager; // 打开串口，关闭串口，发生串口数据 需用的关联类

    private OnOpenSerialPortListener openSerialPortListener = null;
    private OnSerialPortDataListener onSerialPortDataListener = null;
    private boolean isSerialPortOpen = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isOnStop = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化单例
        SerialPortManager.getInstance();
        Log.w(TAG, "BackJobService onCreate()");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "BackJobService destroyed.");
        if (SerialPortManager.getInstance() != null) {
            SerialPortManager.getInstance().closeSerialPort();
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Helpers.doHardWork(this, params);
        //服务启动了或者重新启动了
        Log.w(TAG, "BackJobService onStartJob()");
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(mRunnable, 3000L);
//        Helpers.doHardWork(this, params);
        isSerialPortOpen = false;
        //服务启动了或者重新启动了，连一次串口
        serialConect();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        isOnStop = true;
        Log.w(TAG, "BackJobService stopped & wait to restart params:" + params);
        return true;//返回TRUE才能进程死了还能自启动服务
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isOnStop) {
                Log.d(T.TAG, "mRunnable isOndestroy =" + isOnStop);
                isSerialPortOpen = false;
                //服务启动了或者重新启动了，连一次串口
                serialConect();
                isOnStop = false;
            }
            Log.d(T.TAG, "mRunnable =" + System.currentTimeMillis());
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(mRunnable, 3000L);
        }
    };

    private void serialConect() {
        ArrayList<Device> devices = new SerialPortFinder().getDevices();
        Device targetDevice = null;
        for (Device device : devices) {
            if (device.getName().contains("ttyS2")) {
                targetDevice = device;
            }
        }


        if (openSerialPortListener == null) {
            openSerialPortListener = new OnOpenSerialPortListener() {
                @Override
                public void onSuccess(File device) {
                    Log.i(T.TAG, String.format("串口 [%s] 打开成功", device.getPath()));
                }

                @Override
                public void onFail(File device, Status status) {
                    switch (status) {
                        case NO_READ_WRITE_PERMISSION:
                            Log.i(T.TAG, device.getPath() + ", 没有读写权限");
                            break;
                        case OPEN_FAIL:
                        default:
                            Log.i(T.TAG, device.getPath() + ", 串口打开失败");
                            break;
                    }
                }
            };
        }

        if (onSerialPortDataListener == null) {
            onSerialPortDataListener = new OnSerialPortDataListener() {
                @Override
                public void onDataReceived(byte[] bytes) { // 接收到串口数据的监听函数

                }

                /**
                 * 开启发生消息线程startSendThread - 调用 - 数据发送
                 * @param bytes 发送的数据
                 */
                @Override
                public void onDataSent(byte[] bytes) { // 发送串口数据的监听函数
                    Log.i(T.TAG, " onDataSent [ byte[] ]: " + Arrays.toString(bytes)); // onDataSent [ byte[] ]: [97] 【发送2】
                    Log.i(com.derry.serialportlibrary.T.TAG, " onDataSent [ String ]: " + new String(bytes)); // onDataSent [ String ]: a
                    final byte[] finalBytes = bytes;
                    ThreadUtils.INSTANCE.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(T.TAG, String.format("发送\n%s", new String(finalBytes)));
                        }
                    });
                }
            };
        }


        // 打开串口
        if (targetDevice != null && targetDevice.getFile() != null && !isSerialPortOpen) {
            // 串口设备文件，波特率
            isSerialPortOpen = SerialPortManager.getInstance().setOnOpenSerialPortListener(openSerialPortListener)
                    .setOnSerialPortDataListener(onSerialPortDataListener)
                    .openSerialPort(targetDevice.getFile(), 115200);
        }
    }


}

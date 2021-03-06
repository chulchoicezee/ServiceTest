package com.chulgee.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyService mMyService;
    boolean mBound;
    MyProgress myProgress;
    Handler mHandler = new Handler();
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProgress = (MyProgress)findViewById(R.id.my_progress);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count+=10;
                Log.v(TAG, "count="+count);
                myProgress.setCurVal(count);
                mHandler.postDelayed(this, 5000);
            }
        },5000);
        Intent i = new Intent();
        i.setClass(this, MyService.class);
        startService(i);
        Log.v(TAG, "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    public void onClick(View v){
        Intent i = new Intent();
        switch(v.getId()){
            case R.id.button:
                i.setClass(this, MyService.class);
                bindService(i, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.button2:
                if(mBound){
                    unbindService(mServiceConnection);
                    mBound = false;
                }
                break;
            case R.id.button3:
                if(mBound) {
                    int sum = mMyService.getSum(5, 5);
                    Toast.makeText(this, "hello sum=" + sum, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button4:
                break;
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        public static final String TAG = "ServiceConnection";

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder)service;
            mMyService = binder.getService();
            mBound = true;
            Log.v(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.v(TAG, "onServiceDisconnected");
        }
    };
}

package com.chulgee.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService";
    LocalBinder mBinder = new LocalBinder();

    public MyService() {
    }

    public class LocalBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null){

        }
        Log.v(TAG, "onStartCommand intent="+intent);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind intent="+intent);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "onUnBind intent="+intent);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    public int getSum(int x, int y){
        Log.v(TAG, "getSum");
        return x + y;
    }
}

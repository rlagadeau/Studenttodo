package com.example.studenttodo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {

    private final IBinder kmkBinder = new MyLocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return kmkBinder;
    }

    public class MyLocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return (df.format(new Date()));
    }

}

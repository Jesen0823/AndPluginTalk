package com.jesen.insertpile_plugin;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.jesen.insertpile_standard.ServiceInterface;

public class BaseService extends Service implements ServiceInterface {

    private Service appHostService;

    /**
     * 传递宿主的环境给插件
     * */
    @Override
    public void insertAppContext(Service appHostService) {
        this.appHostService = appHostService;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onDestroy() {

    }
}















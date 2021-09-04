package com.jesen.insertpile_host;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jesen.insertpile_standard.ServiceInterface;

public class ProxyService extends Service {
    private static final String TAG = "[宿主]"+ProxyService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"-----onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"-----onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("className");
        Log.d(TAG,"-----onStartCommand");
        try {
            Class plugServiceClass = PluginManager.getInstance(this).getClassLoader().loadClass(className);
            Object plugService = plugServiceClass.newInstance();

            ServiceInterface serviceInterface = (ServiceInterface) plugService;
            // 注入 组件环境
            serviceInterface.insertAppContext(this);
            serviceInterface.onStartCommand(intent, flags, startId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"-----onDestroy");
    }
}

package com.jesen.insertpile_plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.jesen.insertpile_standard.ActivityInterface;

public class BaseActivity extends Activity implements ActivityInterface {

    private static final String TAG ="[插件]BaseActivity";

    // 宿主的环境
    public Activity hostAppActivity;

    @Override
    public void insertAppContext(Activity hostActivity) {
        Log.d(TAG,"---insertAppContext");
        this.hostAppActivity = hostActivity;
    }

    // 使用宿主的环境设置资源id,否则子类没法正常加载layout
    public void setContentView(int layoutId){
        hostAppActivity.setContentView(layoutId);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"---onCreate");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {
        Log.d(TAG,"---onStart");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {
        Log.d(TAG,"---onResume");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {
        Log.d(TAG,"---onStop");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
        Log.d(TAG,"---onDestroy");
    }

    public View findViewById(int viewId){
        return hostAppActivity.findViewById(viewId);
    }

    @Override
    public void startActivity(Intent intent) {
        Intent intentNew = new Intent();
        intentNew.putExtra("className",intent.getComponent().getClassName());
        hostAppActivity.startActivity(intentNew);

    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intentNew = new Intent();
        intentNew.putExtra("className", service.getComponent().getClassName());
        // 就是为了传递className
        return hostAppActivity.startService(intentNew);
    }

    // 注册广播，用宿主的环境
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
        return hostAppActivity.registerReceiver(receiver, filter);
    }
}

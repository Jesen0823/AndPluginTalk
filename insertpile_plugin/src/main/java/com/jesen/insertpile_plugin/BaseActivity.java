package com.jesen.insertpile_plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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
}

package com.jesen.insertpile_host;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="[宿主]MainActivity";

    private Button load_plug_btn;
    private Button open_plug_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"----onCreate");

        load_plug_btn = findViewById(R.id.load_plug_btn);
        open_plug_btn = findViewById(R.id.open_plug_btn);


        load_plug_btn.setOnClickListener(view->loadPlugin());

        open_plug_btn.setOnClickListener(view -> openPlugin());
    }

    private void loadPlugin() {
        PluginManager.getInstance(this).loadPlugin();
    }

    private void openPlugin() {
        // 获取插件的Activity
        String PATH = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        Log.d(TAG,"---PATH:"+PATH);
        File file = new File(PATH, "plug.apk");
        String filePath = file.getAbsolutePath();

        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        // 取插件第0个Activity
        ActivityInfo activityInfo = packageInfo.activities[0];

        // 启动代理Activity
        Intent intent = new Intent(this,ProxyActivity.class);
        intent.putExtra("className",activityInfo.name);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"----onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"----onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"----onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"----onDestroy");
    }
}
package com.jesen.insertpile_host;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.jesen.insertpile_standard.ActivityInterface;

import java.lang.reflect.Constructor;

/**
 * 代理/占位，用来启动插件Activity
 */
public class ProxyActivity extends Activity {

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getPlugResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getClassLoader();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        // 真正加载插件Activity
        String className = null;
        className = getIntent().getStringExtra("className");

        try {
            Class<?> pluginActivityClazz = getClassLoader().loadClass(className);
            // 实例化
            Constructor<?> constructor = pluginActivityClazz.getConstructor(new Class[]{});
            Object plugActivity = constructor.newInstance(new Object[]{});
            ActivityInterface activityInterface = (ActivityInterface) plugActivity;
            // 宿主的环境注入给插件
            activityInterface.insertAppContext(this);
            // 执行插件的onCreate()
            Bundle bundle = new Bundle();
            bundle.putString("pwd", "Come from Host Proxy.");
            activityInterface.onCreate(bundle);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        // 跳转自身，但是className不同
        Intent proxyIntent = new Intent(this, ProxyActivity.class);
        proxyIntent.putExtra("className", className);
        super.startActivity(proxyIntent);
    }
}
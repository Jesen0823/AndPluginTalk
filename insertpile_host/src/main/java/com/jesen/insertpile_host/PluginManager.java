package com.jesen.insertpile_host;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {

    private static final String TAG = "[宿主]PluginManager";

    private String PATH;

    private static PluginManager instance;

    private Context context;
    private DexClassLoader dexClassLoader;
    private Resources plugResources;

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }
        return instance;
    }

    private PluginManager(Context context) {
        this.context = context;
        PATH = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 加载插件 Activity & layout
     */
    public void loadPlugin() {
        try {
            File file = new File(PATH, "plug.apk");
            if (!file.exists()) {
                Log.d(TAG, " 插件apk不存在");
                return;
            }
            String plugPath = file.getAbsolutePath();
            Log.d(TAG,"插件路径："+plugPath);

            // DexClassLoader需要指定缓存目录
            File tmpDir = context.getDir("pDir", Context.MODE_PRIVATE);
            dexClassLoader = new DexClassLoader(plugPath,tmpDir.getAbsolutePath(),
                    null,context.getClassLoader());

            /**
             *  加载插件里的布局文件
             */
            // 获取资源管理器, 也可以用 context.getAssets();
            AssetManager assetManager = AssetManager.class.newInstance();
            // addAssetPathMethod用来添加插件包路径，以便加载插件包资源,String.class表示有一个String参数
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager,plugPath);
            // 宿主的资源配置信息
            Resources r = context.getResources();
            // 参数2参数3是资源的配置信息
            // 该resource会加载插件里资源，用有了宿主的资源配置
            plugResources = new Resources(assetManager, r.getDisplayMetrics(),r.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader(){
        return dexClassLoader;
    }

    public Resources getPlugResources(){
        return plugResources;
    }
}

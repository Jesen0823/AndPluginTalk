package com.jesen.insertpile_host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
            Log.d(TAG, "插件路径：" + plugPath);

            // DexClassLoader需要指定缓存目录
            File tmpDir = context.getDir("pDir", Context.MODE_PRIVATE);
            dexClassLoader = new DexClassLoader(plugPath, tmpDir.getAbsolutePath(),
                    null, context.getClassLoader());

            /**
             *  加载插件里的布局文件
             */
            // 获取资源管理器, 也可以用 context.getAssets();
            AssetManager assetManager = AssetManager.class.newInstance();
            // addAssetPathMethod用来添加插件包路径，以便加载插件包资源,String.class表示有一个String参数
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, plugPath);
            // 宿主的资源配置信息
            Resources r = context.getResources();
            // 参数2参数3是资源的配置信息
            // 该resource会加载插件里资源，用有了宿主的资源配置
            plugResources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader() {
        return dexClassLoader;
    }

    public Resources getPlugResources() {
        return plugResources;
    }

    /**
     * 反射系统源码，解析apk文件信息
     */
    public void parseApkAction() {
        try {
            // 插件包路径
            File file = new File(PATH, "plug.apk");
            if (!file.exists()) {
                Log.w(TAG, "plugin apk not exists...");
                return;
            }

            // 实例化 PackageParser对象
            Class packageParserClass = Class.forName("android.content.pm.PackageParser");
            Object packageParser = packageParserClass.newInstance();

            // 1.执行此方法 public Package parsePackage(File packageFile, int flags)是为了拿到Package
            Method packageParserMethod = packageParserClass.getMethod("parsePackage", File.class, int.class); // 类类型
            Object mPackage = packageParserMethod.invoke(packageParser, file, PackageManager.GET_ACTIVITIES);  // 执行方法

            // 得到 receivers
            Field receiversField = mPackage.getClass().getDeclaredField("receivers");
            // receivers 本质就是 ArrayList 集合
            Object receivers = receiversField.get(mPackage);

            ArrayList arrayList = (ArrayList) receivers;

            // 此Activity 不是组件的Activity，是PackageParser里面的内部类
            // mActivity --> <receiver android:name=".StaticReceiver">
            for (Object mActivity : arrayList) {
                // 获取 <intent-filter>
                // 通过反射拿到 intents,即多个<intent-filter>的数组
                // Component是内部类
                Class mComponentClass = Class.forName("android.content.pm.PackageParser$Component");
                Field intentsField = mComponentClass.getDeclaredField("intents");
                ArrayList<IntentFilter> intents = (ArrayList) intentsField.get(mActivity);

                // 要拿到广播接收者 android:name=".pluginStaticReceiver"
                // 那么需要拿到 ActivityInfo
                Class mPackageUserState = Class.forName("android.content.pm.PackageUserState");

                Class mUserHandle = Class.forName("android.os.UserHandle");
                int userId = (int) mUserHandle.getMethod("getCallingUserId").invoke(null);

                /**
                 * 执行方法generateActivityInfo，就能拿到 ActivityInfo
                 * public static final ActivityInfo generateActivityInfo(Activity a, int flags,
                 *             PackageUserState state, int userId)
                 */
                Method generateActivityInfoMethod
                        = packageParserClass.getDeclaredMethod("generateActivityInfo",
                        mActivity.getClass(), int.class, mPackageUserState, int.class);
                generateActivityInfoMethod.setAccessible(true);
                // 执行此方法，拿到ActivityInfo
                ActivityInfo mActivityInfo = (ActivityInfo) generateActivityInfoMethod
                        .invoke(null, mActivity, 0, mPackageUserState.newInstance(), userId);
                // 最终拿到广播接收
                String receiverClassName = mActivityInfo.name;
                Class mStaticReceiverClass = getClassLoader().loadClass(receiverClassName);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) mStaticReceiverClass.newInstance();

                for (IntentFilter intentFilter : intents) {
                    // 注册静态广播
                    context.registerReceiver(broadcastReceiver, intentFilter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

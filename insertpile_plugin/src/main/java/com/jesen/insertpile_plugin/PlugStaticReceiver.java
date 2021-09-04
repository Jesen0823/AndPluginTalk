package com.jesen.insertpile_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 插件中静态的广播接受者
 * */
public class PlugStaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"插件中静态广播受到",Toast.LENGTH_SHORT).show();
    }
}

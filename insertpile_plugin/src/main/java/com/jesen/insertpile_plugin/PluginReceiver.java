package com.jesen.insertpile_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PluginReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"插件的广播接收者受到广播",Toast.LENGTH_SHORT).show();
    }


}

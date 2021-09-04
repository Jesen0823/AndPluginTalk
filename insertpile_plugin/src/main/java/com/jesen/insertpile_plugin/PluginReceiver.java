package com.jesen.insertpile_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jesen.insertpile_standard.ReceiverInterface;

public class PluginReceiver extends BroadcastReceiver implements ReceiverInterface {

    private static final String TAG = "[插件]PluginReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"插件的广播接收者受到广播",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"---onReceive, 插件受到广播");
    }


}

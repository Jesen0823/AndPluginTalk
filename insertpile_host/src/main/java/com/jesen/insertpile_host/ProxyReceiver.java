package com.jesen.insertpile_host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProxyReceiver extends BroadcastReceiver {
    private static final String TAG = "[宿主]ProxyReceiver";

    private String pluginReceiverName;

    public ProxyReceiver(String pluginReceiverName) {
        this.pluginReceiverName = pluginReceiverName;
        Log.d(TAG,"---宿主Receiver拿到了插件Receiver的类名，准备代理："+pluginReceiverName);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"---onReceive，接收到广播");
    }
}

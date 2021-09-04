package com.jesen.insertpile_plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    private TextView email;
    private Button jumpPage;
    private Button startService;
    private Button registerBroadcast;
    private Button sendBroadcast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 这里调用的是父类的
        setContentView(R.layout.plugin_main);

        // 不能用this,没有宿主运行环境
        Toast.makeText(hostAppActivity,"正常拉起插件",Toast.LENGTH_SHORT).show();

        email = (TextView) findViewById(R.id.email);

        if (savedInstanceState != null) {
            String rev = savedInstanceState.getString("fromHost");
            email.setText(rev);
        }

        jumpPage =(Button) findViewById(R.id.jumpPage);
        jumpPage.setOnClickListener(view -> {
            startActivity(new Intent(hostAppActivity,SecondActivity.class));
        });

        startService = (Button) findViewById(R.id.startService);
        startService.setOnClickListener(view ->{
            startService(new Intent(hostAppActivity,PluginService.class));
        });

        registerBroadcast = (Button) findViewById(R.id.register_broadcast);
        registerBroadcast.setOnClickListener(view -> {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.jesen.insertpile_plugin_BA");
            registerReceiver(new PluginReceiver(), intentFilter);
        });

        sendBroadcast = (Button) findViewById(R.id.send_broadcast);
        sendBroadcast.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("com.jesen.insertpile_plugin_BA");
            sendBroadcast(intent);
        });
    }
}
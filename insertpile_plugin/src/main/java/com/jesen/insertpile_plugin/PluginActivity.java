package com.jesen.insertpile_plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    private TextView email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 这里调用的是父类的
        setContentView(R.layout.plugin_main);

        // 不能用this,没有宿主运行环境
        Toast.makeText(hostAppActivity,"正常拉起插件",Toast.LENGTH_SHORT).show();

        email = hostAppActivity.findViewById(R.id.email);

        if (savedInstanceState != null) {
            String rev = savedInstanceState.getString("fromHost");
            email.setText(rev);
        }
    }
}
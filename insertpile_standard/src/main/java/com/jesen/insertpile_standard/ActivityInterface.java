package com.jesen.insertpile_standard;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public interface ActivityInterface {

    // 把宿主的环境给插件
    void insertAppContext(Activity hostActivity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onStop();

    void onDestroy();
}

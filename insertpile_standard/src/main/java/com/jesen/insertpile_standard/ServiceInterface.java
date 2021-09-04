package com.jesen.insertpile_standard;

import android.app.Service;
import android.content.Intent;

public interface ServiceInterface {
    /**
     * 把宿主(app)的环境  给  插件
     * @param appHostService
     */
    void insertAppContext(Service appHostService);

    public void onCreate();

    public int onStartCommand(Intent intent, int flags, int startId);

    public void onDestroy();
}

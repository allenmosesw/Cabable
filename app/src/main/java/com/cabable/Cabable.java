package com.cabable;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.cabable.util.MqttUtil;

/**
 * Created by AllenMosesW on 22/01/17.
 */

public class Cabable extends Application {
    private static Cabable instance;


    public Cabable() {
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        MqttUtil.getInstance(getApplicationContext());
    }
}

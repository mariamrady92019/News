package com.route.newsfri;

import android.app.Application;

import com.route.database.MyDataBase;

/**
 * Created by Mohamed Nabil Mohamed on 11/22/2019.
 * m.nabil.fci2015@gmail.com
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBase.init(this);
    }
}

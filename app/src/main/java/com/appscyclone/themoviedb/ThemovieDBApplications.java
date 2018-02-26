package com.appscyclone.themoviedb;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by TDP on 21/02/2018.
 */

public class ThemovieDBApplications  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}

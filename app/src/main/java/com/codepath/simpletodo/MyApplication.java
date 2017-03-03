package com.codepath.simpletodo;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by divine on 3/3/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}

package com.dj.caserecordchoosecomponent;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by DingJing on 2021/4/9
 * Contact me: 847467911@qq.com
 * Describe:
 */
public class AppApplication extends Application {
    // ARouter调试开关
    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebugARouter) {   // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();  // Print log
            ARouter.openDebug();// Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(AppApplication.this); // As early as possible, it is recommended to initialize in the Application
    }
}

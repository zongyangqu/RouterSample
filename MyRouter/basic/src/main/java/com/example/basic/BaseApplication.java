package com.example.basic;

import android.app.Application;

import com.example.arouter.ARouter;

/**
 * author : quzongyang
 * e-mail : quzongyang@xiaohe.com
 * time   : 2021/03/01
 * desc   :
 * version: 1.0
 */


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}

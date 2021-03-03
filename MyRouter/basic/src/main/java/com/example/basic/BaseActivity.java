package com.example.basic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : quzongyang
 * e-mail : quzongyang@xiaohe.com
 * time   : 2021/03/02
 * desc   :
 * version: 1.0
 */


public class BaseActivity extends AppCompatActivity {

    final boolean is_application = BuildConfig.is_application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean getIsApplication() {
        return is_application;
    }
}

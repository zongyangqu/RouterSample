package com.example.myrouter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.annotation.BindPath;
import com.example.arouter.ARouter;
import com.example.basic.BaseActivity;
import com.example.basic.BaseApplication;

@BindPath(key = "main/main")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean is_application = getIsApplication();
        Log.d("is_application",is_application+"");

    }

    public void jumpActivity(View view) {
        ARouter.getInstance().jumpActivity("login/login",null,this);

        //1对1的关系  1对多
//        getLifecycle().addObserver(new MyLifeCycleObserve());
    }
}

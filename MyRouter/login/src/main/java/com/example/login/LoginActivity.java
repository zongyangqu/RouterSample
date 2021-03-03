package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.annotation.BindPath;
import com.example.basic.BaseActivity;

@BindPath(key = "login/login")
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean is_application = getIsApplication();
        Log.d("is_application",is_application+"");
    }
}

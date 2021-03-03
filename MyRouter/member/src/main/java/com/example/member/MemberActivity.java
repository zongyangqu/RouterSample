package com.example.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.annotation.BindPath;
import com.example.basic.BaseActivity;

@BindPath(key = "member/member")
public class MemberActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        boolean is_application = getIsApplication();
        Log.d("is_application",is_application+"");
    }
}

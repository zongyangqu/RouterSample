package com.example.member;

import com.example.arouter.IRouter;

/**
 * author : quzongyang
 * e-mail : quzongyang@xiaohe.com
 * time   : 2021/03/02
 * desc   :
 * version: 1.0
 */


public class ActivityUtil implements IRouter {
    @Override
    public void putActivity() {
        //将当前模块中所有的Activity类对象都加入到路由表中
        com.example.arouter.ARouter.getInstance().addActivity("member/member",MemberActivity.class);
    }
}

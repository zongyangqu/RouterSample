package com.example.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import dalvik.system.DexFile;

/**
 * author : quzongyang
 * e-mail : quzongyang@xiaohe.com
 * time   : 2021/03/02
 * desc   :
 * version: 1.0
 */


public class ARouter {

    private static ARouter aRouter = new ARouter();
    private Context context;

    //装载了所有的Activity的类对象    路由表
    private HashMap<String,Class<? extends Activity>> map;

    private ARouter(){
        map = new HashMap<>();
    }

    public static  ARouter getInstance(){
        return aRouter;
    }

    public void init(Context context){
        //调用生成的工具类的方法
        this.context = context;
        List<String> className = getClassName("com.example.util");
        for (String s : className) {
            try {
                Class<?> aClass = Class.forName(s);
                Object o = aClass.newInstance();
                if(o instanceof IRouter){
                    IRouter iRouter = (IRouter) o;
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将类对象添加进路由表的方法
     * @param key
     * @param clazz
     */
    public void addActivity(String key,Class<? extends Activity> clazz){
        if(key != null && clazz !=null && !map.containsKey(key)){
            map.put(key,clazz);
        }
    }

    /**
     * 将类对象添加进路由表的方法
     *
     * @param key
     */
    public Class<? extends Activity> getActivity(String key) {
        if (key != null && map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public void jumpActivity(String key, Bundle bundle,Activity activity){
        Class<? extends Activity> activityClass = map.get(key);
        if(activityClass != null){
            Intent intent = new Intent(activity,activityClass);
            if(bundle != null){
                intent.putExtras(bundle);
            }
            activity.startActivity(intent);
        }
    }

    /**
     * 通过包名获取这个包下边的所有的类名
     * @param packageName
     * @return
     */
    private List<String> getClassName(String packageName){
        //创建一个class对象的集合
        List<String> classList = new ArrayList<String>();
        try {
            //把当前应用的apk存放路径给到dexFile
            DexFile df = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = df.entries();
            while(entries.hasMoreElements()){
                String className = entries.nextElement();
                if(className.contains(packageName)){
                    classList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classList;
    }

}

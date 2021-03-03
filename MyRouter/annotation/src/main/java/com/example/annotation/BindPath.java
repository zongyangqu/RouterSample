package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : quzongyang
 * e-mail : quzongyang@xiaohe.com
 * time   : 2021/03/02
 * desc   :
 * version: 1.0
 */

@Target(ElementType.TYPE) //生命注解的作用域
@Retention(RetentionPolicy.CLASS) //生命注解的生命周期 在编译时
public @interface BindPath {
//    String value();
        String key();
}

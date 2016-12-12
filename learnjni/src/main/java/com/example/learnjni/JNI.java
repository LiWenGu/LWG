package com.example.learnjni;

/**
 * Created by liwenguang on 2016/11/21.
 */

public class JNI {

    {
        System.loadLibrary("hello");
    }
    /**
     * 定义native方法
     * 调用C代码对应的方法
     * @return
     */

    public native String sayHello();

}

package com.example.learnjni;

/**
 * Created by liwenguang on 2016/11/21.
 */

public class JNI {

    {
        System.loadLibrary("nativehello-lib");
    }

    /**
     * 让C代码做加法计算，把结果返回
     * @param x
     * @param y
     * @return
     */
    public native int sum(int x, int y);

    /**
     * 从Java传入字符串，让C代码进行拼接
     * @param s
     * @return
     */
    public native String sayHello(String s);

    /**
     * 让C代码给每个元素都加上10
     * @param intArray
     * @return
     */
    public native int[] increaseArrayElements(int[] intArray);

    public native int checkPwd(String psw);

    public native int getPressure();

    public native void uninstall(String packName, int sdkVersion);
}

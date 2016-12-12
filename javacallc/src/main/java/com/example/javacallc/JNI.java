package com.example.javacallc;

/**
 * Created by liwenguang on 2016/11/21.
 */

public class JNI {

    {
        System.loadLibrary("hello");
    }
    /**
     * c将java的两个数字相加后返回给java
     * @param x
     * @param y
     * @return
     */
    public native int add(int x, int y);

    /**
     * C进行字符串拼接后返回给java
     * @param a
     * @return
     */
    public native String sayhello(String a);

    /**
     * 让C代码给每个元素都加上10
     * @param intArray
     * @return
     */
    public native int[] increaseArrayEles(int[] intArray);

    /**
     * 检查密码是否正确
     * @param pwd
     * @return
     */
    public native int checkPwd(String pwd);
}

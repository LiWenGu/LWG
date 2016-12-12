//
// Created by liwenguang on 2016/11/21.
//
#include <stdio.h>
#include <stdio.h>
#include <jni.h>

/**
 * jstring: 返回值
 * Java_全类名_方法名
 * JNIEnv* env: 里面有很多方法
 * jobj : 谁调用了这个方法就是谁的实例
 * 当前就是JNI.this
 */
jstring Java_com_example_learnjni_JNI_sayHello(JNIEnv* env, jobject jobj){

//    jstring     (*NewStringUTF)(JNIEnv*, const char*);
    char* text = "i am from c";
    return (*env)->NewStringUTF(env, text);

}
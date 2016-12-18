#include "com_example_learnjni_JNI.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <android/log.h>
/**
 * 把一个jstring转换成一个c语言的char* 类型.
 */
char* _JString2CStr(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env,"GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid, strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if(alen > 0) {
        rtn = (char*)malloc(alen+1); //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen]=0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba,0);
    return rtn;
}



/**
 * 把加法运算的结果返回
 */
jint Java_com_example_learnjni_JNI_sum
        (JNIEnv *env, jobject jobj, jint ji, jint jj) {

    int result = ji + jj;
    return result;
};


JNIEXPORT jstring JNICALL Java_com_example_learnjni_JNI_sayHello
        (JNIEnv * env, jobject jobj, jstring jstr){

    char* fromJava =_JString2CStr(env,jstr);   //I am from java
    //C：
    char* fromC = "add I am from C!!";
    strcat(fromJava,fromC);       //拼接函数
    return  (*env)->NewStringUTF(env,fromJava);

};

/**
 * 给传入的每个元素都加上10
 */
JNIEXPORT jintArray JNICALL Java_com_example_learnjni_JNI_increaseArrayElements
        (JNIEnv * env, jobject jobj, jintArray jarray){

    //1.得到数组的长度
    int size = (*env)->GetArrayLength(env, jarray);
    //2.得到数组的元素
    jint* intarray = (*env)->GetIntArrayElements(env, jarray, JNI_FALSE);
    //3.遍历数组，给每个元素加上10
    for (int i = 0; i < size; ++i) {
        *(intarray + i) = *(intarray + i) + 10;
    }
    //4.返回结果
    return jarray;
}

JNIEXPORT jint JNICALL Java_com_example_learnjni_JNI_checkPwd
        (JNIEnv * env, jobject jobj, jstring jstr){

    //服务器的密码是123456
    char* origin = "123456";
    char* fromUser = _JString2CStr(env, jstr);

    //函数比较字符串是否相同
    int code = strcmp(origin, fromUser);
    if (code == 0){
        return 200;
    }else{
        return 400;
    }
}

int pressure = 20;
int getPressure(){
    int increase = rand()%20;
    pressure+=increase;
    return pressure;
}

JNIEXPORT jint JNICALL
Java_com_example_learnjni_JNI_getPressure(JNIEnv *env, jobject instance) {
    int pressure = getPressure();
    return pressure;
}

JNIEXPORT void JNICALL
Java_com_example_learnjni_JNI_uninstall(JNIEnv *env, jobject instance, jstring packName_,
                                        jint sdkVersion) {
    //返回三个值，大于0，父进程的ID。等于0，子进程的ID。小于0，出错了
    int code = fork();
    char* packName = _JString2CStr(env, packName_);
    if (code == 0){
        //判断软件是否被卸载
        int flag = 1;
        while(flag){
            sleep(1);

            FILE* file = fopen("/data/data/com.example.learnjni", "r");
            if (file == NULL){  //已经被卸载了
                execlp("am", "am", "start", "--user", "0", "-a", "android.intent.action.VIEW", "-d", "http://baidu.com", (char*)NULL);
                flag = 0;  //停止循环
            }
        }
    }else if (code > 0){
        //父进程
    }else{
        //出错了
    }
};
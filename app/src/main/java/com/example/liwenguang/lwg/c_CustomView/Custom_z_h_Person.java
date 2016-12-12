package com.example.liwenguang.lwg.c_CustomView;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_h_Person {

    private String name;
    private String pinyin;

    public Custom_z_h_Person(String name) {
        this.name = name;
        this.pinyin = Custom_z_h_pinyinutil.getPinYin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}

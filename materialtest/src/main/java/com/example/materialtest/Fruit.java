package com.example.materialtest;

/**
 * Created by li-pc on 2016/12/15.
 */

public class Fruit {

    private String name;

    private int imageId;

    public Fruit(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }
}

package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by li-pc on 2016/12/12.
 */

public class Now {
    @SerializedName("tmp")
    private String temperature;

    @SerializedName("cond")
    private More more;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public More getMore() {
        return more;
    }

    public void setMore(More more) {
        this.more = more;
    }

    public static class More{
        @SerializedName("txt")
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}

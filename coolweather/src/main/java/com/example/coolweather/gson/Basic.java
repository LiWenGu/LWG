package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by li-pc on 2016/12/12.
 */

public class Basic {


    /**
     * city : 苏州
     * cnty : 中国
     * id : CN101190401
     * lat : 31.309000
     * lon : 120.612000
     * update : {"loc":"2016-12-12 20:51","utc":"2016-12-12 12:51"}
     */

    @SerializedName("city")
    private String cityName;
    @SerializedName("id")
    private String weatherId;
    private String lat;
    private String lon;
    private UpdateBean update;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class UpdateBean {
        /**
         * loc : 2016-12-12 20:51
         * utc : 2016-12-12 12:51
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }
}

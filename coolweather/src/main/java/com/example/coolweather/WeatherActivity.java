package com.example.coolweather;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    public static final String BASE_API = "http://guolin.tech/api/weather?cityid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        init();
    }

    private void init() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        if (weatherString != null){
            //有缓存直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }else{
            //无缓存时去服务器查询天气
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }

    /**
     * 根据天气ID请求城市天气信息
     */
    public void requestWeather(final String weatherId){
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String key = appInfo.metaData.getString("key");
        String weatherUrl = BASE_API + weatherId + "&key=" + key;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.getStatus())){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else{
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    /**
     * 处理 并展示Weather实体类中的数据
     * @param weather
     */
    private void showWeatherInfo(Weather weather){
        String cityName = weather.getBasic().getCityName();
        String updateTime = weather.getBasic().getUpdate().getLoc().split(" ")[1];
        String degree = weather.getNow().getTemperature() + "°C";
        String weatherInfo = weather.getNow().getMore().getInfo();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.getForecastList()){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.getDate());
            infoText.setText(forecast.getMore().getInfo());
            maxText.setText(forecast.getTemperature().getMax());
            minText.setText(forecast.getTemperature().getMin());
            forecastLayout.addView(view);
        }
        if (weather.getAqi() != null){
            aqiText.setText(weather.getAqi().getCity().getAqi());
            pm25Text.setText(weather.getAqi().getCity().getPm25());
        }
        String comfore = "舒适度: " + weather.getSuggestion().getComfort().getInfo();
        String carWash = "洗车指数: " + weather.getSuggestion().getCarWash().getInfo();
        String sport = "运动建议: " + weather.getSuggestion().getSport().getInfo();
        comfortText.setText(comfore);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}

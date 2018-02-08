package net.simplifiedcoding.navigationdrawerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static CityWeatherModel cityWeatherMode;

    public static CityWeatherModel getCityWeatherMode() {
        return cityWeatherMode;
    }

    public void setCityWeatherMode(CityWeatherModel cityWeatherMode) {
        this.cityWeatherMode = cityWeatherMode;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        // Extract data included in the Intent
       /* String intentData = intent.getStringExtra("message");
        String intentTemp = intent.getStringExtra("temperature");
        String intentImage = intent.getStringExtra("picture");*/



        CityWeatherModel cityWeatherModel = intent.getParcelableExtra("city");// new CityWeatherModel(null,intentData,intentImage,Double.valueOf(intentTemp),true);

        setCityWeatherMode(cityWeatherModel);


    }


}

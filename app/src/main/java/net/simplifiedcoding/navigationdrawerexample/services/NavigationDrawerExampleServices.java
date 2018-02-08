package net.simplifiedcoding.navigationdrawerexample.services;


import net.simplifiedcoding.navigationdrawerexample.model.WeatherDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NavigationDrawerExampleServices {

    @GET(APIConstants.GROUP)
    Call<WeatherDataModel> getWeatherDataModel(@Query("id") String cityIds,
                                               @Query("units") String units,
                                               @Query("appid") String apiKey);
}

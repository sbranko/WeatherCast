package net.simplifiedcoding.navigationdrawerexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.simplifiedcoding.navigationdrawerexample.model.WeatherDataModel;
import net.simplifiedcoding.navigationdrawerexample.services.APIConstants;
import net.simplifiedcoding.navigationdrawerexample.services.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu1 extends Fragment {

    private static final String TAG = Menu1.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private List<CityWeatherModel> listOfCities = new ArrayList<>();
    private Call<WeatherDataModel> mGetCityWeatherRequest;
    private WeatherDataModel mWeatherModelData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_1, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        getCityWeather();

        return rootView;

    }

    private void getCityWeather() {

        String ids = "4957962,3194360,5106292";
        String units = "metrics";
        String apiKey = "1494309c6e0aa312108068ed99c3828e";

        mGetCityWeatherRequest = NetworkHelper.getFurnichaService(APIConstants.BASE_URL).getWeatherDataModel(ids, units, apiKey);
        mGetCityWeatherRequest.enqueue(new Callback<WeatherDataModel>() {
            @Override
            public void onResponse(Call<WeatherDataModel> call, Response<WeatherDataModel> response) {
                if (response.isSuccessful()) {
                    mWeatherModelData = response.body();

                    if (mWeatherModelData != null) {
                        for (int i = 0; i < mWeatherModelData.getList().size(); i++) {
                            String icon = mWeatherModelData.getList().get(i).getWeather().get(0).getIcon();
                            CityWeatherModel model1 = new CityWeatherModel(
                                    mWeatherModelData.getList().get(i).getWeather().get(0).getDescription(),
                                    mWeatherModelData.getList().get(i).getName(),
                                    "http://openweathermap.org/img/w/" + icon + ".png"
                                    , mWeatherModelData.getList().get(i).getMain().getTemp()
                                    , true);
                            listOfCities.add(model1);
                        }
                    }

                    MyRecyclerAdapter recyclerAdapter = new MyRecyclerAdapter(getActivity(), listOfCities);

                    mRecyclerView.setAdapter(recyclerAdapter);
                } else {
                    Log.d(TAG, "onResponse:unSuccessful " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<WeatherDataModel> call, Throwable t) {
                Log.d(TAG, "onFailure:exception " + t.getLocalizedMessage() + " URL: " + call.request().url().toString());
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGetCityWeatherRequest != null && mGetCityWeatherRequest.isCanceled()) {
            mGetCityWeatherRequest.cancel();
        }
    }
}

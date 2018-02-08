package net.simplifiedcoding.navigationdrawerexample.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elpatron on 11/3/2017.
 */

public class NetworkHelper {

    private static final HashMap<String, NavigationDrawerExampleServices> sFurnichaService = new HashMap<>();
    private static final String TAG = NetworkHelper.class.getSimpleName();
    private static final int CONNECTION_TIMEOUT = 300;
    private static final int READ_TIMEOUT = 300;


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static NavigationDrawerExampleServices initializeService() {
        try {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(NavigationDrawerExampleServices.class);
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred with message: ", e);
        }
        return null;
    }

    private static NavigationDrawerExampleServices initializeCustomService(String url) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(NavigationDrawerExampleServices.class);
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred with message: ", e);
        }
        return null;
    }


    public static NavigationDrawerExampleServices getFurnichaService(String url) {
        NavigationDrawerExampleServices ret;
        if (sFurnichaService.containsKey(url)) {
            ret = sFurnichaService.get(url);
        } else {
            if (url.equals(APIConstants.BASE_URL)) {
                ret = initializeService();
            } else {
                ret = initializeCustomService(url);
            }
            sFurnichaService.put(url, ret);
        }

        return ret;
    }
}

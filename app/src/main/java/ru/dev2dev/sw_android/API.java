package ru.dev2dev.sw_android;

import android.os.Build;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Request;

/**
 * Created by Dmitriy on 27.01.2016.
 */
public class API {

    public static final String API_URL = "http://swapi.co/api";

    private static final RestAdapter adapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(API_URL)
            .setClient(new OkClient())
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
//                    request.addHeader("Accept", "application/json");
//                    request.addHeader("Content-Type", "application/json");
                    request.addPathParam("User-Agent", "swapi-Java-SWAPI-JAVA");
                }
            })
            .build();

    private static final APIMethods apiMethods = adapter.create(APIMethods.class);

    public static APIMethods getApi() {
        return apiMethods;
    }

}

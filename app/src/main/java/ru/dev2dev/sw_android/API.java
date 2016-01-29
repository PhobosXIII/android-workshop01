package ru.dev2dev.sw_android;

import android.os.Build;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Dmitriy on 27.01.2016.
 */
public class API {

    public static final String API_URL = "http://swapi.co/api";
    public static final String PEOPLE_PATH = "/people/";

    private static final RestAdapter adapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(API_URL)
            .setClient(new OkClient())
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", "ws-sw-android-" + Build.VERSION.RELEASE);
                }
            })
            .build();

    private static final APIMethods apiMethods = adapter.create(APIMethods.class);

    public static APIMethods getApi() {
        return apiMethods;
    }

}

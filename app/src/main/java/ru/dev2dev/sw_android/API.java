package ru.dev2dev.sw_android;

import android.os.Build;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class API {
    public static final String API_URL = "http://swapi.co/api";

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

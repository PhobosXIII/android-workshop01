package ru.dev2dev.sw_android;

import android.os.Build;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {
    public static final String API_URL = "http://swapi.co/api";

    private static RestClient restClient;
    private SwApi api;

    public static RestClient getRestClient() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    private RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
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

        api = restAdapter.create(SwApi.class);
    }

    public SwApi getApi() {
        return api;
    }
}

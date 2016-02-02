package ru.dev2dev.sw_android;

import retrofit.Callback;
import retrofit.http.GET;

public interface SwApi {
    @GET("/people/")
    void getPeople(Callback<PeopleResponse> callback);
}

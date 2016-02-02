package ru.dev2dev.sw_android;

import retrofit.Callback;
import retrofit.http.GET;

public interface APIMethods {

    @GET("/people/")
    void getPeople(Callback<PeopleResponse> responseCallback);
}

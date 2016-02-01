package ru.dev2dev.sw_android;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Dmitriy on 27.01.2016.
 */
public interface APIMethods {

    @GET(API.PEOPLE_PATH)
    PeopleResponse getPeople();

}

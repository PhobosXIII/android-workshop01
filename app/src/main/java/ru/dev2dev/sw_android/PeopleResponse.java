package ru.dev2dev.sw_android;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dmitriy on 27.01.2016.
 */
public class PeopleResponse {

    @SerializedName("results")
    private ArrayList<Person> persons;

    public ArrayList<Person> getPersons() {
        return persons;
    }
}

package ru.dev2dev.sw_android;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PeopleResponse {

    @SerializedName("results")
    private ArrayList<Person> persons;

    public ArrayList<Person> getPersons() {
        return persons;
    }
}

package ru.dev2dev.sw_android;

import java.io.Serializable;
import java.util.ArrayList;

public class PeopleResult implements Serializable {
    private static final long serialVersionUID = 13L;

    private ArrayList<Person> people;
    private String error;

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

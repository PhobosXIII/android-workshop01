package ru.dev2dev.sw_android;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private static final long serialVersionUID = 13L;

    private String name;
    private String height;
    private String mass;
    private String eyeColor;
    private String birthYear;
    private String gender;

    public Person(String name, String height, String mass, String eyeColor, String birthYear, String gender) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.eyeColor = eyeColor;
        this.birthYear = birthYear;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Person fromJson(JSONObject json) throws JSONException {
        String name = json.getString("name");
        String height = json.getString("height");
        String mass = json.getString("mass");
        String eyeColor = json.getString("eye_color");
        String birthYear = json.getString("birth_year");
        String gender = json.getString("gender");

        return new Person(name, height, mass, eyeColor, birthYear, gender);
    }

    public static ArrayList<Person> getList(String json) {
        ArrayList<Person> people = null;

        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray results = jsonObject.getJSONArray("results");
                people = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    Person person = Person.fromJson(results.getJSONObject(i));
                    people.add(person);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return people;
    }
}

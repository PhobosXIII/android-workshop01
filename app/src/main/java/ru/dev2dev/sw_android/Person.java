package ru.dev2dev.sw_android;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Person implements Serializable {
    private static final long serialVersionUID = 13L;

    private String name;
    private String height;
    private String mass;
    private String eyeColor;
    private String birthYear;
    private String gender;

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

    public static ArrayList<Person> getList(String json) throws JSONException {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        JsonParser parser = new JsonParser();
        JsonArray results = parser.parse(json).getAsJsonObject().getAsJsonArray("results");
        Type collectionType = new TypeToken<ArrayList<Person>>(){}.getType();
        return gson.fromJson(results, collectionType);
    }
}

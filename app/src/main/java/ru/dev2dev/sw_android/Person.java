package ru.dev2dev.sw_android;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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

    public String getHeight() {
        return height;
    }

    public String getMass() {
        return mass;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ArrayList<Person> getList(String json) throws JsonSyntaxException {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        JsonParser parser = new JsonParser();
        JsonArray results = parser.parse(json).getAsJsonObject().getAsJsonArray("results");
        Type collectionType = new TypeToken<ArrayList<Person>>(){}.getType();
        return gson.fromJson(results, collectionType);
    }
}

package ru.dev2dev.sw_android;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dmitriy on 27.01.2016.
 */
public class Person implements Serializable {

    private String name;
    private String height;
    private String mass;
    @SerializedName("eye_color")
    private String eyeColor;
    @SerializedName("birth_year")
    private String birthYear;
    private String gender;

    public Person() {}

    public Person(String name, String height, String mass, String eyeColor, String birthYear,
                  String gender) {
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
}

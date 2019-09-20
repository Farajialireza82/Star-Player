package com.example.galaxyplayer.Objects;

import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("name")
    public String name;

    @SerializedName("birth_year")
    public String birth_year;

    public String getBirth_year() {
        return birth_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name + " - " +birth_year;
    }

}

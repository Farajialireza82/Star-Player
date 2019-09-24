package com.example.galaxyplayerkotlin.Retrofit;

import com.example.galaxyplayerkotlin.Objects.People;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SwapiClient {

    @GET("/api/people")
    Call<People> getPeople();


}

package com.example.galaxyplayer.Retrofit;

import com.example.galaxyplayer.Objects.People;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SwapiClient {

    @GET("/api/people")
    Call<People> getPeople();


}

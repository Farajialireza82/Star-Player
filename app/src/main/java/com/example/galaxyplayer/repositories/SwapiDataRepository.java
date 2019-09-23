package com.example.galaxyplayer.repositories;

import android.util.Log;

import com.example.galaxyplayer.Objects.People;
import com.example.galaxyplayer.Objects.Person;
import com.example.galaxyplayer.Retrofit.ServiceGenerator;
import com.example.galaxyplayer.Retrofit.SwapiClient;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwapiDataRepository {

    private static SwapiDataRepository instance;
    private ArrayList<String> dataSet = new ArrayList<>();

    private static final String TAG = "SwapiDataRepository";

    public static SwapiDataRepository getInstance() {

        if (instance == null) {

            instance = new SwapiDataRepository();

        }

        return instance;

    }

    public MutableLiveData<List<String>> getFinalArray() {

        setupArrayList();
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;


    }

    public void setupArrayList() {

        SwapiClient swapiClient = ServiceGenerator.createService(SwapiClient.class);

        Call<People> peopleCall = swapiClient.getPeople();

        peopleCall.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {

                getPeopleInfo(response);

            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.toString());


            }
        });


    }

    public void getPeopleInfo(Response<People> response) {

        List<Person> personList = response.body().getResults();

        for (int i = 0; i < personList.size(); i++) {

            dataSet.add(personList.get(i).toString());

        }
    }


}

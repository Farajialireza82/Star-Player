package com.example.galaxyplayer.repositories;

import android.app.Application;
import android.util.Log;

import com.example.galaxyplayer.Objects.MusicModel;
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

public class SwapiPeopleListRepository {

    private static SwapiPeopleListRepository instance;
    private ArrayList<Person> peopleList /*MusicModels*/ = new ArrayList<>();

    MutableLiveData<ArrayList<Person>> data = new MutableLiveData<>();


    private static final String TAG = "SwapiPeopleListRepo";

    public static SwapiPeopleListRepository getInstance() {

        if (instance == null) {

            instance = new SwapiPeopleListRepository();

        }

        return instance;

    }


    public MutableLiveData<ArrayList<Person>> getData(Application application) {

        askApiForInfo();

        return data;

    }

    private void askApiForInfo() {

        SwapiClient swapiClient = ServiceGenerator.createService(SwapiClient.class);

        Call<People> peopleCall = swapiClient.getPeople();

        peopleCall.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {

                List<Person> personList = response.body().getResults();

                for (int i = 0; i < personList.size(); i++) {

                    Log.d(TAG, "onResponse: personList Result added to peopleInfo ArrayList " + personList.get(i).getName());

                    peopleList.add(personList.get(i));

                    Log.d(TAG, "onResponse: on 2nd that is " + peopleList.get(i));

                }

                ArrayList<Person> finalPeopleArray ;

                finalPeopleArray = peopleList;

                data.postValue(finalPeopleArray);


            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

                //   Log.d(TAG, "onFailure " + t.toString());

            }
        });

    }
}


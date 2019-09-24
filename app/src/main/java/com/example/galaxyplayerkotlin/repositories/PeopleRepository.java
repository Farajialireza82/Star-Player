package com.example.galaxyplayerkotlin.repositories;

import com.example.galaxyplayerkotlin.Objects.People;
import com.example.galaxyplayerkotlin.Objects.Person;
import com.example.galaxyplayerkotlin.Retrofit.ServiceGenerator;
import com.example.galaxyplayerkotlin.Retrofit.SwapiClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleRepository {

    private static PeopleRepository instance;
    private List<String> dataSet = new ArrayList<>();

    public static PeopleRepository getInstance(){
        if(instance == null){

            instance = new PeopleRepository();
        }
        return instance;
    }

    public MutableLiveData<List<String>> getPeopleInfo(){

        findPeopleInfo();

        MutableLiveData<List<String>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }


    public void findPeopleInfo(){

        SwapiClient swapiClient = ServiceGenerator.createService(SwapiClient.class);

        Call<People> peopleCall = swapiClient.getPeople();

        peopleCall.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {

                askForPeopleInfo(response);

            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {


                if(t instanceof UnknownHostException){

                  //  tryAgainButton.setVisibility(View.VISIBLE);

                 //   Toast.makeText(recyclerView.getContext(), "Please check you Connection and try again", Toast.LENGTH_SHORT).show();

                }else{
                   // Toast.makeText(recyclerView.getContext() , "unknown Error" + t.toString() , Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void askForPeopleInfo(Response<People> response) {

        List<Person> personList = response.body().getResults();

        for (int i = 0; i < personList.size(); i++) {

            dataSet.add(personList.get(i).getName());


        }
    }




}

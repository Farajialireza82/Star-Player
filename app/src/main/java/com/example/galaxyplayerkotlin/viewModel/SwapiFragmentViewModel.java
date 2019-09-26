package com.example.galaxyplayerkotlin.viewModel;

import android.app.Application;

import com.example.galaxyplayerkotlin.Objects.Person;
import com.example.galaxyplayerkotlin.repositories.SwapiPeopleListRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SwapiFragmentViewModel extends AndroidViewModel {

    public SwapiFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    MutableLiveData<ArrayList<Person>> people;

    public void InitializeProcess(SwapiPeopleListRepository swapiPeopleListRepository) {

        swapiPeopleListRepository = SwapiPeopleListRepository.getInstance();

        people = swapiPeopleListRepository.getData(getApplication());


    }

    public LiveData<ArrayList<Person>> reciveSongs() {

        return people;
    }
}
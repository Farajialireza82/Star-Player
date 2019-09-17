package com.example.galaxyplayer.viewModels;

import com.example.galaxyplayer.repositories.PeopleRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SwapiFragmentViewModel extends ViewModel {

    private MutableLiveData<List<String>> mutableLiveData;
    private PeopleRepository peopleRepository;

    public void init(){
        if(peopleRepository != null){
            return;
        }

        peopleRepository = PeopleRepository.getInstance();
        mutableLiveData = peopleRepository.getPeopleInfo();


    }

    public LiveData<List<String>> getPeopleInfo() {
        return mutableLiveData;
    }


}

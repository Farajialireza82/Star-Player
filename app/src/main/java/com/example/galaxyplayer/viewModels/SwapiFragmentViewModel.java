package com.example.galaxyplayer.viewModels;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SwapiFragmentViewModel extends ViewModel {

    private MutableLiveData<List<String>> mutableLiveData;

    public LiveData<List<String>> getPeopleInfo() {
        return mutableLiveData;
    }


}

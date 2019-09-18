package com.example.galaxyplayer.viewModel;

import com.example.galaxyplayer.repositories.SwapiDataRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SwapiFragmentViewModel extends ViewModel {

    MutableLiveData<List<String>> stringsData;
    public SwapiDataRepository swapiDataRepository = new SwapiDataRepository();

    public void init() {

        if (stringsData != null) {
            return;
        }

        swapiDataRepository = SwapiDataRepository.getInstance();

        stringsData = swapiDataRepository.getFinalArray();
    }

    public LiveData<List<String>> getSwapiStringInfo() {

        return stringsData;

    }

}

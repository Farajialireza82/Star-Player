package com.example.galaxyplayerkotlin.viewModel;

import android.app.Application;

import com.example.galaxyplayerkotlin.Objects.MusicModel;
import com.example.galaxyplayerkotlin.repositories.SongListRepo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SongListFragmentViewModel extends AndroidViewModel {

    public SongListFragmentViewModel(@NonNull Application application) {
        super(application);
    }


    MutableLiveData<ArrayList<MusicModel>> songs;

   // public SongListRepo songListRepo = new SongListRepo();

    public void InitializeProcess(SongListRepo songListRepo) {

        songListRepo = SongListRepo.getInstance();

        songs = songListRepo.getMusicData(getApplication());


    }


    public LiveData<ArrayList<MusicModel>> reciveSongs() {

        return songs;
    }

}
package com.example.galaxyplayerkotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.galaxyplayerkotlin.Objects.MusicModel
import com.example.galaxyplayerkotlin.repositories.SongListRepo.Companion.INSTANCE
import java.util.*

class SongListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var songs: MutableLiveData<ArrayList<MusicModel>>

    fun initializeProcess() {
        val songListRepo = INSTANCE
        songs = songListRepo.getMusicData(getApplication())
    }

    fun receiveSongs(): LiveData<ArrayList<MusicModel>> {
        return songs
    }
}

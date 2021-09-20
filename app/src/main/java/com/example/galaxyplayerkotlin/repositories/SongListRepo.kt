package com.example.galaxyplayerkotlin.repositories

import android.app.Application
import android.provider.MediaStore
import android.provider.MediaStore.Audio.AudioColumns
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.galaxyplayerkotlin.Objects.MusicModel
import java.util.*

class SongListRepo {
    private val musicModels = ArrayList<MusicModel>()
    fun getMusicData(application: Application): MutableLiveData<ArrayList<MusicModel>> {
        getSongs(application)
        val data = MutableLiveData<ArrayList<MusicModel>>()
        data.value = musicModels
        return data
    }

    private fun getSongs(application: Application) {
        var title: String
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            AudioColumns.DATA,
            AudioColumns.ARTIST,
            AudioColumns.DURATION
        )
        val audioCursor = application.applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj,
            null, null, null
        )
        if (audioCursor != null) {
            if (audioCursor.moveToFirst()) {
                do {
                    val music = MusicModel("null", "null", "null", "null")
                    try {
                        Log.i(
                            "activity0101",
                            " we should see a getting music text in the log text "
                        )
                        val songTitle =
                            audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                        title = songTitle.replace(".mp3".toRegex(), "")
                        music.title = title
                        Log.i("activity0101", " we should see a title added text in the log text ")
                    } catch (e: Exception) {
                        Log.i("activity0101", " there was an Exeption while setting the title ")
                        Log.i("activity0101", " we should see a error in the log text ")
                        Log.i("activity0101", " error is $e")
                        music.title = "Unknown"
                    }
                    try {
                        val songArtist =
                            audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                        music.artist = songArtist
                    } catch (e: Exception) {
                        music.artist = "Unknown"

                    }
                    try {
                        val msDuration =
                            audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                        val seconds = msDuration.toInt() % 60000
                        val songDuration =
                            (msDuration.toInt() / 60000).toString() + ":" + seconds.toString().substring(0, 2)

                        music.duration = songDuration
                    } catch (e: Exception) {
                        music.duration = ""

                    }
                    music.path =
                        audioCursor.getString(audioCursor.getColumnIndexOrThrow(AudioColumns.DATA))

                    musicModels.add(music)
                } while (audioCursor.moveToNext())
            }
        }
        audioCursor!!.close()
    }
    companion object {
        val INSTANCE = SongListRepo()
    }
}
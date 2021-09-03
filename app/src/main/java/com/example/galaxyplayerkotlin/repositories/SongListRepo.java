package com.example.galaxyplayerkotlin.repositories;

import android.app.Application;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.galaxyplayerkotlin.Objects.MusicModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;

public class SongListRepo {


    private static SongListRepo instance;
    private ArrayList<MusicModel> musicModels = new ArrayList<>();

    private static final String TAG = "SongListRepo";

    public static SongListRepo getInstance(){

        if(instance == null){

            instance = new SongListRepo();

        }

        return instance;

    }

    public MutableLiveData<ArrayList<MusicModel>> getMusicData(Application application){

        getSongs(application);

        MutableLiveData <ArrayList<MusicModel>> data = new MutableLiveData<>();

        data.setValue(musicModels);
        return data;

    }

    private void getSongs(Application application) {

        String title;


        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ARTIST, MediaStore.Audio.AudioColumns.DURATION};

        Cursor audioCursor = application.getApplicationContext().getContentResolver().query

                (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj,

                        null, null, null);

        if (audioCursor != null) {

            if (audioCursor.moveToFirst()) {

                do {

                    MusicModel music = new MusicModel();

                    try {

                        Log.i("activity0101", " we should see a getting music text in the log text ");

                        String songTitle = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));


                        title = songTitle.replaceAll(".mp3", "");

                        music.setTitle(title);


                        Log.i("activity0101", " we should see a title added text in the log text ");


                    } catch (Exception e) {

                        Log.i("activity0101", " there was an Exeption while setting the title ");


                        Log.i("activity0101", " we should see a error in the log text ");

                        Log.i("activity0101", " error is " + e.toString());

                        music.setTitle("Unknown");

                    }
                    try {

                        String songArtist = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

                        music.setArtist(songArtist);


                    } catch (Exception e) {

                        music.setArtist("Unknown");

                        Log.d(TAG, "getSongs: setArtist= ERROR " + e.getLocalizedMessage() );

                    }

                    try {

                        String msDuration = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                        String seconds = Integer.valueOf(msDuration)%60000 +"";

                        String songDuration = Integer.valueOf(msDuration)/60000 + ":" + seconds.substring(0,2);
                        Log.d(TAG, "getSongs: setDuration: Duration = " + songDuration);

                        music.setDuration(songDuration);



                    } catch (Exception e) {

                        music.setDuration("");

                        Log.d(TAG, "getSongs: setDuration= ERROR " + e.getLocalizedMessage() );


                    }


                    music.setPath(audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)));

                    Log.i("activity0101", " we should see music path set text in the log text ");

                    Log.i("activity0101", " we should see music added set text in the log text ");

                    Log.i("do_in_background", "final song : " + music.toString());

                    Log.i("do_in_background", "final deliverer : " + music.toString());

                    musicModels.add(music);


                } while (audioCursor.moveToNext());
            }
        }
        audioCursor.close();

    }

}

package com.example.galaxyplayer;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static boolean play;

    ArrayList<String> songNames;

    ArrayList<MusicModel> songs;

    ArrayList<String> songUrlList;

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView songUrls;

    TextView logs;

    LinearLayoutManager manager;

    GridLayoutManager gridlayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = true;

        logs = findViewById(R.id.logs);

        Log.i("activity0101", "We Should see a start text");

        logs.setText("App Started");

        songUrls = findViewById(R.id.recycler_view);

        manager = new LinearLayoutManager(this);

        gridlayoutManager = new GridLayoutManager(this, 3);

        songUrls.setLayoutManager(gridlayoutManager);

        songNames = new ArrayList<>();

        songs = new ArrayList<>();

        songUrlList = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(songs);

        songUrls.setAdapter(recyclerViewAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {

            Log.i("activity0101", "music setter may start now");

            AsyncTaskMusicSeter musicSetter = new AsyncTaskMusicSeter(this);

            musicSetter.execute(recyclerViewAdapter);

            Log.i("activity0101", "we should see the song list");

        } else {

            Toast.makeText(this, "In order to load your musics , App needs Access to You Data", Toast.LENGTH_LONG).show();

        }

    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //setmusicsOnRecyclerBar();

                } else {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


    public class AsyncTaskMusicSeter extends android.os.AsyncTask<RecyclerViewAdapter, PostMan, RecyclerViewAdapter> {

        private WeakReference<MainActivity> activityWeakReference;
        RecyclerViewAdapter viewAdapter;
        String title;
        MusicModel deliverer;



        AsyncTaskMusicSeter(MainActivity activity) {

            activityWeakReference = new WeakReference<MainActivity>(activity);

        }

        @Override
        protected RecyclerViewAdapter doInBackground(RecyclerViewAdapter... adapters) {

            Integer i = 0;

             viewAdapter = adapters[0];

            //activity.logs.setText("permission checked");

            Log.i("activity0101", "we should see a permission text in log text");

            String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.AudioColumns.DATA};

            Cursor audioCursor = getContentResolver().query

                    (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj,

                            null, null, null);

            if (audioCursor != null) {

                if (audioCursor.moveToFirst()) {

                    do {

                        MusicModel music = new MusicModel();

                        try {

                          //  activity.logs.setText("getting music");

                            Log.i("activity0101", " we should see a getting music text in the log text ");

                            String songTitle = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));

                             title = songTitle.replaceAll(".mp3", "");

                            music.setTitle(title);

                          //  activity.logs.setText("title added ");

                            Log.i("activity0101", " we should see a title added text in the log text ");

                            PostMan postMan = new PostMan();
                            postMan.setStatus("add_title");

                            publishProgress(postMan);

                        } catch (Exception e) {

                            Log.i("activity0101", " there was an Exeption while setting the title ");


                         //   activity.logs.setText("error at setting song title " + e.toString());

                            Log.i("activity0101", " we should see a error in the log text ");

                            Log.i("activity0101", " error is " + e.toString());

                            music.setTitle("Unknown");

                            PostMan postMan = new PostMan();
                            postMan.setStatus("title_un");

                            publishProgress(postMan);

                        }

                        music.setPath(audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)));

                      //  activity.logs.setText("music path set");

                        Log.i("activity0101", " we should see music path set text in the log text ");


                      //  activity.logs.setText("music added in list");

                        Log.i("activity0101", " we should see music added set text in the log text ");

                        Log.i("do_in_background" , "final song : " + music.toString());

                        Log.i("do_in_background" , "final deliverer : " + music.toString());

                        PostMan postMan = new PostMan();
                        postMan.setStatus("add_song");
                        postMan.setMusicModel(music);

                        publishProgress(postMan);

                        i++;


                    } while (audioCursor.moveToNext());
                }
            }
            audioCursor.close();


            if (i == 0) {


                PostMan postMan = new PostMan();
                postMan.setStatus("no_songs");
                publishProgress(postMan);

                //activity.logs.setText("No Music found");
            }
            return null;
        }

        @Override
        protected void onPostExecute(RecyclerViewAdapter recyclerViewAdapter) {
            super.onPostExecute(recyclerViewAdapter);

            viewAdapter.notifyDataSetChanged();

        }

        @Override
        protected void onProgressUpdate(PostMan... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();


            if(values[0].getStatus() == "add_title"){

                Log.i("onProgressLogs" , "add_title + " +  title);
                activity.songNames.add(title);

            }

            if(values[0].getStatus() == "title_un"){

                Log.i("onProgressLogs" , "unknown title + " +  title);


                activity.songNames.add("unknown");

            }

            if(values[0].getStatus() == "add_song"){

                Log.i("do_in_background" , "final value[0].getMusicModel in progress : " + values[0].getMusicModel());
                activity.songs.add(values[0].getMusicModel());

            }

            if(values[0].getStatus() == "no_songs"){

                Toast.makeText(activity, "No songs were found", Toast.LENGTH_SHORT).show();

            }


        }
    }
    public class PostMan {

        String status;
        MusicModel musicModel;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public MusicModel getMusicModel() {
            return musicModel;
        }

        public void setMusicModel(MusicModel musicModel) {
            this.musicModel = musicModel;
        }
    }
}
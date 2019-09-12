package com.example.galaxyplayer.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import  android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.galaxyplayer.Activities.MainActivityClass;
import com.example.galaxyplayer.Objects.MusicModel;
import com.example.galaxyplayer.Objects.PostMan;
import com.example.galaxyplayer.R;
import com.example.galaxyplayer.Adapters.RecyclerViewAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static android.content.Context.MODE_PRIVATE;
import static com.example.galaxyplayer.Activities.MainActivityClass.permissionAllowed;
import static com.example.galaxyplayer.Fragments.LoginFragment.NAME;
import static com.example.galaxyplayer.Fragments.LoginFragment.SHARED_PREFS;

public class SongListFragment extends Fragment  {

    public static boolean play;

    public ArrayList<String> songNames;

    public ArrayList<MusicModel> songs;

    public ArrayList<String> songUrlList;

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView songUrls;

    LinearLayoutManager manager;

    GridLayoutManager gridlayoutManager;

    TextView userGreetings;

    String userName;

    View view;

    private static final String TAG = "SongListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main , container , false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

            Log.i("activity0101", "music setter may start now");

            AsyncTaskMusicSetter musicSetter = new AsyncTaskMusicSetter(new MainActivityClass() , this );

            musicSetter.execute(recyclerViewAdapter);

            Log.i("activity0101", "we should see the song list");




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onCreateView: Welcome to SongListFragment class");

        userGreetings = view.findViewById(R.id.main_activity_user_greeting);

        play = true;

        songUrls = view.findViewById(R.id.recycler_view);

        manager = new LinearLayoutManager(getActivity());

        gridlayoutManager = new GridLayoutManager(getActivity(), 3);

        songUrls.setLayoutManager(gridlayoutManager);

        songNames = new ArrayList<>();

        songs = new ArrayList<>();

        songUrlList = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(songs);

        songUrls.setAdapter(recyclerViewAdapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        userName = sharedPreferences.getString(NAME , "");

        userGreetings.setText("Welcome Dear " + userName);


    }

    public class AsyncTaskMusicSetter extends android.os.AsyncTask<RecyclerViewAdapter, PostMan, RecyclerViewAdapter> {

        private WeakReference<MainActivityClass> activityWeakReference;
        private WeakReference<SongListFragment> fragmentWeakReference;
        RecyclerViewAdapter viewAdapter;
        String title;

        public AsyncTaskMusicSetter(MainActivityClass activity , SongListFragment fragment) {

            activityWeakReference = new WeakReference<>(activity);

            fragmentWeakReference = new WeakReference<>(fragment);

        }

        @Override
        protected RecyclerViewAdapter doInBackground(RecyclerViewAdapter... adapters) {

            MainActivityClass activity = activityWeakReference.get();

            Integer i = 0;

            viewAdapter = adapters[0];

            //activity.logs.setText("permission checked");

            Log.i("activity0101", "we should see a permission text in log text");

            String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.AudioColumns.DATA};

            Cursor audioCursor = getActivity().getContentResolver().query

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

                        Log.i("do_in_background", "final song : " + music.toString());

                        Log.i("do_in_background", "final deliverer : " + music.toString());

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



            SongListFragment songListFragment = fragmentWeakReference.get();


            if (values[0].getStatus() == "add_title") {

                Log.i("onProgressLogs", "add_title + " + title);

                //activity.songListFragment.songNames.add(title);
                songListFragment.songNames.add(title);



            }

            if (values[0].getStatus() == "title_un") {

                Log.i("onProgressLogs", "unknown title + " + title);


               // activity.songListFragment.songNames.add("unknown");
                songListFragment.songNames.add("unknown");

            }

            if (values[0].getStatus() == "add_song") {

                Log.i("do_in_background", "final value[0].getMusicModel in progress : " + values[0].getMusicModel());

                // activity.songListFragment.songs.add(values[0].getMusicModel());
                songListFragment.songs.add(values[0].getMusicModel());


            }

            if (values[0].getStatus() == "no_songs") {

                Toast.makeText(getActivity() , "No songs were found", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

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

                    Log.i("activity0101", "music setter may start now");

                    AsyncTaskMusicSetter musicSetter = new AsyncTaskMusicSetter(new MainActivityClass() , this );

                    musicSetter.execute(recyclerViewAdapter);

                    Log.i("activity0101", "we should see the song list");


                } else {
                    Toast.makeText(getContext() , "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


}


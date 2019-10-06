package com.example.galaxyplayerkotlin.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxyplayerkotlin.Activities.MainActivityClass;
import com.example.galaxyplayerkotlin.Objects.MusicModel;
import com.example.galaxyplayerkotlin.Objects.PostMan;
import com.example.galaxyplayerkotlin.Adapters.RecyclerViewAdapter;
import com.example.galaxyplayerkotlin.R;
import com.example.galaxyplayerkotlin.repositories.SongListRepo;
import com.example.galaxyplayerkotlin.viewModel.SongListFragmentViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.galaxyplayerkotlin.Fragments.LoginFragment.NAME;
import static com.example.galaxyplayerkotlin.Fragments.LoginFragment.SHARED_PREFS;

public class SongListFragment extends Fragment {

    public static boolean play;

    public ArrayList<MusicModel> songs;

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView songUrls;

    LinearLayoutManager manager;

    GridLayoutManager gridlayoutManager;

    TextView userGreetings;

    String userName;

    SongListFragmentViewModel songListFragmentViewModel;

    View view;

    private static final String TAG = "SongListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

      /*  if (checkPermissionREAD_EXTERNAL_STORAGE(userGreetings.getContext()))

            Log.i("activity0101", "music setter may start now");

        AsyncTaskMusicSetter musicSetter = new AsyncTaskMusicSetter(new MainActivityClass(), this, recyclerViewAdapter);

        musicSetter.execute(view);

        Log.i("activity0101", "we should see the song list");*/

    }

    public void changeSongListFragment(Fragment fragment) {

        FragmentManager fm = getActivity().getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.music_player_layout, fragment);

        transaction.commit();
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

        songs = new ArrayList<>();

        SharedPreferences sharedPreferences = userGreetings.getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        userName = sharedPreferences.getString(NAME, "");

        userGreetings.setText("Welcome Dear " + userName);

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            songListFragmentViewModel = ViewModelProviders.of(this).get(SongListFragmentViewModel.class);

            SongListRepo songListRepo = new SongListRepo();

            songListFragmentViewModel.InitializeProcess(songListRepo);

            songListFragmentViewModel.reciveSongs().observe(this, new Observer<ArrayList<MusicModel>>() {

                @Override
                public void onChanged(ArrayList<MusicModel> musicModels) {

                    recyclerViewAdapter = new RecyclerViewAdapter(musicModels);

                    songUrls.setAdapter(recyclerViewAdapter);

                    songUrls.setLayoutManager(gridlayoutManager);

                    recyclerViewAdapter.notifyDataSetChanged();

                }
            });
        } else {

            requestStoragePermission();

        }

    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {


            new AlertDialog.Builder(view.getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because i order to get the songs list , we need to have access to your storage  ")
                    .setPositiveButton("Oh ok ! ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                                }

                            }
                    ).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();


        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

    }

    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    songListFragmentViewModel = ViewModelProviders.of(this).get(SongListFragmentViewModel.class);

                    SongListRepo songListRepo = new SongListRepo();

                    songListFragmentViewModel.InitializeProcess(songListRepo);

                    songListFragmentViewModel.reciveSongs().observe(this, new Observer<ArrayList<MusicModel>>() {

                        @Override
                        public void onChanged(ArrayList<MusicModel> musicModels) {

                            recyclerViewAdapter = new RecyclerViewAdapter(musicModels);

                            songUrls.setAdapter(recyclerViewAdapter);

                            songUrls.setLayoutManager(gridlayoutManager);

                            recyclerViewAdapter.notifyDataSetChanged();

                        }
                    });


                } else {
                    Toast.makeText(userGreetings.getContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public void setupRecyclerView() {

        ArrayList<MusicModel> musicModels = songListFragmentViewModel.reciveSongs().getValue();

        recyclerViewAdapter = new RecyclerViewAdapter(musicModels);

        songUrls.setAdapter(recyclerViewAdapter);

        songUrls.setLayoutManager(gridlayoutManager);


    }


    public class AsyncTaskMusicSetter extends android.os.AsyncTask<View, PostMan, RecyclerViewAdapter> {

        private WeakReference<MainActivityClass> activityWeakReference;
        private WeakReference<SongListFragment> fragmentWeakReference;
        private WeakReference<RecyclerViewAdapter> recyclerViewAdapterWeakReference;
        RecyclerViewAdapter viewAdapter;
        String title;

        public AsyncTaskMusicSetter(MainActivityClass activity, SongListFragment fragment, RecyclerViewAdapter adapter) {

            activityWeakReference = new WeakReference<>(activity);

            fragmentWeakReference = new WeakReference<>(fragment);

            recyclerViewAdapterWeakReference = new WeakReference<>(adapter);

        }

        @Override
        protected RecyclerViewAdapter doInBackground(View... views) {

            MainActivityClass activity = activityWeakReference.get();

            RecyclerViewAdapter adapter = recyclerViewAdapterWeakReference.get();

            Integer i = 0;

            viewAdapter = adapter;

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
                // songListFragment.songNames.add(title);


            }

            if (values[0].getStatus() == "title_un") {

                Log.i("onProgressLogs", "unknown title + " + title);


                // activity.songListFragment.songNames.add("unknown");
                // songListFragment.songNames.add("unknown");


            }

            if (values[0].getStatus() == "add_song") {

                Log.i("do_in_background", "final value[0].getMusicModel in progress : " + values[0].getMusicModel());

                // activity.songListFragment.songs.add(values[0].getMusicModel());
                songListFragment.songs.add(values[0].getMusicModel());


            }

            if (values[0].getStatus() == "no_songs") {

                Toast.makeText(getActivity(), "No songs were found", Toast.LENGTH_SHORT).show();

            }

        }
    }


}


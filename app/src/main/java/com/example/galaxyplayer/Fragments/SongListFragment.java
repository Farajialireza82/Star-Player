package com.example.galaxyplayer.Fragments;

import  android.database.Cursor;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.galaxyplayer.Activities.MainActivityClass.permissionAllowed;

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

    private static final String TAG = "SongListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main , container , false);

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

        userGreetings.setText("Welcome Dear " + userName);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (permissionAllowed) {

            Log.i("activity0101", "music setter may start now");

            AsyncTaskMusicSetter musicSetter = new AsyncTaskMusicSetter(new MainActivityClass() , this );

            musicSetter.execute(recyclerViewAdapter);

            Log.i("activity0101", "we should see the song list");

        } else {

            Toast.makeText(getActivity(), "In order to load your musics , App needs Access to You Data", Toast.LENGTH_LONG).show();
        }

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

            Cursor audioCursor = activity.getContentResolver().query

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


}


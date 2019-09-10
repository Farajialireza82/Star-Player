package com.example.galaxyplayer.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxyplayer.AsyncTaskMusicSetter;
import com.example.galaxyplayer.MainActivityClass;
import com.example.galaxyplayer.Objects.MusicModel;
import com.example.galaxyplayer.R;
import com.example.galaxyplayer.RecyclerViewAdapter;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SongListFragment extends Fragment implements LoginFragment.LoginFragmentListener {

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

    @Override
    public void sendName(CharSequence input) {
        userName = input.toString();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main , container , false);

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

        if (MainActivityClass.permissionAllowed) {

            Log.i("activity0101", "music setter may start now");

            AsyncTaskMusicSetter musicSetter = new AsyncTaskMusicSetter(new MainActivityClass());

            musicSetter.execute(recyclerViewAdapter);

            Log.i("activity0101", "we should see the song list");

        } else {

            Toast.makeText(getActivity(), "In order to load your musics , App needs Access to You Data", Toast.LENGTH_LONG).show();
        }

    }
}

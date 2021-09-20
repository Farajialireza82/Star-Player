package com.example.galaxyplayerkotlin.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.galaxyplayerkotlin.Objects.MusicModel;
import com.example.galaxyplayerkotlin.Adapters.RecyclerViewAdapter;
import com.example.galaxyplayerkotlin.R;
import com.example.galaxyplayerkotlin.repositories.SongListRepo;
import com.example.galaxyplayerkotlin.viewModel.SongListFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SongListFragment extends Fragment {

    public static boolean play;

    public ArrayList<MusicModel> songs;

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView songUrls;

    LinearLayoutManager manager;

    GridLayoutManager gridlayoutManager;

    SongListFragmentViewModel songListFragmentViewModel;

    View view;

    private static final String TAG = "SongListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_songlist, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d(TAG, "onCreateView: Welcome to SongListFragment class");

        play = true;

        songUrls = view.findViewById(R.id.recycler_view);

        manager = new LinearLayoutManager(getActivity());

        gridlayoutManager = new GridLayoutManager(getActivity(), 1);

        songUrls.setLayoutManager(gridlayoutManager);

        songs = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            songListFragmentViewModel = ViewModelProviders.of(this).get(SongListFragmentViewModel.class);

            songListFragmentViewModel.InitializeProcess();


            songListFragmentViewModel.reciveSongs().observe(getViewLifecycleOwner(), new Observer<ArrayList<MusicModel>>() {

                @Override
                public void onChanged(ArrayList<MusicModel> musicModels) {

                    recyclerViewAdapter = new RecyclerViewAdapter(musicModels);

                    songUrls.setAdapter(recyclerViewAdapter);

                    songUrls.setLayoutManager(gridlayoutManager);

                    recyclerViewAdapter.notifyDataSetChanged();

                }
            });
        } else {

            Toast.makeText(getContext(), "Please grant storage permission from Settings", Toast.LENGTH_LONG).show();

        }

    }

}
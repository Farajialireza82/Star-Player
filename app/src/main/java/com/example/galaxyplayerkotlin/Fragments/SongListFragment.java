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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
                    Toast.makeText(getContext(), "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}


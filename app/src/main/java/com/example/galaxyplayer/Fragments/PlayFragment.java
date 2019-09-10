package com.example.galaxyplayer.Fragments;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.galaxyplayer.R;
import com.example.galaxyplayer.RecyclerViewAdapter;
import com.example.galaxyplayer.ServiceClass;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayFragment extends Fragment implements RecyclerViewAdapter.RecyclerAdapterListener {

    PlayerView playerView;

    ExoPlayer exoPlayer;

    long playbackPosition;

    int currentWindow;

    boolean playWhenReady;

    String name;

    String value;

    final ServiceConnection connection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            ExoPlayer exoPlayerS;

            if (service instanceof ServiceClass.VideoServiceBinder) {

                exoPlayerS = ((ServiceClass.VideoServiceBinder) service).getExoPlayerInstance();

                if (exoPlayerS == null) {
                    Log.i("nullCheck", "exoPLayerService is null");
                    return;
                }

                Toast.makeText(getContext(), "Service Connected", Toast.LENGTH_SHORT).show();

                Log.i("service-activity", "Service Connected successfully ");

                try {

                    exoPlayerS.setPlayWhenReady(playWhenReady);

                    exoPlayerS.seekTo(currentWindow, playbackPosition);

                    exoPlayer = exoPlayerS;

                    playerView.setPlayer(exoPlayer);

                } catch (Exception e) {

                    Log.i("nullCheck", e.toString());

                }

            }


        }


        @Override
        public void onServiceDisconnected(ComponentName name) {

            Toast.makeText(getContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();

            Log.i("service-activity", "Service Disconnected successfully ");

        }

    };

    @Override
    public void sendSongPath(String songPath) {

        value = songPath;

    }

    @Override
    public void sendSongTitle(String songTitle) {

        name = songTitle;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_music , container , false);

        playerView = view.findViewById(R.id.exoPlayerView);

        getActivity().startService(
                ServiceClass.createIntent(
                        getContext(),
                        ServiceClass.ACTION_PLAY,
                        value,
                        name
                )
        );


        return view;
    }


}

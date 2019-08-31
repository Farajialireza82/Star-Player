package com.example.galaxyplayer;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import java.io.File;
import java.net.URI;
import java.net.URL;

public class PlayActivity extends AppCompatActivity {

    PlayerView playerView;

    ExoPlayer exoPlayer;

    long playbackPosition;

    int currentWindow;

    boolean playWhenReady;

    String name;

    String value;

    String path;

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

                Toast.makeText(PlayActivity.this, "Service Connected", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(PlayActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show();

            Log.i("service-activity", "Service Disconnected successfully ");

        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        playerView = findViewById(R.id.exoPlayerView);

        name = new String();

        value = new String();

        path = new String();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            value = extras.getString("key");

            name = extras.getString("title");

        }

        startService(
                ServiceClass.createIntent(
                        this,
                        ServiceClass.ACTION_PLAY,
                        value,
                        name
                )
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        bindService(new Intent(this, ServiceClass.class), connection, Context.BIND_AUTO_CREATE);

        hideSystemUi();

    }

    @Override
    public void onResume() {

        super.onResume();

        hideSystemUi();

    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    @Override
    public void onStop() {
        unbindService(connection);
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        exoPlayer = null;
        playerView.setPlayer(null);

    }


    private void pausePlayer() {
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.getPlaybackState();
    }


    private void stopPlayer() {
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.getPlaybackState();
        exoPlayer = null;

    }

    private void startPlayer() {
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.getPlaybackState();
    }


}

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




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        releasePlayer();

        Intent service = new Intent(this , ServiceClass.class);

        name = new String();

        value = new String();


        playerView = findViewById(R.id.exoPlayerView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            value = extras.getString("key");

            name = extras.getString("title");

        }

        Intent serviceActivity = new Intent(PlayActivity.this, ServiceClass.class);

        service.putExtra("key", value);

        service.putExtra("title" , name);

        startService(service);



        final ServiceConnection connection = new ServiceConnection() {


            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                releasePlayer();

                ExoPlayer exoPlayerS ;

                if (service instanceof ServiceClass.VideoServiceBinder) {

                    exoPlayerS= ((ServiceClass.VideoServiceBinder) service).getExoPlayerInstance();

                    if(exoPlayerS == null){
                        Log.i("nullCheck" , "exoPLayerService is null");
                    }

                    Toast.makeText(PlayActivity.this, "Service Connected", Toast.LENGTH_SHORT).show();

                    Log.i("service-activity" , "Service Connected successfully ");

                    try {

                        exoPlayerS.setPlayWhenReady(playWhenReady);

                        exoPlayerS.seekTo(currentWindow, playbackPosition);

                        releasePlayer();

                        exoPlayer = exoPlayerS;

                        playerView.setPlayer(exoPlayer);

                    }catch (Exception e){

                        Log.i("nullCheck" ,  e.toString());

                    }

                }


            }


            @Override
            public void onServiceDisconnected(ComponentName name) {


                releasePlayer();

                Toast.makeText(PlayActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show();

                Log.i("service-activity" , "Service Disconnected successfully ");

            }

        };

      //  bindService(serviceActivity , connection , BIND_AUTO_CREATE);

        bindService(new Intent(this , ServiceClass.class) , connection , Context.BIND_AUTO_CREATE);


    }

    @Override
    public void onStart() {
        super.onStart();

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

        super.onStop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        exoPlayer = null;
        playerView.setPlayer(null);

    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();


            currentWindow = exoPlayer.getCurrentWindowIndex();

            playWhenReady = exoPlayer.getPlayWhenReady();

            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(exoPlayer.getPlayWhenReady()){
                pausePlayer();
            }
            else {
                startPlayer();
            }
        }
    }

    private void pausePlayer(){
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.getPlaybackState();
    }
    private void startPlayer(){
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.getPlaybackState();
    }


}

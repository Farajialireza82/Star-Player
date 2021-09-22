package com.example.galaxyplayerkotlin.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import com.example.galaxyplayerkotlin.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;


import static com.example.galaxyplayerkotlin.service.App.CHANNEL_ID;

public class ServiceClass extends Service {

    public static final String EXTRA_ACTION = "extra_action";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_MUSIC_URL = "extra_music_url";

    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PLAY_PAUSE = "action_play_pause";
    public static final String RESET_DATA = "reset_data";

    private static final String TAG = "ServiceClass";

    public static final int REQUEST_CODE_NOTIFICATION = 1;

    String currentMusicUrl = "";

    String name = "";

    ExoPlayer exoPlayer;

    IBinder videoServiceBinder = new VideoServiceBinder();

    public MediaSessionCompat mediaSession;

    public static Intent createIntent(Context context, String action) {
        Intent intent = new Intent(context, ServiceClass.class);
        intent.putExtra(EXTRA_ACTION, action);
        return intent;
    }

    public static Intent createIntent(Context context, String action, String songURL, String songTitle) {
        Intent intent = new Intent(context, ServiceClass.class);
        intent.putExtra(EXTRA_ACTION, action);
        intent.putExtra(EXTRA_TITLE, songTitle);
        intent.putExtra(EXTRA_MUSIC_URL, songURL);
        return intent;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();

        String action;
        String title;
        String songURI;

        if (extras == null) {
            return START_STICKY;
        }


        songURI = extras.getString(EXTRA_MUSIC_URL, "");

        title = extras.getString(EXTRA_TITLE, "");

        action = extras.getString(EXTRA_ACTION, "");

        if (action.length() == 0) {
            return START_STICKY;
        }

        switch (action) {
            case ACTION_PLAY: {
                Log.d(TAG, "onStartCommand: ACTION_PLAY");
                playSong(songURI, title);
                exoPlayer.setPlayWhenReady(true);
                break;
            }
            case ACTION_PAUSE: {
                Log.d(TAG, "onStartCommand: ACTION_PAUSE");
                pauseSong();
                break;
            }
            case ACTION_PLAY_PAUSE: {
                Log.d(TAG, "onStartCommand: ACTION_PLAY_PAUSE");
                if (exoPlayer.getPlayWhenReady()) {
                    pauseSong();
                } else {
                    resumeSong();
                }
                break;
            }
            default: {
                break;
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return videoServiceBinder;

    }

    public class VideoServiceBinder extends Binder {

        public ExoPlayer getExoPlayerInstance() {

            return exoPlayer;

        }

    }


    private void playSong(String url, String title) {
        //this song is already playing
        if (
                exoPlayer != null &&
                        exoPlayer.getPlayWhenReady() &&
                        currentMusicUrl.equalsIgnoreCase(url)) {
            return;
        }

        //check to see if exo player has been created
        if (exoPlayer == null) {
            createExoPlayer();
        }

        //if the song is the same as previous and stopped we should play it here
        if (currentMusicUrl.equalsIgnoreCase(url)) {
            exoPlayer.setPlayWhenReady(true);
            return;
        }
        //if the url is not the same we have to first stop the player
        exoPlayer.stop();

        //create new media source and feed to ExoPlayer
        MediaSource source = createMediaSource(url);

        exoPlayer.prepare(source);

        exoPlayer.setPlayWhenReady(true);


        currentMusicUrl = url;
        name = title;

        createNotificationForTheSong(title);
    }

    private void createNotificationForTheSong(String title) {
        PendingIntent playPause = PendingIntent.getService(
                this,
                REQUEST_CODE_NOTIFICATION,
                ServiceClass.createIntent(this, ACTION_PLAY_PAUSE),
                0
        );


        mediaSession = new MediaSessionCompat(this, "tag");
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.exo_controls_fastforward);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.exo_controls_play)
                .setContentTitle("Galaxy Player")
                .setLargeIcon(picture)
                .setContentText(title)
                .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", null)
                .addAction(R.drawable.ic_play_circle_filled_black_24dp, "Play/Pause", playPause)
                .addAction(R.drawable.ic_skip_next_black_24dp, "Next", null)
                .setSubText(name)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        startForeground(1, notification);
    }

    private void pauseSong() {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
        }
    }

    private void createExoPlayer() {
        exoPlayer = new SimpleExoPlayer.Builder(this,
                new DefaultRenderersFactory(this)).build();

    }

    public void resumeSong() {
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.getPlaybackState();
    }

    private MediaSource createMediaSource(String songURL) {
        Uri uri = Uri.parse(songURL);

        return new ProgressiveMediaSource.Factory(
                new DefaultDataSourceFactory(this, "Exoplayer-local")).
                createMediaSource(uri);
    }
}

package com.example.galaxyplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Base64;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.gson.Gson;

import static com.example.galaxyplayer.App.CHANNEL_ID;

public class ServiceClass extends Service {

    String uriString;

    String name;

    String value;

    ExoPlayer exoPlayer;

    IBinder videoServiceBinder = new VideoServiceBinder();

    private MediaSessionCompat mediaSession;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();

        String action;

        if (extras != null) {

            value = extras.getString("key");

            name = extras.getString("title");

            uriString = value;
        }

        action = extras.getString("action");

        Log.i("activity0101" , "action equals to" + action);

        mediaSession = new MediaSessionCompat(this, "tag");

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        Uri uri = Uri.parse(uriString);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(this, "Exoplayer-local")).
                createMediaSource(uri);

        exoPlayer.prepare(mediaSource, false, false);

        if (action == "play") {

            Log.i("activity0101" , "action is play");



            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.exo_controls_fastforward);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.exo_controls_play)
                    .setContentTitle("Galaxy Player")
                    .setLargeIcon(picture)
                    .setContentText(name)
                    .addAction(R.drawable.ic_dislike_black_24dp, "Dislike", null)
                    .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", null)
                    .addAction(R.drawable.ic_play_circle_filled_black_24dp, "Play/Pause", null)
                    .addAction(R.drawable.ic_skip_next_black_24dp, "Next", null)
                    .addAction(R.drawable.ic_like_black_24dp, "Like", null)
                    .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(1, 2, 3)
                            .setMediaSession(mediaSession.getSessionToken()))
                    .setSubText(name)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            startForeground(1, notification);

        }






        return START_STICKY;
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


}

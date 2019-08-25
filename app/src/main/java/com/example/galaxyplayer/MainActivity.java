package com.example.galaxyplayer;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static boolean play;

    ArrayList<String> songNames;

    ArrayList<MusicModel> songs;

    ArrayList<String> songUrlList;

    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerView songUrls;

    TextView logs;

    LinearLayoutManager manager;

    GridLayoutManager gridlayoutManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        play = true;

        logs = findViewById(R.id.logs);

        songUrls = findViewById(R.id.recycler_view);

        manager = new LinearLayoutManager(this);

        gridlayoutManager = new GridLayoutManager(this, 3);

        songUrls.setLayoutManager(gridlayoutManager);

        songNames = new ArrayList<>();

        songs = new ArrayList<>();

        songUrlList = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(songs);

        songUrls.setAdapter(recyclerViewAdapter);

        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {

            setmusicsOnRecyclerBar();

        }



    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setmusicsOnRecyclerBar();

                } else {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public void setmusicsOnRecyclerBar(){

        logs.setText("permission checked");

        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.AudioColumns.DATA};

        // Can include more data for more details and check it.

        Cursor audioCursor = getContentResolver().query

                (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj,

                        null, null, null);

        if (audioCursor != null) {

            if (audioCursor.moveToFirst()) {

                do {

                    MusicModel music = new MusicModel();

                    try {

                        logs.setText("getting music");

                        String songTitle =audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));

                        String title = songTitle.replaceAll(".mp3" , "");

                        music.setTitle(title);

                        logs.setText("title added ");

                        songNames.add(title);
                    }
                    catch (Exception e ){

                        logs.setText("error at setting song title " + e.toString());

                        music.setTitle("Unknown");

                        songNames.add("Unknown");

                    }

                    music.setPath(audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)));

                    logs.setText("music path set");



                    logs.setText("music added in list");

                    songs.add(music);



                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    byte[] rawArt;
                    Bitmap art;
                    BitmapFactory.Options bfo = new BitmapFactory.Options();

                    try {

                        logs.setText("getting music cover");

                        mmr.setDataSource(MainActivity.this, Uri.parse(music.getPath()));
                        rawArt = mmr.getEmbeddedPicture();


                        // if rawArt is null then no cover art is embedded in the file or is not
                        // recognized as such.

                        if (rawArt != null) {

                            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);

                            music.setArt(art);

                        }
                    }
                    catch(Exception e){

                        logs.setText("cover failed exeption : " + e.toString());

                        Bitmap b =
                                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);

                        music.setArt(b);




                    }




                } while (audioCursor.moveToNext());
            }
        }
        audioCursor.close();

        recyclerViewAdapter.notifyDataSetChanged();
    }


}

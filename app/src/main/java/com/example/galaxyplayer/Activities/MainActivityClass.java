package com.example.galaxyplayer.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.galaxyplayer.Adapters.SimpleSectionPagerAdapter;
import com.example.galaxyplayer.Fragments.LoginFragment;
import com.example.galaxyplayer.Fragments.MusicPlayerFragment;
import com.example.galaxyplayer.Fragments.SongListFragment;
import com.example.galaxyplayer.Fragments.SwapiFragment;
import com.example.galaxyplayer.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class MainActivityClass extends AppCompatActivity {

    static ViewPager viewPager;
    public SimpleSectionPagerAdapter simpleSectionPagerAdapter;
    TabLayout tabLayout;

    private static final String TAG = "MainActivityClass";

    public static final int SWAPI_FRAGMENT = 1;
    public static final int MUSIC_PLAYER_FRAGMENT = 0;

    public static boolean permissionAllowed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        tabLayout = findViewById(R.id.tabLayout_id);

        viewPager = findViewById(R.id.view_pager);

        simpleSectionPagerAdapter = new SimpleSectionPagerAdapter(getSupportFragmentManager());

        //sectionPageAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(simpleSectionPagerAdapter);

        Log.d(TAG, "onCreate: sectionPageAdapter set to viewPager");

        simpleSectionPagerAdapter.addFragment(new MusicPlayerFragment(), "Music Player");
        simpleSectionPagerAdapter.addFragment(new SwapiFragment(), "Swapi");

        simpleSectionPagerAdapter.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);

        Log.d(TAG, "onCreate: Opening Login Fragment");

        permissionAllowed = checkPermissionREAD_EXTERNAL_STORAGE(this);

    }


    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

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
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
                                new String[]{permission},
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

                } else {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

}

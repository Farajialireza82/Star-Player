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

    }


}

package com.example.galaxyplayerkotlin.Activities;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.galaxyplayerkotlin.Adapters.SimpleSectionPagerAdapter;
import com.example.galaxyplayerkotlin.Fragments.LoginFragment;
import com.example.galaxyplayerkotlin.Fragments.MusicPlayerFragment;
import com.example.galaxyplayerkotlin.Fragments.SongListFragment;
import com.example.galaxyplayerkotlin.Fragments.SwapiFragment;
import com.example.galaxyplayerkotlin.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivityClass extends AppCompatActivity {

    static ViewPager viewPager;
    public SimpleSectionPagerAdapter simpleSectionPagerAdapter;
    TabLayout tabLayout;

    private static final String TAG = "MainActivityClass";


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

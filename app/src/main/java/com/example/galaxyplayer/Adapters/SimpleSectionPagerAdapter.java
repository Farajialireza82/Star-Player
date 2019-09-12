package com.example.galaxyplayer.Adapters;

import com.example.galaxyplayer.Activities.MainActivityClass;
import com.example.galaxyplayer.Fragments.LoginFragment;
import com.example.galaxyplayer.Fragments.PlayFragment;
import com.example.galaxyplayer.Fragments.SongListFragment;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.example.galaxyplayer.Activities.MainActivityClass.LOGIN_FRAGMENT;
import static com.example.galaxyplayer.Activities.MainActivityClass.SONG_LIST_FRAGMENT;

public class SimpleSectionPagerAdapter extends FragmentPagerAdapter {

    ArrayList <Fragment> fragments = new ArrayList<>();
    ArrayList < String > fragmentsTitle = new ArrayList<>();



    public SimpleSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment , String fragmentTitle){

        fragments.add(fragment);
        fragmentsTitle.add(fragmentTitle);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}

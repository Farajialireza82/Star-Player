package com.example.galaxyplayer.Adapters;

import com.example.galaxyplayer.Activities.MainActivityClass;
import com.example.galaxyplayer.Fragments.LoginFragment;
import com.example.galaxyplayer.Fragments.PlayFragment;
import com.example.galaxyplayer.Fragments.SongListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.example.galaxyplayer.Activities.MainActivityClass.LOGIN_FRAGMENT;
import static com.example.galaxyplayer.Activities.MainActivityClass.PLAY_FRAGMENT;
import static com.example.galaxyplayer.Activities.MainActivityClass.SONG_LIST_FRAGMENT;

public class SimpleSectionPagerAdapter extends FragmentPagerAdapter {

    public SimpleSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case LOGIN_FRAGMENT:
                return new LoginFragment();
            case PLAY_FRAGMENT:
                return new PlayFragment();
            case SONG_LIST_FRAGMENT:
                return new SongListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

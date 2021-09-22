package com.example.galaxyplayerkotlin.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.galaxyplayerkotlin.Fragments.ArtistsListFragment
import com.example.galaxyplayerkotlin.Fragments.SongListFragment

private const val NUM_TABS = 2

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = NUM_TABS

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return SongListFragment()
            1 -> return ArtistsListFragment()

        }
        return SongListFragment()
    }

}

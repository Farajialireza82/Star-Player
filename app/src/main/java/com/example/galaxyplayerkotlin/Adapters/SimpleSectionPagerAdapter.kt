package com.example.galaxyplayerkotlin.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class SimpleSectionPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    var fragments = ArrayList<Fragment>()

    var fragmentsTitle = ArrayList<String>()

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    fun addFragment(fragment: Fragment, fragmentTitle: String) {
        fragments.add(fragment)
        fragmentsTitle.add(fragmentTitle)
    }

    override fun getPageTitle(position: Int) = fragmentsTitle[position]

}
package com.example.galaxyplayerkotlin.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.galaxyplayerkotlin.R
import com.example.galaxyplayerkotlin.Fragments.SongListFragment

class MusicPlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.music_player_layout, container, false)
        val fm = activity?.supportFragmentManager
        val transaction = fm?.beginTransaction()
        transaction?.replace(R.id.music_player_layout, SongListFragment())
        transaction?.commit()
        return view
    }


}
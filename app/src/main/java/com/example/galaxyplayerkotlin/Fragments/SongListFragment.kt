package com.example.galaxyplayerkotlin.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxyplayerkotlin.Adapters.RecyclerViewAdapter
import com.example.galaxyplayerkotlin.Objects.MusicModel
import com.example.galaxyplayerkotlin.R
import com.example.galaxyplayerkotlin.viewModel.SongListFragmentViewModel
import java.util.*

class SongListFragment : Fragment(R.layout.fragment_songlist) {

    lateinit var songs: ArrayList<MusicModel>

    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private lateinit var songUrls: RecyclerView

    private lateinit var manager: LinearLayoutManager

    private lateinit var gridlayoutManager: GridLayoutManager

    private lateinit var songListFragmentViewModel: SongListFragmentViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onCreateView: Welcome to SongListFragment class")

        play = true

        songUrls = view.findViewById(R.id.recycler_view)

        manager = LinearLayoutManager(activity)

        gridlayoutManager = GridLayoutManager(activity, 1)

        songUrls.layoutManager = gridlayoutManager

        songs = ArrayList()

        if (ContextCompat.checkSelfPermission(
                view.context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            songListFragmentViewModel =
                ViewModelProvider(this).get(SongListFragmentViewModel::class.java)

            songListFragmentViewModel.initializeProcess()

            songListFragmentViewModel.receiveSongs()
                .observe(viewLifecycleOwner, Observer { musicModels ->

                    musicModels.sortBy {
                        it.title
                    }

                    recyclerViewAdapter = RecyclerViewAdapter(musicModels)

                    songUrls.adapter = recyclerViewAdapter

                    songUrls.layoutManager = gridlayoutManager

                    recyclerViewAdapter.notifyDataSetChanged()
                })
        } else {
            Toast.makeText(
                context,
                "Please grant storage permission from Settings",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        var play = false
        private const val TAG = "SongListFragment"
    }
}
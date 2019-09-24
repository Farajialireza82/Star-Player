package com.example.galaxyplayerkotlin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.galaxyplayerkotlin.Adapters.Swapi_RecyclerViewAdapter;
import com.example.galaxyplayerkotlin.Objects.Person;
import com.example.galaxyplayerkotlin.R;
import com.example.galaxyplayerkotlin.repositories.SwapiPeopleListRepository;
import com.example.galaxyplayerkotlin.viewModel.SwapiFragmentViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SwapiFragment extends Fragment {

    ArrayList<Person> peopleInfo = new ArrayList<>();
    RecyclerView recyclerView;
    Button tryAgainButton;
    LinearLayoutManager layoutManager;
    ArrayList <String> peopleList;
    SwapiFragmentViewModel swapiFragmentViewModel;
    Swapi_RecyclerViewAdapter swapi_recyclerViewAdapter;

    private static final String TAG = "SwapiFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swapi_fragment, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onCreateView: SwapiFragment Started");

        tryAgainButton = view.findViewById(R.id.try_again_button);

        tryAgainButton.setVisibility(View.GONE);

        peopleList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.swapi_recyclerView);

        layoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(layoutManager);

        swapiFragmentViewModel = ViewModelProviders.of(this).get(SwapiFragmentViewModel.class);

         swapi_recyclerViewAdapter = new Swapi_RecyclerViewAdapter(peopleList);

        recyclerView.setAdapter(swapi_recyclerViewAdapter);

        swapi_recyclerViewAdapter.notifyDataSetChanged();

        SwapiPeopleListRepository swapiRepo = new SwapiPeopleListRepository();

        swapiFragmentViewModel.InitializeProcess(swapiRepo);

        swapiFragmentViewModel.reciveSongs().observe(this, new Observer<ArrayList<Person>>() {
            @Override
            public void onChanged(ArrayList<Person> people) {

                Log.d(TAG, "onChanged: people.size() = " + people.size());


                for(int i = 0 ; i < people.size() ; i++){

                    peopleList.add(people.get(i).getName());

                    Log.d(TAG, "onChanged: people num & name " + i + " " + people.get(i).getName());

                }

                swapi_recyclerViewAdapter.notifyDataSetChanged();




            }
        });

        //setupRecyclerViewAdapter();

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideTryAgainButton();


            }
        });


    }


    public void showTryAgainButton() {

        tryAgainButton.setVisibility(View.VISIBLE);

    }

    public void hideTryAgainButton() {

        tryAgainButton.setVisibility(View.GONE);


    }

  /*  public void setupRecyclerViewAdapter() {

        ArrayList<Person> people = swapiFragmentViewModel.reciveSongs().getValue();

        Log.d(TAG, "setupRecyclerViewAdapter: " + people.size());

            swapi_recyclerViewAdapter = new Swapi_RecyclerViewAdapter( swapiFragmentViewModel.reciveSongs().getValue());

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(swapi_recyclerViewAdapter);





    }*/


}

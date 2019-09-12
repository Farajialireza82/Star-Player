package com.example.galaxyplayer.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.galaxyplayer.Adapters.Swapi_RecyclerViewAdapter;
import com.example.galaxyplayer.Objects.People;
import com.example.galaxyplayer.Objects.Person;
import com.example.galaxyplayer.R;
import com.example.galaxyplayer.Retrofit.ServiceGenerator;
import com.example.galaxyplayer.Retrofit.SwapiClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwapiFragment extends Fragment {

    ArrayList<String> peopleInfo = new ArrayList<>();
    RecyclerView recyclerView;
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

        recyclerView = view.findViewById(R.id.swapi_recyclerView);

        final Swapi_RecyclerViewAdapter swapi_recyclerViewAdapter = new Swapi_RecyclerViewAdapter(peopleInfo);

        Log.d(TAG, "onCreateView: adapter set to Recycler View");

        recyclerView.setAdapter(swapi_recyclerViewAdapter);

        SwapiClient swapiClient = ServiceGenerator.createService(SwapiClient.class);

        Log.d(TAG, "onCreateView: swapiClient Created");

        Call<People> peopleCall = swapiClient.getPeople();

        Log.d(TAG, "onCreateView: swapiClient.getPeople() called");

        peopleCall.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {

                Log.d(TAG, "onResponse");


                getPeopleInfo(response);

                Log.d(TAG, "onCreateView: recyclerViewAdapter notifyDataSetChanged");

                swapi_recyclerViewAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

                Log.d(TAG, "onFailure: Error " + t.toString());

                Toast.makeText(getActivity(), "ERROR : " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Log.d(TAG, "onCreateView: recyclerView.setLayoutManager");


    }


    public void getPeopleInfo(Response<People> response) {

        List<Person> personList = response.body().getResults();

        for (int i = 0; i < personList.size(); i++) {

            Log.d(TAG, "onResponse: personList Result added to peopleInfo ArrayList " + personList.size());

            peopleInfo.add(personList.get(i).toString());


        }
    }

}

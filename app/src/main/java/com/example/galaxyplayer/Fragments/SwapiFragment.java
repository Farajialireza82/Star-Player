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

        recyclerView = view.findViewById(R.id.swapi_recyclerView);

        SwapiClient swapiClient = ServiceGenerator.createService(SwapiClient.class);

        Call<People> peopleCall = swapiClient.getPeople();

        peopleCall.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {

                List<Person> personList = response.body().getResults();

                for (int i = 0; i < personList.size(); i++) {

                    peopleInfo.add(personList.get(i).toString());


                }


            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

                Log.d(TAG, "onFailure: Error " + t.toString());

                Toast.makeText(getActivity(), "ERROR : " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new Swapi_RecyclerViewAdapter(peopleInfo));


        return view;
    }
}

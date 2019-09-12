package com.example.galaxyplayer.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galaxyplayer.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Swapi_RecyclerViewAdapter extends RecyclerView.Adapter<Swapi_RecyclerViewAdapter.ViewHolder> {

    private List<String> peopleNames;

    public Swapi_RecyclerViewAdapter(List<String> names) {

        peopleNames = names;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swapi_recycler_view_adapter, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.personInfoTextView.setText(peopleNames.get(position));

    }

    @Override
    public int getItemCount() {

        return peopleNames.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView personInfoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            personInfoTextView = itemView.findViewById(R.id.person_name);

        }
    }
}


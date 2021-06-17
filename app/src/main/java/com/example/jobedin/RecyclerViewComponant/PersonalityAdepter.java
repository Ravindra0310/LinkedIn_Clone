package com.example.jobedin.RecyclerViewComponant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobedin.R;

import java.util.ArrayList;

public class PersonalityAdepter extends RecyclerView.Adapter<PersonalityViewHolder> {
    private ArrayList<Personality> personalityArrayList;
    public PersonalityAdepter(ArrayList<Personality> personalityArrayList){
        this.personalityArrayList=personalityArrayList;
    }

    @NonNull
    @Override
    public PersonalityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.mynetwork,parent,false);
        return new PersonalityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalityViewHolder holder, int position) {
          Personality personality=personalityArrayList.get(position);
          holder.setData(personality);
    }

    @Override
    public int getItemCount() {
        return personalityArrayList.size();
    }
}

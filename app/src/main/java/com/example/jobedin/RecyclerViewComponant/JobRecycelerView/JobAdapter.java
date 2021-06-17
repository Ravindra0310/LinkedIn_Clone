package com.example.jobedin.RecyclerViewComponant.JobRecycelerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobedin.R;


import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobViewHolder> {
    private ArrayList<JobModel> jobModelArrayList;
    public JobAdapter(ArrayList<JobModel> jobModelArrayList){
        this.jobModelArrayList=jobModelArrayList;
    }
    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewNav= LayoutInflater.from(parent.getContext()).inflate(R.layout.jobsitemlayout,parent,false);
        return new JobViewHolder(viewNav);

    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobModel model=jobModelArrayList.get(position);
        holder.setData(model);
    }

    @Override
    public int getItemCount() {
        return jobModelArrayList.size();
    }
}

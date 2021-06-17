package com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobedin.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    public ArrayList<NotificationModel> notificationModels=new ArrayList<>();

    public NotificationAdapter(ArrayList<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationitem,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
         NotificationModel notificationModel=notificationModels.get(position);
         holder.setData(notificationModel);
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }
}

package com.example.jobedin.RecyclerViewComponant;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobedin.R;

public class NavViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvMyNetwork;
    public TextView mTvInvitations;

    public NavViewHolder(@NonNull View itemView) {
        super(itemView);
        mTvMyNetwork=itemView.findViewById(R.id.tvManageMyNetwork);
        mTvInvitations=itemView.findViewById(R.id.tvInvitations);
    }
    public void setData(Personality personality){
        mTvMyNetwork.setText(personality.getName());
        mTvInvitations.setText(personality.getName());
    }
}

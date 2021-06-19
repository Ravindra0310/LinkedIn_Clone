package com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobedin.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvNotification;
    private TextView mTvNotificationDescription;
    private TextView mTvNotificationTime;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        mIvNotification=itemView.findViewById(R.id.ivNotificationImageLogo);
        mTvNotificationDescription=itemView.findViewById(R.id.tvNotificationContent);
        mTvNotificationTime=itemView.findViewById(R.id.tvNotificationTime);
    }
    public void setData(NotificationModel notificationModel){
        Glide.with(mIvNotification).load(notificationModel.getPostImage()).into(mIvNotification);
        mTvNotificationDescription.setText(notificationModel.getText());
        mTvNotificationTime.setText(notificationModel.getTime());
    }
}

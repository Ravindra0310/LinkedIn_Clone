package com.example.jobedin.RecyclerViewComponant.JobRecycelerView;



import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobedin.R;

import org.w3c.dom.Text;

public class JobViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvCompanyImage;
    private TextView mTvJobType;
    private TextView mTvCompanyName;
    private TextView mTvJobLocation;
    private TextView mTvJobPostTiming;

    public JobViewHolder(@NonNull View itemView) {
        super(itemView);
        mIvCompanyImage=itemView.findViewById(R.id.ivJobImageLogo);
        mTvJobType=itemView.findViewById(R.id.tvJobTextHeader);
        mTvCompanyName=itemView.findViewById(R.id.tvCompanyName);
        mTvJobLocation=itemView.findViewById(R.id.tvJobLocation);
        mTvJobPostTiming=itemView.findViewById(R.id.tvJobPostTiming);

    }
    public void setData(JobModel jobModel){
        mIvCompanyImage.setImageResource(jobModel.getImageId());
        mTvJobType.setText(jobModel.getJobType());
        mTvCompanyName.setText(jobModel.getCompanyName());
        mTvJobLocation.setText(jobModel.getJobLocation());
        mTvJobPostTiming.setText(jobModel.getJobPostTiming());
    }

}

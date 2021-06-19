package com.example.jobedin.RecyclerViewComponant;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobedin.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalityViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImg;
    private CircleImageView mCircularImgView;
    private TextView mTvName;
    private TextView mTvProfession;
    private TextView mTvExperience;
    public PersonalityViewHolder(@NonNull View itemView) {
        super(itemView);
        itemViews(itemView);
    }

    private void itemViews(View itemView) {
        mImg=itemView.findViewById(R.id.ivBackGroundImage);
        mCircularImgView=itemView.findViewById(R.id.ivCircularImage);
        mTvName=itemView.findViewById(R.id.tvName1);
        mTvProfession=itemView.findViewById(R.id.tvProfileDescreption);
        mTvExperience=itemView.findViewById(R.id.tvConnections);
    }
    public void setData(Personality personality){
        Glide.with(mCircularImgView).load(personality.getImageId()).into(mCircularImgView);
        mImg.setImageResource(personality.getCircularImgId());
        mTvName.setText(personality.getName());
        mTvProfession.setText(personality.getProfession());
        mTvExperience.setText(personality.getExperience());
    }
}

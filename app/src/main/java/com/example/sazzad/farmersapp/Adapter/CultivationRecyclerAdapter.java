package com.example.sazzad.farmersapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sazzad.farmersapp.CultivationDescActivity;
import com.example.sazzad.farmersapp.Model.CropCultivation;
import com.example.sazzad.farmersapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CultivationRecyclerAdapter extends RecyclerView.Adapter <CultivationRecyclerAdapter.ViewHolder>{

    public Context context;
    public List<CropCultivation>crop_list;
    private FirebaseFirestore firebaseFirestore;
    public CultivationRecyclerAdapter(List<CropCultivation> crop_list) {
        this.crop_list=crop_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.crop_item_list, viewGroup, false);
        context=viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CultivationRecyclerAdapter.ViewHolder viewHolder, int i) {
        String cropName=crop_list.get(i).getCropName();
        final String place=crop_list.get(i).getPlace();
        String time=crop_list.get(i).getTime();
        String process=crop_list.get(i).getProcess();
        String imageUrl=crop_list.get(i).getImage_url();
        final String cropId=crop_list.get(i).getCultivationId();

        viewHolder.setCropName(cropName);
        viewHolder.setCropImage(imageUrl);
        viewHolder.layoutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CultivationDescActivity.class);
                intent.putExtra("crop_id", cropId);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return crop_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        ImageView cropImage;
        TextView txtCropName;
        View layoutLinear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            layoutLinear= mView.findViewById(R.id.layout_linear);
        }

        public void setCropName(String cropName) {
            txtCropName=mView.findViewById(R.id.txt_cropName);
            txtCropName.setText(cropName);
        }

        public void setCropImage(String imageUrl) {
            cropImage=mView.findViewById(R.id.crop_image);
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(imageUrl).into(cropImage);
        }
    }
}

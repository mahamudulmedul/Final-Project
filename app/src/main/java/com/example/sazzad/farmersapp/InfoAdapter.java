package com.example.sazzad.farmersapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.sazzad.farmersapp.Model.Users;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {
    public Context context;
    public List<Users>info_list ;

    public InfoAdapter(List<Users> info_list){

        this.info_list= info_list;
    }
    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_list, viewGroup, false);

        context=viewGroup.getContext();
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder infoViewHolder, int i) {

            String name =info_list.get(i).getName();
        infoViewHolder.txtDesc.setText(name);

    }

    @Override
    public int getItemCount() {
        return info_list.size();
    }


    public class InfoViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        ImageView user_image;
        TextView txtDesc;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            user_image=mView.findViewById(R.id.info_user_image);
            txtDesc=mView.findViewById(R.id.txt_info);
        }
    }


}

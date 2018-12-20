package com.example.sazzad.farmersapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sazzad.farmersapp.Adapter.CultivationRecyclerAdapter;
import com.example.sazzad.farmersapp.Model.CropCultivation;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CultivationActivity extends Fragment {
    public Context context;

    private RecyclerView mResultList;
    private CultivationRecyclerAdapter cultivationRecyclerAdapter;
    public List<CropCultivation> crop_list;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CultivationActivity(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cultivation, container, false);


        crop_list = new ArrayList<>();

        mResultList = view.findViewById(R.id.crop_list);
        cultivationRecyclerAdapter = new CultivationRecyclerAdapter(crop_list);
        mResultList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mResultList.setHasFixedSize(true);
        mResultList.setAdapter(cultivationRecyclerAdapter);

        db.collection("Cultivation").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()== DocumentChange.Type.ADDED){


                        CropCultivation cropCultivation = doc.getDocument().toObject(CropCultivation.class);
                        cropCultivation.setCultivationId(doc.getDocument().getId());
                        crop_list.add(cropCultivation);
                        cultivationRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return view;
    }
}

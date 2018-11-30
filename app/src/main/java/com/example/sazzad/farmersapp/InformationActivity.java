package com.example.sazzad.farmersapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import com.example.sazzad.farmersapp.Model.Farmer;
import com.example.sazzad.farmersapp.Model.Users;

public class InformationActivity extends Fragment {

    private EditText mSearch;
    private Button btnDealer,btnFarmer;
    private ImageButton btnSearch;
    public Context context;

    private RecyclerView mResultList;
    private InfoAdapter infoAdapter;
    public List<Users> info_list;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // private CollectionReference dealerRef = db.collection("DealerCollection");
    private CollectionReference farmerRef = db.collection("Users");

    public InformationActivity() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_information, container, false);
        btnSearch=view.findViewById(R.id.imageButton);
        btnDealer=view.findViewById(R.id.btn_dealer);
        btnFarmer=view.findViewById(R.id.btn_farmer);
        mSearch=view.findViewById(R.id.editText_Search);

        info_list = new ArrayList<>();

        mResultList = view.findViewById(R.id.info_list);
        infoAdapter = new InfoAdapter(info_list);
        mResultList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mResultList.setHasFixedSize(true);
        mResultList.setAdapter(infoAdapter);

        btnDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_list.clear();
                db.collection("Users").whereEqualTo("RegType","ডিলার").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType() == DocumentChange.Type.ADDED){
                                Users users=doc.getDocument().toObject(Users.class);
                                info_list.add(users);
                                infoAdapter.notifyDataSetChanged();

                            }

                        }
                    }
                });
            }
        });

        btnFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_list.clear();
                db.collection("Users").whereEqualTo("RegType","কৃষক").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType() == DocumentChange.Type.ADDED){
                                Users users=doc.getDocument().toObject(Users.class);
                                info_list.add(users);
                                infoAdapter.notifyDataSetChanged();

                            }

                        }
                    }
                });
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_list.clear();
                String city=mSearch.getText().toString();
                db.collection("Users").whereEqualTo("city",city).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType() == DocumentChange.Type.ADDED){
                                Users users=doc.getDocument().toObject(Users.class);
                                info_list.add(users);
                                infoAdapter.notifyDataSetChanged();

                            }

                        }
                    }
                });
            }
        });

        info();
        return view;

}




    public void info(){
        db.collection("Users").whereEqualTo("RegType","ডিলার").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        Users users=doc.getDocument().toObject(Users.class);
                        info_list.add(users);
                        infoAdapter.notifyDataSetChanged();

                    }

                }
            }
        });


    }
}

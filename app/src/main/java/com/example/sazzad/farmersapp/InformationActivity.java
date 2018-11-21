package com.example.sazzad.farmersapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class InformationActivity extends AppCompatActivity {

    private TextView txtInfo;
    private EditText mSearch;
    private Button btnSearch;
    public Context context;

    private RecyclerView mResultList;
    private InfoAdapter infoAdapter;
    public List<Users> info_list ;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
   // private CollectionReference dealerRef = db.collection("DealerCollection");
    private CollectionReference farmerRef = db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        txtInfo=findViewById(R.id.txt_info);
        info_list=new ArrayList<>();

        mResultList =findViewById(R.id.info_list);
        infoAdapter=new InfoAdapter(info_list);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setHasFixedSize(true);
        mResultList.setAdapter(infoAdapter);




        info();

    }

    public void load(View view) {

        db.collection("Users").whereEqualTo("RegType","কৃষক").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Users info_list = documentSnapshot.toObject(Users.class);

//                            String name = note.getName();
//                            String phone = note.getPhoneNo();
//                            String road = note.getRoad();
//                            String city = note.getCity();
//                            String district = note.getDistrict();
//                            String type = note.getType();
//                            String regType=note.getRegType();
//                            String image_uri=note.getImage_url();
//
//
//                            data += "Name: " + name
//                                    + "\nPhone: " + phone + "\nAddress: " + road + ", " + city + ", " + district + "\nType: " + type + "\n\n";
                        }


//                        ImageView user_image=mResultList.findViewById(R.id.blog_user_image);
//                        //txtInfo=mResultList.findViewById(R.id.txtinfo);
//                       // Glide.with(context).load().into(user_image);
//                        txtInfo.setText(data);
                    }
                });
    }

    public void farmerInfo(View view) {
        farmerRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Farmer note = documentSnapshot.toObject(Farmer.class);

                            String name = note.getName();
                            String phone = note.getPhoneNo();
                            String road = note.getRoad();
                            String city = note.getCity();
                            String district = note.getDistrict();
                            String type = note.getType();


                            data += "Name: " + name
                                    + "\nPhone: " + phone + "\nAddress: " + road + ", " + city + ", " + district + "\nType: " + type + "\n\n";
                        }

                       // txtInfo.setText(data);
                    }
                });


    }
    public void info(){
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
}

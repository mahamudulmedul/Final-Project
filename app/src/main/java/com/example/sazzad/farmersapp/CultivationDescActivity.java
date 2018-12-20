package com.example.sazzad.farmersapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CultivationDescActivity extends AppCompatActivity {
    private String cropId ;
    TextView cropName,cultivationPlace,cultivationTime,cultivationProcess;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation_desc);
        cropName=findViewById(R.id.txt_crop_name);
        cultivationPlace=findViewById(R.id.cultivation_place);
        cultivationTime=findViewById(R.id.cultivation_time);
        cultivationProcess=findViewById(R.id.cultivation_process);

        if(getIntent().getExtras().getString("crop_id") != null){
            cropId = getIntent().getExtras().getString("crop_id");
            Log.d("Blog ID",cropId);
        }

        db.collection("Cultivation").document(cropId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String name = task.getResult().getString("cropName");
                    String place = task.getResult().getString("place");
                    String time = task.getResult().getString("time");
                    String process = task.getResult().getString("process");

                    cropName.setText(name);
                    cultivationPlace.setText(place);
                    cultivationTime.setText(time);
                    cultivationProcess.setText(process);
                }
            }
        });
    }
}

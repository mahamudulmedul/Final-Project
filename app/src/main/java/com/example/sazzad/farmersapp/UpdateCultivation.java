package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateCultivation extends AppCompatActivity {

    Button btnSave;
    EditText editCropName,editTime,editPlace,editProcess;
    ImageView cropImage;
    private Uri cropImageUri = null;
    private StorageReference storageReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cultivation);
        editCropName=findViewById(R.id.edit_cropName);
        editTime=findViewById(R.id.edit_time);
        editPlace=findViewById(R.id.edit_place);
        editProcess=findViewById(R.id.edit_process);
        btnSave=findViewById(R.id.btn_save);
        cropImage=findViewById(R.id.new_crop_image);
        storageReference = FirebaseStorage.getInstance().getReference();

        cropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cropName=editCropName.getText().toString();
                final String place=editPlace.getText().toString();
                final String time=editTime.getText().toString();
                final String process=editProcess.getText().toString();

                final String randomName = UUID.randomUUID().toString();
                StorageReference filePath = storageReference.child("crop_image").child(randomName + ".jag");
                filePath.putFile(cropImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            String downloadUri = task.getResult().getDownloadUrl().toString();
                            Map<String, Object> postMap = new HashMap<>();
                            postMap.put("image_url", downloadUri);
                            postMap.put("cropName", cropName);
                            postMap.put("place", place);
                            postMap.put("time", time);
                            postMap.put("process", process);

                            db.collection("Cultivation").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(UpdateCultivation.this, "add", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });



            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {

            cropImageUri = data.getData();
            cropImage.setImageURI(cropImageUri);
        }

    }
}

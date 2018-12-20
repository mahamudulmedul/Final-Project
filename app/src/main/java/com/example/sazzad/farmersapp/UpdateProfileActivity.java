package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNo;
    private EditText editTextRoad;
    private EditText editTextCity;
    private EditText editTextDistrict;
    private EditText editTextType;
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button updateButton;
    private ImageView profileImg;
    private String current_user_id;
    private ProgressBar profileProgress;
    String image;
    String name;
    String phoneNo;
    String road;
    String city;
    String district;
    String type;
    String email;

    private static final int PICK_IMAGE = 1;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private Uri postImageUri = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editTextName = findViewById(R.id.edit_name);
        editTextPhoneNo = findViewById(R.id.edit_phoneno);
        editTextRoad = findViewById(R.id.edit_road);
        editTextCity = findViewById(R.id.edit_city);
        editTextDistrict = findViewById(R.id.edit_district);
        editTextType = findViewById(R.id.edit_type);
        editTextEmail = findViewById(R.id.edit_email);
        updateButton = findViewById(R.id.btnUpdate);
        profileImg = findViewById(R.id.profile_Image);
        profileProgress=findViewById(R.id.profileProgress);


        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        profileUpdate();
        Log.d(image, "onCreate: ");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                profileProgress.setVisibility(View.VISIBLE);

                name = editTextName.getText().toString();
                phoneNo = editTextPhoneNo.getText().toString();
                road = editTextRoad.getText().toString();
                city = editTextCity.getText().toString();
                district = editTextDistrict.getText().toString();
                type = editTextType.getText().toString();
                email = editTextEmail.getText().toString();

                Map<String, Object> postMap = new HashMap<>();
                postMap.put("name", name);
                postMap.put("phoneNo", phoneNo);
                postMap.put("road", road);
                postMap.put("city", city);
                postMap.put("district", district);
                postMap.put("type", type);
                postMap.put("email", email);


                db.collection("Users").document(current_user_id).update(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateProfileActivity.this, "update ", Toast.LENGTH_SHORT).show();
                            profileProgress.setVisibility(View.INVISIBLE);
                        }
                        else{
                            profileProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

    }

    public void profileUpdate() {
        db.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful()){
                if(task.getResult().exists()){
                    name = task.getResult().getString("name");
                    road = task.getResult().getString("road");
                    city = task.getResult().getString("city");
                    district = task.getResult().getString("district");
                    phoneNo = task.getResult().getString("phoneNo");
                    email= task.getResult().getString("email");
                    image= task.getResult().getString("image_url");
                    type= task.getResult().getString("type");

                    editTextName.setText(name);

                    editTextRoad.setText(road);
                    editTextCity.setText(city);
                    editTextDistrict.setText(district);
                    editTextPhoneNo.setText(phoneNo);
                    editTextEmail.setText(email);
                    editTextType.setText(type);


                    RequestOptions placeholderRequest = new RequestOptions();
                    placeholderRequest.placeholder(R.drawable.default_image);

                    Glide.with(UpdateProfileActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(profileImg);

                }
            }
            }
        });

    }


}

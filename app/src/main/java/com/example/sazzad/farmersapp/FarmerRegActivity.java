package com.example.sazzad.farmersapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FarmerRegActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNo;
    private EditText editTextRoad;
    private EditText editTextCity;
    private EditText editTextDistrict;
    private EditText editTextType;
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button submitButton;
    private ImageView profileImg;
    private static final int PICK_IMAGE = 1;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private ProgressBar profileProgress;
    private Spinner reg_Type;

    private Uri postImageUri = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_reg);
        editTextName = findViewById(R.id.editText_name);
        editTextPhoneNo = findViewById(R.id.editText_phoneno);
        editTextRoad = findViewById(R.id.editText_road);
        editTextCity = findViewById(R.id.editText_city);
        editTextDistrict = findViewById(R.id.editText_district);
        editTextType = findViewById(R.id.editText_type);
        editTextEmail = findViewById(R.id.editText_email);
        submitButton = findViewById(R.id.btnsubmit);
        profileImg = findViewById(R.id.profile_image);
        profileProgress=findViewById(R.id.profile_progress);
        reg_Type=findViewById(R.id.regType);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        String[] reglist={"কৃষক","ডিলার"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reglist);
        reg_Type.setAdapter(adapter);


        current_user_id = firebaseAuth.getCurrentUser().getUid();

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(FarmerRegActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(FarmerRegActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(FarmerRegActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_IMAGE);
                    }
                } else {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }

            }
        });
    }

    public void addFarmer(View v) {

        final String name = editTextName.getText().toString();
        final String phoneNo = editTextPhoneNo.getText().toString();
        final String road = editTextRoad.getText().toString();
        final String city = editTextCity.getText().toString();
        final String district = editTextDistrict.getText().toString();
        final String type = editTextType.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String regType=reg_Type.getSelectedItem().toString();

        if(name.isEmpty()){
            editTextName.setError("enter name");
            editTextName.requestFocus();
            return;
        }
        if(phoneNo.isEmpty()){
            editTextPhoneNo.setError("Phone number is required");
            editTextPhoneNo.requestFocus();
            return;
        }
        if(road.isEmpty()){
            editTextRoad.setError("enter road no");
            editTextRoad.requestFocus();
            return;
        }
        if(city.isEmpty()){
            editTextCity.setError("city");
            editTextCity.requestFocus();
            return;
        }
        if(district.isEmpty()){
            editTextDistrict.setError("district");
            editTextDistrict.requestFocus();
            return;
        }
        if(type.isEmpty()){
            editTextType.setError("harvage");
            editTextType.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("enter email");
            editTextEmail.requestFocus();
            return;
        }
        if(postImageUri==null){
            Toast.makeText(this, "please select photo", Toast.LENGTH_SHORT).show();
            return;
        }


        profileProgress.setVisibility(View.VISIBLE);

        final String randomName = UUID.randomUUID().toString();
        StorageReference filePath = storageReference.child("users_image").child(randomName + ".jag");
        filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    String downloadUri = task.getResult().getDownloadUrl().toString();

                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("image_url", downloadUri);
                    postMap.put("name", name);
                    postMap.put("phoneNo", phoneNo);
                    postMap.put("road", road);
                    postMap.put("city", city);
                    postMap.put("district", district);
                    postMap.put("type", type);
                    postMap.put("email", email);
                    postMap.put("RegType", regType);

                    db.collection("Users").document(current_user_id).set(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if (task.isSuccessful()){
                                Toast.makeText(FarmerRegActivity.this, "add ", Toast.LENGTH_SHORT).show();
                                profileProgress.setVisibility(View.INVISIBLE);
                            }
                            else{
                                profileProgress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {

            postImageUri = data.getData();
            profileImg.setImageURI(postImageUri);
        }

    }

}
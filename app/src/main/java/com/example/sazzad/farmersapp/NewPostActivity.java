package com.example.sazzad.farmersapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {
    // private Toolbar newPostToolbar;

    private ImageView newPostImage;
    private EditText newPostName,newPostPhone,newPostPrice,newPostQun,newPostAddress;
    private Button newPostBtn;

    private Uri postImageUri = null;
    private static final int PICK_IMAGE = 1;

    private ProgressBar newPostProgress;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id;

   // private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();

        //newPostToolbar = findViewById(R.id.new_post_toolbar);
        // setSupportActionBar(newPostToolbar);
        // getSupportActionBar().setTitle("Add New Post");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newPostImage = findViewById(R.id.new_post_image);
        newPostPhone=findViewById(R.id.editText_phone);
        newPostQun=findViewById(R.id.editText_Qun);
        newPostPrice=findViewById(R.id.editText_Price);
        newPostAddress=findViewById(R.id.editText_Address);
        newPostName = findViewById(R.id.editText_Name);
        newPostBtn = findViewById(R.id.post_btn);
        newPostProgress = findViewById(R.id.new_post_progress);

        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(NewPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(NewPostActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(NewPostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_IMAGE);


//                        CropImage.activity()
//                                .setGuidelines(CropImageView.Guidelines.ON)
//                                .setMinCropResultSize(512, 512)
//                                .setAspectRatio(1, 1)
//                                .start(NewPostActivity.this);

                    }

                } else {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);


//                    CropImage.activity()
//                            .setGuidelines(CropImageView.Guidelines.ON)
//                            .setMinCropResultSize(512, 512)
//                            .setAspectRatio(1, 1)
//                            .start(NewPostActivity.this);

                }


            }
        });
        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = newPostName.getText().toString();
                final String phone=newPostPhone.getText().toString();
                final String quantity=newPostQun.getText().toString();
                final String price=newPostPrice.getText().toString();
                final String address=newPostAddress.getText().toString();

                if (!TextUtils.isEmpty(name) && postImageUri != null) {

                    newPostProgress.setVisibility(View.VISIBLE);

                    final String randomName = UUID.randomUUID().toString();
                    StorageReference filePath = storageReference.child("post_images").child(randomName + ".jag");
                    filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                String downloadUri = task.getResult().getDownloadUrl().toString();

                                Map<String, Object> postMap = new HashMap<>();
                                postMap.put("image_url", downloadUri);
                                //postMap.put("image_thumb", downloadthumbUri);
                                postMap.put("name", name);
                                postMap.put("phone", phone);
                                postMap.put("quantity", quantity);
                                postMap.put("price", price);
                                postMap.put("address", address);

                                postMap.put("user_id", current_user_id);
                                postMap.put("timestamp", FieldValue.serverTimestamp());
                                firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if(task.isSuccessful()){

                                            Toast.makeText(NewPostActivity.this, "Post was added", Toast.LENGTH_LONG).show();
                                            Intent mainIntent = new Intent(NewPostActivity.this, PostActivity.class);
                                            startActivity(mainIntent);
                                            finish();
                                        }
                                        else {

                                        }
                                        newPostProgress.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } else {
                                newPostProgress.setVisibility(View.INVISIBLE);
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
            newPostImage.setImageURI(postImageUri);
        }

    }
}


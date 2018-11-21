package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView txtName, txtAddress, txtPhone, txtEmail;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String current_user_id;
    private FirebaseAuth firebaseAuth;

    private BottomNavigationView mainbottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        profileImage=findViewById(R.id.user_image);
        txtName=findViewById(R.id.txt_user_name);
        txtAddress=findViewById(R.id.txt_address);
        txtPhone=findViewById(R.id.txt_phone);
        txtEmail=findViewById(R.id.txt_email);
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        profileUpdate();

        mainbottomNav = findViewById(R.id.mainBottomNav);
        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_action_home:
                        Intent intent= new Intent(UserProfileActivity.this, PostActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        return true;

                    case R.id.bottom_action_account:


                        return true;

                    case R.id.bottom_action_notif:

                        Intent mIntent= new Intent(UserProfileActivity.this, InformationActivity.class);

                        startActivity(mIntent);
                        return true;

                    default:
                        return false;


                }

            }
        });

    }

    public void profileUpdate() {
        db.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        String name = task.getResult().getString("name");
                        String road = task.getResult().getString("road");
                        String city = task.getResult().getString("city");
                        String district = task.getResult().getString("district");
                        String phone = task.getResult().getString("phoneNo");
                        String email= task.getResult().getString("email");
                        String image= task.getResult().getString("image_url");

                        //mainImageURI = Uri.parse(image);

                        txtName.setText(name);

                        txtAddress.setText("Address: "+road+", "+city+", "+district);
                        txtPhone.setText("Phone: "+phone);
                        txtEmail.setText("Email: "+email);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.default_image);

                        Glide.with(UserProfileActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(profileImage);

                    }
                }
            }
        });

    }

    public void btnUpdate(View view) {
        Intent newPostIntent = new Intent(UserProfileActivity.this, FarmerRegActivity.class);
        startActivity(newPostIntent);
    }
}

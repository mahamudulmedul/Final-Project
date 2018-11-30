package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends Fragment {

    ImageView profileImage;
    TextView txtName, txtAddress, txtPhone, txtEmail;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String current_user_id;
    private FirebaseAuth firebaseAuth;
    private Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        profileImage=view.findViewById(R.id.user_image);
        txtName=view.findViewById(R.id.txt_user_name);
        txtAddress=view.findViewById(R.id.txt_address);
        txtPhone=view.findViewById(R.id.txt_phone);
        txtEmail=view.findViewById(R.id.txt_email);
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        btnUpdate=view.findViewById(R.id.btn_update);
        profileUpdate();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FarmerRegActivity.class);
                    startActivity(intent);
            }
        });

       return view;

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


}

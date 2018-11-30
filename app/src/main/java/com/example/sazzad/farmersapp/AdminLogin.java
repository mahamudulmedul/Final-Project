package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    EditText editTextUser, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        editTextUser=findViewById(R.id.editText_username);
        editTextPass=findViewById(R.id.editText_pass);
    }

    public void btnLogin(View view) {
        String username=editTextUser.getText().toString();
        String pass=editTextPass.getText().toString();

        if(username == "admin" && pass == "admin"){
            Intent intent= new Intent(AdminLogin.this, PriceUpdateActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
//            Intent intent= new Intent(AdminLogin.this, PriceUpdateActivity.class);
//            startActivity(intent);

        }


    }
}

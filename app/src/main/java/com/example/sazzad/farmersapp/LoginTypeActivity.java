package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
    }

    public void dealerReg(View view) {
        Intent intent= new Intent(this, DealerRegActivity.class);
        startActivity(intent);
    }

    public void farmerReg(View view) {
        Intent intent= new Intent(this, FarmerRegActivity.class);
        startActivity(intent);
    }
}

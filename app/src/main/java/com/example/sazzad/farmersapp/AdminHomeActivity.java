package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void priceUpdate(View view) {
        Intent intent = new Intent(this, PriceUpdateActivity.class);
        startActivity(intent);
    }

    public void cultivation(View view) {
        Intent intent = new Intent(this, UpdateCultivation.class);
        startActivity(intent);
    }
}

package com.example.sazzad.farmersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.sazzad.farmersapp.Model.Dealer;

public class DealerRegActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhoneNo;
    private EditText editTextRoad;
    private EditText editTextCity;
    private EditText editTextDistrict;
    private EditText editTextType;
    private EditText editTextEmail;
    private EditText editTextPass;

    private Button submitButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dealerRef = db.collection("DealerCollection");
    private DocumentReference documentRef = db.document("DealerCollection/DealerDocu");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_reg);

        editTextName = findViewById(R.id.editText_name);
        editTextPhoneNo = findViewById(R.id.editText_phoneno);
        editTextRoad = findViewById(R.id.editText_road);
        editTextCity = findViewById(R.id.editText_city);
        editTextDistrict = findViewById(R.id.editText_district);
        editTextType = findViewById(R.id.editText_type);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPass = findViewById(R.id.editText_password);
        submitButton = findViewById(R.id.btnsubmit);
    }

    public void addDealer(View v) {
        String name = editTextName.getText().toString();
        String phoneNo = editTextPhoneNo.getText().toString();
        String road = editTextRoad.getText().toString();
        String city = editTextCity.getText().toString();
        String district = editTextDistrict.getText().toString();
        String type = editTextType.getText().toString();
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();



        Dealer dealer = new Dealer(name, phoneNo,road, city, district, type, email, pass);
        dealerRef.add(dealer);



    }
}

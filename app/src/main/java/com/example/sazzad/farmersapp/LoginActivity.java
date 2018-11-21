package com.example.sazzad.farmersapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextPhone;
    private Button btnSendCode ;
    public String number,phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextPhone=findViewById(R.id.editText_phone);
        btnSendCode=findViewById(R.id.btn_SendCode);

    }


    public void login(View view) {

        phoneNum=editTextPhone.getText().toString();

        if(phoneNum.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }

        if(phoneNum.length() < 11 ){
            editTextPhone.setError("Please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }
        number="+88"+ phoneNum;


        Intent intent= new Intent(this, VerifyCodeActivity.class);
        intent.putExtra("number",number);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent= new Intent(this, PostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

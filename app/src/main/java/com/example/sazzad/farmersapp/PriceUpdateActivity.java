package com.example.sazzad.farmersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sazzad.farmersapp.Model.UpdatePrice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.google.firebase.firestore.FieldValue.serverTimestamp;

public class PriceUpdateActivity extends AppCompatActivity {

    EditText editTextProduct,editTextQuantity,editTextPrice,editTextPlace,editTextDate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_update);
        editTextProduct=findViewById(R.id.editText_productName);
        editTextQuantity=findViewById(R.id.editText_quantity);
        editTextPrice=findViewById(R.id.editText_price);
        editTextPlace=findViewById(R.id.editText_place);
        editTextDate=findViewById(R.id.editText_date);

        String deviceDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        editTextDate.setText( deviceDate);
    }

    public void btnSave(View view) {
        String productName=editTextProduct.getText().toString();
        String quantity=editTextQuantity.getText().toString();
        String price=editTextPrice.getText().toString();
        String place=editTextPlace.getText().toString();
        String date= editTextDate.getText().toString();
        if(productName.isEmpty()){
            editTextProduct.setError("enter product name");
            return;
        }
        if(quantity.isEmpty()){
            editTextQuantity.setError("enter Quantity");
            return;
        }
        if(price.isEmpty()){
            editTextPrice.setError("enter price");
            return;
        }
        if(place.isEmpty()){
            editTextPlace.setError("enter place");
            return;
        }
        if(date.isEmpty()){
            editTextDate.setError("enter date");
            return;
        }

        UpdatePrice updatePrice = new UpdatePrice(productName,quantity,price,place,date);
        db.collection("MarketPrice").add(updatePrice).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(PriceUpdateActivity.this, "add", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

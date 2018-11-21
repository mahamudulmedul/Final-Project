package com.example.sazzad.farmersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sazzad.farmersapp.Model.UpdatePrice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MarketPriceActivity extends AppCompatActivity {
    TextView txtProductname,txtQuantity,txtPrice,txtPlace,txtDate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);
        txtProductname=findViewById(R.id.txt_productName);
        txtQuantity=findViewById(R.id.txt_quantity);
        txtPrice=findViewById(R.id.txt_price);
        txtPlace=findViewById(R.id.txt_place);
        txtDate=findViewById(R.id.txt_date);

        load();

    }
    public void load(){

        db.collection("MarketPrice").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    UpdatePrice updatePrice = doc.toObject(UpdatePrice.class);

                     String productName=updatePrice.getProductName();
                     String quantity=updatePrice.getQuantity();
                     String price=updatePrice.getPrice();
                     String place =updatePrice.getPlace();
                     String date= updatePrice.getDate();

                     txtProductname.setText(productName);
                     txtQuantity.setText(quantity);
                     txtPrice.setText(price);
                     txtPlace.setText(place);
                     txtDate.setText(date);
                }

            }
        });

    }
}

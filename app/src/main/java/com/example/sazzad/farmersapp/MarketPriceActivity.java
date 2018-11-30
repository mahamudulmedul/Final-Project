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
import android.widget.TextView;

import com.example.sazzad.farmersapp.Model.UpdatePrice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MarketPriceActivity extends Fragment {
    TextView txtProductname,txtQuantity,txtPrice,txtPlace,txtDate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_market_price, container, false);
        txtProductname=view.findViewById(R.id.txt_productName);
        txtQuantity=view.findViewById(R.id.txt_quantity);
        txtPrice=view.findViewById(R.id.txt_price);
        txtPlace=view.findViewById(R.id.txt_place);
        txtDate=view.findViewById(R.id.txt_date);

        load();
      return view;
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

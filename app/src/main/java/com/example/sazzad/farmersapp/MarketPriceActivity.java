package com.example.sazzad.farmersapp;

import android.content.Context;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sazzad.farmersapp.Model.UpdatePrice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MarketPriceActivity extends Fragment {
    TextView txtProductname,txtQuantity,txtPrice,txtPlace,txtDate;
    TableRow tableRow;
    TableLayout tablePriceTable;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_market_price, container, false);
        tablePriceTable = view.findViewById(R.id.table_price);


        load();
        return view;
    }
    public void load(){

        db.collection("MarketPrice").orderBy("date", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    UpdatePrice updatePrice = doc.toObject(UpdatePrice.class);
                    setUpdatePriceInTableRow(updatePrice);
                }

            }
        });

    }

    private void setUpdatePriceInTableRow(UpdatePrice updatePrice) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow row = (TableRow) inflater.inflate(R.layout.market_price_row,null);
        txtProductname=row.findViewById(R.id.txt_productName);
        txtQuantity=row.findViewById(R.id.txt_quantity);
        txtPrice=row.findViewById(R.id.txt_price);
        txtPlace=row.findViewById(R.id.txt_place);
        txtDate=row.findViewById(R.id.txt_date);
        txtProductname.setText(updatePrice.getProductName());
        txtQuantity.setText(updatePrice.getQuantity());
        txtPrice.setText(updatePrice.getPrice());
        txtPlace.setText(updatePrice.getPlace());
        txtDate.setText(updatePrice.getDate());
        tablePriceTable.addView(row);
    }
}

package com.example.sazzad.farmersapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {
    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    private FloatingActionButton addPostBtn;

    private BottomNavigationView mainbottomNav;

    private HomeFragment homeFragment;
    private InformationActivity informationActivity;
    private UserProfileActivity userProfileActivity;
    private MarketPriceActivity marketPriceActivity;
    private CultivationActivity cultivationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("Farmers App");

        if (mAuth.getCurrentUser() != null) {

            mainbottomNav = findViewById(R.id.mainBottomNav);
            homeFragment = new HomeFragment();
            informationActivity=new InformationActivity();
            userProfileActivity =new UserProfileActivity();
            marketPriceActivity=new MarketPriceActivity();
            cultivationActivity=new CultivationActivity();


            initializeFragment();
            mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

                    switch (item.getItemId()) {

                        case R.id.bottom_action_home:
                            addPostBtn.setVisibility(View.VISIBLE);
                            replaceFragment(homeFragment, currentFragment);
                            return true;
                        case R.id.bottom_action_price:
                            addPostBtn.setVisibility(View.INVISIBLE);
                            replaceFragment(marketPriceActivity, currentFragment);
                            return true;

                        case R.id.bottom_action_account:
                            addPostBtn.setVisibility(View.INVISIBLE);
                            replaceFragment(userProfileActivity, currentFragment);
                            return true;
                        case R.id.bottom_action_cultivation:
                            addPostBtn.setVisibility(View.INVISIBLE);
                            replaceFragment(cultivationActivity, currentFragment);
                            return true;

                        case R.id.bottom_action_notif:
                            addPostBtn.setVisibility(View.INVISIBLE);
                            replaceFragment(informationActivity, currentFragment);

                            return true;

                        default:
                            return false;


                    }

                }
            });


            addPostBtn = findViewById(R.id.add_post_btn);
            addPostBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent newPostIntent = new Intent(PostActivity.this, NewPostActivity.class);
                    startActivity(newPostIntent);

                }
            });

        }


    }

    //Reg
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){

            sendToLogin();

        } else {

            current_user_id = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()){

                        if(!task.getResult().exists()){

                            Intent setupIntent = new Intent(PostActivity.this, FarmerRegActivity.class);
                            startActivity(setupIntent);
                            finish();

                        }

                    } else {

                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(PostActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();


                    }

                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout_btn:
             logOut();
               return true;

             case R.id.action_adminLogin_btn:

             Intent settingsIntent = new Intent(PostActivity.this, AdminLogin.class);
             startActivity(settingsIntent);

             return true;


            default:
                return false;


        }

    }

    private void logOut() {


        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(PostActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    private void initializeFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, homeFragment);
        fragmentTransaction.add(R.id.main_container, informationActivity);
        fragmentTransaction.add(R.id.main_container, userProfileActivity);
        fragmentTransaction.add(R.id.main_container, marketPriceActivity);
        fragmentTransaction.add(R.id.main_container, cultivationActivity);
        fragmentTransaction.hide(informationActivity);
        fragmentTransaction.hide(userProfileActivity);
        fragmentTransaction.hide(marketPriceActivity);
        fragmentTransaction.hide(cultivationActivity);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment, Fragment currentFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment == homeFragment) {
            fragmentTransaction.hide(informationActivity);
            fragmentTransaction.hide(userProfileActivity);
            fragmentTransaction.hide(marketPriceActivity);
            fragmentTransaction.hide(cultivationActivity);
        }
        if (fragment == informationActivity) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(userProfileActivity);
            fragmentTransaction.hide(marketPriceActivity);
            fragmentTransaction.hide(cultivationActivity);
        }
        if (fragment == userProfileActivity) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(informationActivity);
            fragmentTransaction.hide(marketPriceActivity);
            fragmentTransaction.hide(cultivationActivity);

        }
        if (fragment == cultivationActivity) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(userProfileActivity);
            fragmentTransaction.hide(informationActivity);
            fragmentTransaction.hide(marketPriceActivity);
        }
        if (fragment == marketPriceActivity) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(userProfileActivity);
            fragmentTransaction.hide(informationActivity);
            fragmentTransaction.hide(cultivationActivity);
        }


        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();

    }
}

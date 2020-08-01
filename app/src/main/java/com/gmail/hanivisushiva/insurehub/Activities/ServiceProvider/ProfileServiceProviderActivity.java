package com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileServiceProviderActivity extends AppCompatActivity {

    TextView username,email,mobile,website,business_des,address,discount_position,authorized_person;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_service_provider);


        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        website = findViewById(R.id.website);
        circleImageView = findViewById(R.id.profile_img);
        business_des = findViewById(R.id.business_des);
        address = findViewById(R.id.address);
        discount_position = findViewById(R.id.position);
        authorized_person = findViewById(R.id.person);


        username.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getName());
        email.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getEmail());
        mobile.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getMobile());

        website.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getWebsite());
        business_des.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getBussinessName());
        address.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getAddress());
        discount_position.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getDiscountPosition());
        authorized_person.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getAuthorisedPerson());



       // card_no.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getCardNo());
        //role.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getAddress());

        if (SharedPrefManager.get_mInstance(getApplicationContext()).getImage() != ""){
            Picasso.get().load(SharedPrefManager.get_mInstance(getApplicationContext()).getImage()).into(circleImageView);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }




}


package com.gmail.hanivisushiva.insurehub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView username,email,mobile,role,card_no;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        role = findViewById(R.id.role);
        circleImageView = findViewById(R.id.profile_img);
        card_no = findViewById(R.id.card_no);


        username.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getName());
        email.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getEmail());
        mobile.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getMobile());

        card_no.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getCardNo());
        role.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getAddress());

        if (SharedPrefManager.get_mInstance(getApplicationContext()).getImage() != ""){
            Picasso.get().load(SharedPrefManager.get_mInstance(getApplicationContext()).getImage()).into(circleImageView);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
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
}

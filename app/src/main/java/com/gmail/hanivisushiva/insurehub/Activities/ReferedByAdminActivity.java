package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.CustomerModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferedByAdminActivity extends AppCompatActivity {

    String sid;

    TextView name,phone,email,username;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refered_by_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Refered By");
        }

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);

        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        sid = intent.getStringExtra("sid");


        //sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();

        Log.e("dataSizeId",sid);
        getDataFromServer(sid);
    }


    private void getDataFromServer(String sid){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait Loading ..");
        progressDialog.show();

        Call<CustomerModel> call = RetrofitClient.getmInstance().getApi().getReferalDetails(sid);
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                progressDialog.dismiss();
                CustomerModel customerModel = response.body();
                Log.e("dataSize",customerModel.toString()+"---");
                //Log.e("dataSize",customerModel.getData().size()+"---");

                if (customerModel != null){

                    Datum datum = customerModel.getData().get(0);


                    Log.e("dataSize",datum.toString()+"---");

                    if (datum.getFirstName() != null){
                        name.setText(datum.getFirstName());
                    }

                    if (datum.getMobileNo() != null){
                        phone.setText(datum.getMobileNo());
                    }

                    if (datum.getEmail() != null){
                        email.setText(datum.getEmail());
                    }

                    if (datum.getUsername() != null){
                        username.setText(datum.getUsername());
                    }

                    if (datum.getUploadFile() != null){
                       if (!datum.getUploadFile().equals("")){
                           Picasso.get().load(datum.getUploadFile()).into(imageView);
                       }
                    }



                }else {
                    Toast_msg("null response");
                }
            }

            @Override
            public void onFailure(Call<CustomerModel> call, Throwable t) {
                progressDialog.dismiss();
               Toast_msg(t.getMessage());
            }
        });
    }


    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

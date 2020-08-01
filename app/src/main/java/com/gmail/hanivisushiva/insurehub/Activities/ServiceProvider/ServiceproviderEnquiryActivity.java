package com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceproviderEnquiryActivity extends AppCompatActivity {


    EditText name,phone,request;
    String name_txt,phone_txt,request_txt,id_txt;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider_enquiry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Enquiry");
        }

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        request = findViewById(R.id.request);
        submit = findViewById(R.id.submit);
        id_txt  =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name_txt = name.getText().toString().trim();
                phone_txt = phone.getText().toString().trim();
                request_txt = request.getText().toString().trim();

                if (TextUtils.isEmpty(name_txt)) {
                    name.setError("Required");
                    name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone_txt)) {
                    phone.setError("Required");
                    phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(request_txt)) {
                    request.setError("Required");
                    request.requestFocus();
                    return;
                }

                SendDataToServer(id_txt,phone_txt,name_txt,request_txt);

            }
        });

    }



    private void SendDataToServer(String id,String phone,String name,String request){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().serviceProviderEnquiry(id,name,phone,request);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.hide();
                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        Toast_msg("Enquiry added successfully");
                        finish();
                    }else {
                        Toast_msg("something went wrong, try again later");
                    }
                }else {
                    Toast_msg("server returned Null response");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.hide();
               Toast_msg(t.getLocalizedMessage());
            }
        });

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

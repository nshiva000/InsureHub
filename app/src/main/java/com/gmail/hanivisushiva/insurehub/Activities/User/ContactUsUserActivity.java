package com.gmail.hanivisushiva.insurehub.Activities.User;

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

public class ContactUsUserActivity extends AppCompatActivity {

    EditText name,phone,request;
    String name_txt,phone_txt,request_txt;
    Button submit;
    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_request_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Contact Us");
        }
        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        request = findViewById(R.id.request);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_txt = name.getText().toString();
                phone_txt = phone.getText().toString();
                request_txt = request.getText().toString();


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


                Raiserequest();
            }
        });




    }



    private void Raiserequest(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().user_raise_request(name_txt,phone_txt,request_txt,sid);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();
                UpdateModel updateModel = response.body();
                if (updateModel != null){
                    if (updateModel.getStatus()){
                        Toast_msg(updateModel.getMessage()+"---");
                        finish();
                    }else {
                        Toast_msg(updateModel.getMessage()+"---");
                    }
                }else{
                    Toast_msg("Null response from the server");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast_msg(t.getMessage()+"");
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

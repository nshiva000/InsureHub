package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class ReferAndEarnUserActivity extends AppCompatActivity {

    EditText name,phone,email,description,requirement;
    String name_txt,phone_txt,email_txt,description_txt,requirement_txt,sid;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Refer And Earn");
        }


        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        requirement = findViewById(R.id.requirement);
        description = findViewById(R.id.description);
        submit = findViewById(R.id.submit);

        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_txt = name.getText().toString();
                phone_txt = phone.getText().toString();
                email_txt = email.getText().toString();
                requirement_txt = requirement.getText().toString();
                description_txt = description.getText().toString();

                sendDataToServer();
            }
        });




    }


    private void sendDataToServer(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();
        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().referAndEarn(sid,name_txt,phone_txt,requirement_txt,email_txt,description_txt);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();
                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                      Toast_msg("Referred Successfully");
                      finish();
                    }else {
                        Toast_msg("something wrong try again later");
                    }
                }else {
                    Toast_msg("response is null");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error",t.getMessage());
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

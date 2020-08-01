package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import com.gmail.hanivisushiva.insurehub.Adapter.CustomerAllRequestsAdapter;
import com.gmail.hanivisushiva.insurehub.Adapter.CustomerAllServicesAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerAllServicesModel.CustomerAllServicesModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerAllServicesModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCustomerServicesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;



    ArrayList<Datum> arrayList = new ArrayList();
    String sid;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_customer_services);

        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Requests");
        }




        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new CustomerAllServicesAdapter(AllCustomerServicesActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        intent = getIntent();
        if (intent.getStringExtra("id") != null){
            getAllServices(intent.getStringExtra("id"));
            //Toast_msg(intent.getStringExtra("edit_id"));
        }else {
            getAllServices(sid);
            //Toast_msg(intent.getStringExtra("edit_id"));
        }


    }



    private void getAllServices(String id){
        //Toast_msg("id"+intent.getStringExtra("edit_id"));
        Call<CustomerAllServicesModel> call = RetrofitClient.getmInstance().getApi().getCustomerAllServices(id);
        call.enqueue(new Callback<CustomerAllServicesModel>() {
            @Override
            public void onResponse(Call<CustomerAllServicesModel> call, Response<CustomerAllServicesModel> response) {
                CustomerAllServicesModel customerAllServicesModel = response.body();
                if (customerAllServicesModel != null){
                    if (customerAllServicesModel.getStatus()){
                        arrayList.addAll(customerAllServicesModel.getData());
                        adapter.notifyDataSetChanged();

                    }else {
                        Toast_msg("there is no data to show");
                    }
                }else {
                    Toast_msg("server returned null response");
                }
            }

            @Override
            public void onFailure(Call<CustomerAllServicesModel> call, Throwable t) {

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

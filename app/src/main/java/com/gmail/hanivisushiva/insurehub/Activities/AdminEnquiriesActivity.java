package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import com.gmail.hanivisushiva.insurehub.Adapter.CustomerRequestPolicyAdapter;
import com.gmail.hanivisushiva.insurehub.Model.AdminCustomerRequestsModel.AdminCustomerRequestsModel;
import com.gmail.hanivisushiva.insurehub.Model.AdminCustomerRequestsModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEnquiriesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Enquiries");
        }

        getCustomerPolicyRequest();


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new CustomerRequestPolicyAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);
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


    private void getCustomerPolicyRequest(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<AdminCustomerRequestsModel> call = RetrofitClient.getmInstance().getApi().admin_customer_request_policy();
        call.enqueue(new Callback<AdminCustomerRequestsModel>() {
            @Override
            public void onResponse(Call<AdminCustomerRequestsModel> call, Response<AdminCustomerRequestsModel> response) {
                progressDialog.dismiss();
                AdminCustomerRequestsModel adminCustomerRequestsModel = response.body();

                if (adminCustomerRequestsModel != null){
                    if (adminCustomerRequestsModel.getStatus()){
                        arrayList.addAll(adminCustomerRequestsModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast_msg("No data to Display");
                    }
                }else {
                    Toast_msg("server returned null response");
                }
            }

            @Override
            public void onFailure(Call<AdminCustomerRequestsModel> call, Throwable t) {
                 progressDialog.dismiss();
            }
        });

    }
}

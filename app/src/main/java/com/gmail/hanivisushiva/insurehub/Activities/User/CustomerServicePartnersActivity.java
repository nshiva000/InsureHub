package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.CustomerServicePartnersAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerServicePartners.CustomerServicePartners;
import com.gmail.hanivisushiva.insurehub.Model.CustomerServicePartners.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerServicePartnersActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_partners);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Service Partners");
        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomerServicePartnersAdapter(CustomerServicePartnersActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
        getServicePartners();
    }
    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void getServicePartners() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();
        Call<CustomerServicePartners> call = RetrofitClient.getmInstance().getApi().getServicePartners();
        call.enqueue(new Callback<CustomerServicePartners>() {
            @Override
            public void onResponse(Call<CustomerServicePartners> call, Response<CustomerServicePartners> response) {
                progressDialog.dismiss();
                CustomerServicePartners customerServicePartners = response.body();
                if (customerServicePartners != null){
                    if (customerServicePartners.getStatus()){
                        arrayList.addAll(customerServicePartners.getData());
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast_msg("server returned null response");
                }
            }

            @Override
            public void onFailure(Call<CustomerServicePartners> call, Throwable t) {
                progressDialog.dismiss();
                Toast_msg(t.getMessage());
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
}

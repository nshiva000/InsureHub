package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.CustomerMyRewardsAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerMyRewards.CustomerMyRewards;
import com.gmail.hanivisushiva.insurehub.Model.CustomerMyRewards.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerMyRewardsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList<>();
    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_my_rewards);



        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomerMyRewardsAdapter(CustomerMyRewardsActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to tioolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("My Rewards");
        }

        getRewards(sid);
    }


    private void getRewards(String id){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait");
        progressDialog.show();

        Call<CustomerMyRewards> call = RetrofitClient.getmInstance().getApi().get_customer_rewards(id);
        call.enqueue(new Callback<CustomerMyRewards>() {
            @Override
            public void onResponse(Call<CustomerMyRewards> call, Response<CustomerMyRewards> response) {
                progressDialog.dismiss();
                CustomerMyRewards customerMyRewards = response.body();
                if (customerMyRewards != null){
                    if (customerMyRewards.getStatus()){
                        arrayList.addAll(customerMyRewards.getData());
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast_msg("server returned null response");
                }
            }

            @Override
            public void onFailure(Call<CustomerMyRewards> call, Throwable t) {
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

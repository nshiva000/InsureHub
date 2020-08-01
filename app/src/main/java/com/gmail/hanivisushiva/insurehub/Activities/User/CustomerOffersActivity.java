package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.CustomerOffersAdapter;
import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.AdminOffersModel;
import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerOffersActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_offers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Offers");
        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomerOffersAdapter(CustomerOffersActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        getOffers();
    }
    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void getOffers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();


        Call<AdminOffersModel> call = RetrofitClient.getmInstance().getApi().getOffers();
        call.enqueue(new Callback<AdminOffersModel>() {
            @Override
            public void onResponse(Call<AdminOffersModel> call, Response<AdminOffersModel> response) {
                progressDialog.dismiss();
                AdminOffersModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        arrayList.clear();
                        arrayList.addAll(updateModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast_msg("No Offers to show");
                    }
                }else{
                    Toast_msg("Null response from the server");
                }

            }

            @Override
            public void onFailure(Call<AdminOffersModel> call, Throwable t) {
                progressDialog.dismiss();
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

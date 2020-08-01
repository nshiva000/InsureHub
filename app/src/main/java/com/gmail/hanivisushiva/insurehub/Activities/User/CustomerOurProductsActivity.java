package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.CustomerProductsAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerProducts.CustomerProducts;
import com.gmail.hanivisushiva.insurehub.Model.CustomerProducts.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerOurProductsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList<>();

    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_our_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Our Products");
        }

        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomerProductsAdapter(CustomerOurProductsActivity.this,arrayList);
        recyclerView.setAdapter(adapter);


        getProducts();
    }
    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void getProducts() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait");
        progressDialog.show();

        Call<CustomerProducts> call = RetrofitClient.getmInstance().getApi().getCustomerProducts();
        call.enqueue(new Callback<CustomerProducts>() {
            @Override
            public void onResponse(Call<CustomerProducts> call, Response<CustomerProducts> response) {
                progressDialog.dismiss();
                CustomerProducts customerProducts = response.body();
                if (customerProducts != null){
                    if (customerProducts.getStatus()){
                        arrayList.addAll(customerProducts.getData());
                        adapter.notifyDataSetChanged();

                        Toast_msg(customerProducts.getStatus().toString());
                    }
                }else {
                    Toast_msg("server returned null");
                }
            }

            @Override
            public void onFailure(Call<CustomerProducts> call, Throwable t) {
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

package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.CustomerPolicyAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerPolicy.CustomerPolicy;
import com.gmail.hanivisushiva.insurehub.Model.CustomerPolicy.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerMyPolicyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList<>();
    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_my_policy);

        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("My Policy");
        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomerPolicyAdapter(CustomerMyPolicyActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        getPolicy(sid);
    }
    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }



    private void getPolicy(String id){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait");
        progressDialog.show();

        Call<CustomerPolicy> call = RetrofitClient.getmInstance().getApi().getCustomerPolicy(id);
        call.enqueue(new Callback<CustomerPolicy>() {
            @Override
            public void onResponse(Call<CustomerPolicy> call, Response<CustomerPolicy> response) {
                progressDialog.dismiss();
                CustomerPolicy customerPolicy = response.body();
                if (customerPolicy != null){
                    if (customerPolicy.getStatus()){
                        arrayList.addAll(customerPolicy.getData());
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast_msg("server Returned Null response");
                }
            }

            @Override
            public void onFailure(Call<CustomerPolicy> call, Throwable t) {
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

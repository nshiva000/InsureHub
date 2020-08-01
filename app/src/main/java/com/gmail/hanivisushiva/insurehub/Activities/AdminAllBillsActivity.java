package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.ServiceProviderAllBillsAdapter;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderAllBillsModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderAllBillsModel.ServiceProviderAllBillsModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllBillsActivity extends AppCompatActivity {

    String sid;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList();


    @Override
    protected void onStart() {
        super.onStart();
        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();
        //getAllBills(sid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_bills);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);




        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Bills");
        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ServiceProviderAllBillsAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);

getAllBills();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void getAllBills(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();

        Call<ServiceProviderAllBillsModel> call = RetrofitClient.getmInstance().getApi().admin_get_all_bills();
        call.enqueue(new Callback<ServiceProviderAllBillsModel>() {
            @Override
            public void onResponse(Call<ServiceProviderAllBillsModel> call, Response<ServiceProviderAllBillsModel> response) {
                progressDialog.dismiss();

                ServiceProviderAllBillsModel serviceProviderAllBillsModel = response.body();

                if (serviceProviderAllBillsModel != null){
                    if (serviceProviderAllBillsModel.getStatus()){
                        arrayList.clear();
                        arrayList.addAll(serviceProviderAllBillsModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast_msg("No data to display");
                    }
                }else {
                    Toast_msg("server returned null response");
                }
            }

            @Override
            public void onFailure(Call<ServiceProviderAllBillsModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }



    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


}

package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.HomeAdapter;
import com.gmail.hanivisushiva.insurehub.Adapter.ServiceProviderAdapter;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.ServiceProviderModel;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList();
    FloatingActionButton floatingActionButton;

    @Override
    protected void onStart() {
        super.onStart();
        getServiceProvider();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Service Provider");
        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ServiceProviderAdapter(ServiceProviderActivity.this,arrayList);
        recyclerView.setAdapter(adapter);


        floatingActionButton = findViewById(R.id.add_new);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceProviderActivity.this,AddNewServiceProviderActivity.class);
                startActivity(intent);
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


    private void getServiceProvider(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<ServiceProviderModel> call = RetrofitClient.getmInstance().getApi().getServiceProvider();
        call.enqueue(new Callback<ServiceProviderModel>() {
            @Override
            public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {
                progressDialog.dismiss();
                ServiceProviderModel serviceProviderModel = response.body();


                if (serviceProviderModel != null){
                    if (serviceProviderModel.getStatus()){
                        arrayList.clear();
                        arrayList.addAll(serviceProviderModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast_msg("No Data to show");
                    }
                }else {
                    Toast_msg("server null response");
                }

            }

            @Override
            public void onFailure(Call<ServiceProviderModel> call, Throwable t) {
              Toast_msg(t.getMessage());
              progressDialog.dismiss();
            }
        });
    }


    public void DeleteCategory(String id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().delete_serviceProvider(id);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();

                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        Toast_msg("Deleted successfully");
                        getServiceProvider();
                    }else {
                        Toast_msg("Something Went wrong, Try agian");
                    }
                }else {
                    Toast_msg("server returning null message");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("errorMsg",t.getMessage());

            }
        });
    }
}

package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.CustomerAllRequestsAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerAllRequestsModel.CustomerAllRequestsModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerAllRequestsModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCustomerRequestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;



    ArrayList<Datum> arrayList = new ArrayList();
    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_customer_request);

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

        adapter = new CustomerAllRequestsAdapter(AllCustomerRequestActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        getAllRequests(sid);

    }




   private void getAllRequests(String id){
       Call<CustomerAllRequestsModel> call = RetrofitClient.getmInstance().getApi().getCustomerAllRequests(id);
       call.enqueue(new Callback<CustomerAllRequestsModel>() {
           @Override
           public void onResponse(Call<CustomerAllRequestsModel> call, Response<CustomerAllRequestsModel> response) {
               CustomerAllRequestsModel customerAllRequestsModel = response.body();
               if (customerAllRequestsModel != null){
                   if (customerAllRequestsModel.getStatus()){
                       arrayList.addAll(customerAllRequestsModel.getData());
                       adapter.notifyDataSetChanged();

                   }else {
                       Toast_msg("there is no data to show");
                   }
               }else {
                   Toast_msg("server returned null response");
               }
           }

           @Override
           public void onFailure(Call<CustomerAllRequestsModel> call, Throwable t) {

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

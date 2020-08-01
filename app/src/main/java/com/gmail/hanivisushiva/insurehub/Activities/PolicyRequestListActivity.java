package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.AdminPolicyRequestAdapter;
import com.gmail.hanivisushiva.insurehub.Adapter.CustomerAdapter;
import com.gmail.hanivisushiva.insurehub.Model.AdminPolicyRequestModel.AdminPolicyRequestModel;
import com.gmail.hanivisushiva.insurehub.Model.AdminPolicyRequestModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolicyRequestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Datum> arrayList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);



        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Policy Request List");
        }


        getCustomerPolicyRequest();


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AdminPolicyRequestAdapter(PolicyRequestListActivity.this,arrayList);
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

        Call<AdminPolicyRequestModel> call = RetrofitClient.getmInstance().getApi().admin_policy_requests();
        call.enqueue(new Callback<AdminPolicyRequestModel>() {
            @Override
            public void onResponse(Call<AdminPolicyRequestModel> call, Response<AdminPolicyRequestModel> response) {
                progressDialog.dismiss();
                AdminPolicyRequestModel adminCustomerRequestsModel = response.body();

                if (adminCustomerRequestsModel != null){
                    if (adminCustomerRequestsModel.getStatus()){
                        arrayList.clear();
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
            public void onFailure(Call<AdminPolicyRequestModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }



    public void Approve(String id){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().admin_approve_request(id);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();

                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        ToastMsg("Approved successfully");
                        getCustomerPolicyRequest();
                    }else {
                        ToastMsg("Something Went wrong, Try agian");
                    }
                }else {
                    ToastMsg("server returning null message");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("errorMsg",t.getMessage());

            }
        });
    }


    public void Reject(String id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().admin_reject_request(id);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();

                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        ToastMsg("Rejected");
                        getCustomerPolicyRequest();
                    }else {
                        ToastMsg("Something Went wrong, Try agian");
                    }
                }else {
                    ToastMsg("server returning null message");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("errorMsg",t.getMessage());

            }
        });
    }
    private void ToastMsg(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}

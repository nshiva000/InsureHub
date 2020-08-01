package com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.AddNewCustomerActivity;
import com.gmail.hanivisushiva.insurehub.Activities.CustomerActivity;
import com.gmail.hanivisushiva.insurehub.Adapter.CustomerAdapter;
import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.CustomerModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderCustomerList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    EditText searchTxt;

    ArrayList<Datum> arrayList = new ArrayList();
    ArrayList<Datum> searchArrayList = new ArrayList();
    ArrayList<Datum> storeArrayList = new ArrayList();
    String role,sid;

    @Override
    protected void onStart() {
        super.onStart();
        getCustomerData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_customer_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        searchTxt = findViewById(R.id.search_txt);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Customer");
        }


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new CustomerAdapter(ServiceProviderCustomerList.this,arrayList);
        recyclerView.setAdapter(adapter);



        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Toast_msg(s.toString());




                    if (!s.toString().equals("")){

                    if (storeArrayList != null){

                        searchArrayList.clear();
                            for (int i = 0; i< storeArrayList.size(); i++){

                                if (storeArrayList.get(i).getCardNo() != null){
                                    if (storeArrayList.get(i).getCardNo().toLowerCase().contains(s.toString().toLowerCase())){
                                        Log.e("compare",storeArrayList.get(i).getCardNo().toLowerCase()+"---"+s.toString());
                                        searchArrayList.add(storeArrayList.get(i));
                                    }else {
                                        Log.e("compare not",storeArrayList.get(i).getCardNo()+"---"+s.toString());

                                    }

                                }else {
                                    Toast_msg("empty");
                                }
                            }

                            arrayList.clear();
                            arrayList.addAll(searchArrayList);
                            //Toast_msg(searchArrayList.size()+"");
                            adapter.notifyDataSetChanged();



                    }else {
                        Toast_msg("store arraylist is null");
                    }

                    }else {
                        Toast_msg("String is empty");
                        arrayList.clear();
                        arrayList.addAll(storeArrayList);
                        adapter.notifyDataSetChanged();
                    }


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


    public void DeleteCategory(String id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().delete_customer(id);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();

                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        Toast_msg("Deleted successfully");
                        getCustomerData();
                    }else {
                        Toast_msg("Something Went wrong, Try agian");
                    }
                }else {
                    Toast_msg("server returning null message");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                Log.e("errorMsg",t.getMessage());

            }
        });
    }



    private void getCustomerData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.. please Wait");
        progressDialog.show();

        Call<CustomerModel> call = RetrofitClient.getmInstance().getApi().getCustomersList();
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                progressDialog.dismiss();
                CustomerModel serviceProviderModel = response.body();



                if (serviceProviderModel != null){
                    if (serviceProviderModel.getStatus()){
                        arrayList.clear();
                        storeArrayList.clear();
                        arrayList.addAll(serviceProviderModel.getData());
                        storeArrayList.addAll(serviceProviderModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast_msg("No Data to show");
                    }
                }else {
                    Toast_msg("server null response");
                }

            }

            @Override
            public void onFailure(Call<CustomerModel> call, Throwable t) {
                Toast_msg(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}

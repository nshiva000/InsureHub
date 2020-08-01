package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.AllReferalsAdapter;
import com.gmail.hanivisushiva.insurehub.Model.AllReferals.AllReferals;
import com.gmail.hanivisushiva.insurehub.Model.AllReferals.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllReferalsAdminActivity extends AppCompatActivity {

    ArrayList<Datum> datumArrayList = new ArrayList<>();

    String sid;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_referals_admin);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Referals");
        }

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AllReferalsAdapter(AllReferalsAdminActivity.this,datumArrayList);
        recyclerView.setAdapter(adapter);

        getAllReferals();
    }



    private void getAllReferals(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait....");
        progressDialog.show();
        Call<AllReferals> call = RetrofitClient.getmInstance().getApi().getAllReferals();
        call.enqueue(new Callback<AllReferals>() {
            @Override
            public void onResponse(Call<AllReferals> call, Response<AllReferals> response) {
                progressDialog.dismiss();
                AllReferals allReferals = response.body();

                if (allReferals != null){

                  datumArrayList.addAll(allReferals.getData());
                  adapter.notifyDataSetChanged();

                }else {
                   Toast_msg("null response");
                }
            }

            @Override
            public void onFailure(Call<AllReferals> call, Throwable t) {
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

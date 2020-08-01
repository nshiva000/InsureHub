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

import com.gmail.hanivisushiva.insurehub.Adapter.CategoryAdapter;
import com.gmail.hanivisushiva.insurehub.Interface.DeleteCategoryInterface;
import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.CategoryModel;
import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements DeleteCategoryInterface {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;



    ArrayList<Datum> arrayList = new ArrayList();

    FloatingActionButton floatingActionButton;
    DeleteCategoryInterface deleteCategoryInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Category");
        }



        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new CategoryAdapter(CategoryActivity.this,arrayList,deleteCategoryInterface);
        recyclerView.setAdapter(adapter);


        floatingActionButton = findViewById(R.id.add_new);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,AddNewCategoryActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        getCategory();
    }

    private void getCategory(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait Loading ..");
        progressDialog.show();
        Call<CategoryModel> call = RetrofitClient.getmInstance().getApi().getCategory();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.dismiss();
                CategoryModel categoryModel = response.body();
                if (categoryModel != null){
                    if (categoryModel.getStatus()){
                        //Toast_msg(categoryModel.getData().size()+"---");
                        arrayList.clear();
                        arrayList.addAll(categoryModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast_msg("There is No data To Show");
                    }
                }else {
                    Toast_msg("Server resturned null response");
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("errorMsg",t.getMessage());
                Toast_msg(t.getMessage()+"");
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

    @Override
    public void myMethod(String id) {
        Toast_msg("category id"+id);
    }


    public void DeleteCategory(String id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait Loading ..");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().delete_category(id);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();

                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        Toast_msg("Deleted successfully");
                        getCategory();
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

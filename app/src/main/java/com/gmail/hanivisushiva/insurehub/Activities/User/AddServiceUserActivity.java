package com.gmail.hanivisushiva.insurehub.Activities.User;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.AddNewServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.CategoryModel;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceUserActivity extends AppCompatActivity {

    Spinner category;
    Button submit;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    String category_txt,sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_user);



        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Service");
        }

        category = findViewById(R.id.category);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category_txt = category.getSelectedItem().toString();
                //Toast_msg(category_txt);
                add_service();

            }
        });

        get_category();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void get_category(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait");
        progressDialog.show();


        Call<CategoryModel> call = RetrofitClient.getmInstance().getApi().getCategory();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.dismiss();
                CategoryModel categoryModel = response.body();

                if (categoryModel != null){
                    if (categoryModel.getStatus()){

                        for (int i =0; i<categoryModel.getData().size();i++){
                            stringArrayList.add(categoryModel.getData().get(i).getCategoryName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddServiceUserActivity.this,android.R.layout.simple_dropdown_item_1line,stringArrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        category.setAdapter(adapter);

                    }
                }else {
                    Toast_msg("Null response");
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Toast_msg(t.getMessage());
            }
        });

    }

    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void add_service(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();
        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().user_add_service(sid,category_txt);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();
                UpdateModel updateModel = response.body();
                if (updateModel != null){
                    if (updateModel.getStatus()){
                        Toast_msg(updateModel.getMessage()+"---");
                        finish();
                    }else {
                        Toast_msg(updateModel.getMessage()+"---");
                    }
                }else{
                    Toast_msg("Null response from the server");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast_msg(t.getMessage()+"");
            }
        });
    }


}

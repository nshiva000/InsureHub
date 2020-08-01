package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewCategoryActivity extends AppCompatActivity {

    EditText category_name;
    Button add_new_category_btn;
    String txt_category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add New Category");
        }

        category_name = findViewById(R.id.category);
        add_new_category_btn = findViewById(R.id.add_new_category_btn);


        add_new_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_category_name = category_name.getText().toString().trim();
                if (TextUtils.isEmpty(txt_category_name)) {
                    category_name.setError("Required");
                    category_name.requestFocus();
                    return;
                }

                AddNewCategory(txt_category_name);
            }
        });
    }




    private void AddNewCategory(String name){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait Loading ..");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().add_new_category(name);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();
                UpdateModel updateModel = response.body();

                if (updateModel != null){
                  if (updateModel.getStatus()){
                      ToastMsg("New category Added successfully");
                      finish();
                  }else {
                      ToastMsg("Something Went wrong, Try agian");
                  }
                }else {
                    ToastMsg("server returning null message");
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                Log.e("errorMsg",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }









    private void ToastMsg(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

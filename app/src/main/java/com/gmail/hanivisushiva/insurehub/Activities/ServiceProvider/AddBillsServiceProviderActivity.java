package com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBillsServiceProviderActivity extends AppCompatActivity {

    EditText card_no,amount,discount,total,bill_date;
    String card_no_txt,amount_txt,discount_txt,total_txt,type_txt,bill_date_txt;
    Button submit;
    String sid,bid;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bills_service_provider);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        sid =  SharedPrefManager.get_mInstance(getApplicationContext()).getId();

        card_no = findViewById(R.id.card_no);
        amount = findViewById(R.id.amount);
        discount = findViewById(R.id.discount);
        total = findViewById(R.id.total);
        submit = findViewById(R.id.submit);
        bill_date = findViewById(R.id.bill_date);


        Intent intent = getIntent();
        card_no_txt = intent.getStringExtra("cardno");
        amount_txt = intent.getStringExtra("amount");
        discount_txt = intent.getStringExtra("discount");
        total_txt = intent.getStringExtra("total");
        bid = intent.getStringExtra("id");
        type_txt = intent.getStringExtra("type");



        bill_date.setInputType(InputType.TYPE_NULL);
        bill_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddBillsServiceProviderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                bill_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

            }
        });

if (type_txt != null) {

    if (type_txt.equals("edit")) {
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Edit Bills");
        }
        if (card_no_txt != null) {
            card_no.setText(card_no_txt);
        }

        if (amount_txt != null) {
            amount.setText(amount_txt);
        }

        if (discount_txt != null) {
            discount.setText(discount_txt);
        }

        if (total_txt != null) {
            total.setText(total_txt);
        }


    } else {
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Bills");
        }
    }

}else {
    if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         getSupportActionBar().setTitle("Add Bills");
    }
}







        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_no_txt = card_no.getText().toString().trim();
                amount_txt = amount.getText().toString().trim();
                discount_txt = discount.getText().toString().trim();
                total_txt = total.getText().toString().trim();
                bill_date_txt = bill_date.getText().toString().trim();

                if (TextUtils.isEmpty(card_no_txt)) {
                    card_no.setError("Required");
                    card_no.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(amount_txt)) {
                    amount.setError("Required");
                    amount.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(bill_date_txt)) {
                    bill_date.setError("Required");
                    bill_date.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(discount_txt)) {
                    discount.setError("Required");
                    discount.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(total_txt)) {
                    total.setError("Required");
                    total.requestFocus();
                    return;
                }




                if(type_txt != null){
                    if (type_txt.equals("edit")){
                        editBills();
                    }
                }else {
                    addBills();
                }





            }
        });


    }


    private void addBills(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().serviceProvider_addBills(card_no_txt,amount_txt,discount_txt,total_txt,bill_date_txt,sid);
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




    private void editBills(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().serviceProvider_EditBills(card_no_txt,amount_txt,discount_txt,total_txt,bid);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}

package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.CategoryModel;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.ServiceProviderModel;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerCategoryActivity extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();

    Spinner category;
    EditText card_no;


    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    HashMap<String,String> stringDatumHashMap = new HashMap<>();

    HashMap<String,String> categoryHashMap = new HashMap<>();
    Button mOrder,submit_btn;
    ArrayList<String> items = new ArrayList<>();


    String category_txt,services_txt,card_txt,id_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_category);
        category = findViewById(R.id.category);
        card_no = findViewById(R.id.card);
        submit_btn = findViewById(R.id.submit);

        mOrder = findViewById(R.id.btnOrder);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Customer Category");
        }

        Intent intent = getIntent();
        id_txt = intent.getStringExtra("id");





        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                get_services(category.getSelectedItemPosition()+"");
              // Toast_msg(category.getSelectedItemPosition()+"");

//                String selected_val=category.getSelectedItem().toString();
//
//                Toast.makeText(getApplicationContext(), selected_val ,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



//        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                get_services(position+"");
//                Toast_msg(position+"");
//            }
//        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_txt = categoryHashMap.get(category.getSelectedItem().toString());
                Toast_msg(id_txt);
                card_txt = card_no.getText().toString();
                sendDataToServer();

            }
        });



        getCategory();

    }


    private void sendDataToServer(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().add_customer_category(id_txt,category_txt,services_txt,card_txt);
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


    private void getCategory(){
        Call<CategoryModel> call = RetrofitClient.getmInstance().getApi().getCategory();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                CategoryModel categoryModel = response.body();
                if (categoryModel != null){
                    if (categoryModel.getStatus()){
                        //Toast_msg(categoryModel.getData().size()+"---");
                        arrayList.clear();

                        for (int i = 0; i<categoryModel.getData().size();i++){
                            arrayList.add(categoryModel.getData().get(i).getCategoryName());
                            categoryHashMap.put(categoryModel.getData().get(i).getCategoryName(),categoryModel.getData().get(i).getId());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCustomerCategoryActivity.this,android.R.layout.simple_dropdown_item_1line,arrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        category.setAdapter(adapter);

                    }else {
                        Toast_msg("There is No data To Show");
                    }
                }else {
                    Toast_msg("Server resturned null response");
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.e("errorMsg",t.getMessage());
                Toast_msg(t.getMessage()+"");
            }
        });
    }


    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }



    private void get_services(String category_id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Please wait");
        progressDialog.show();
        Call<ServiceProviderModel> call = RetrofitClient.getmInstance().getApi().getCatServiceProvider(category_id);

        call.enqueue(new Callback<ServiceProviderModel>() {
            @Override
            public void onResponse(Call<ServiceProviderModel> call, Response<ServiceProviderModel> response) {
                progressDialog.dismiss();


                ServiceProviderModel services = response.body();

                if (services.getStatus()) {
                    mOrder.setEnabled(true);

                    for (int i = 0;i<services.getData().size();i++){
                        items.clear();
                        items.add(services.getData().get(i).getUserId());
                        stringDatumHashMap.clear();
                        stringDatumHashMap.put(services.getData().get(i).getUserId(),services.getData().get(i).getId());
                    }



                    listItems = items.toArray(new String[0]);
                    checkedItems = new boolean[listItems.length];

                    mOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddCustomerCategoryActivity.this);
                            mBuilder.setTitle(R.string.dialog_title);
                            mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                                    if (isChecked) {
                                        mUserItems.add(position);
                                    } else {
                                        mUserItems.remove((Integer.valueOf(position)));
                                    }
                                }
                            });

                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String item = "";
                                    for (int i = 0; i < mUserItems.size(); i++) {
                                        item = item + stringDatumHashMap.get(listItems[mUserItems.get(i)]);
                                        if (i != mUserItems.size() - 1) {
                                            item = item + ",";
                                        }
                                    }
                                    mItemSelected.setText(item);

                                    services_txt = item;
                                }
                            });

                            mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    for (int i = 0; i < checkedItems.length; i++) {
                                        checkedItems[i] = false;
                                        mUserItems.clear();
                                        mItemSelected.setText("");
                                    }
                                }
                            });

                            AlertDialog mDialog = mBuilder.create();
                            mDialog.show();
                        }
                    });


                }else {
                    items.clear();
                    stringDatumHashMap.clear();
                    mUserItems.clear();
                    Toast_msg("No services for this category");
                    mOrder.setEnabled(false);
                }

            }

            @Override
            public void onFailure(Call<ServiceProviderModel> call, Throwable t) {
                progressDialog.dismiss();

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


}

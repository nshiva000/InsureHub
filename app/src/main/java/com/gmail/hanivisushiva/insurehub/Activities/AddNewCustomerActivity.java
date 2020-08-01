package com.gmail.hanivisushiva.insurehub.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.User.AllCustomerServicesActivity;
import com.gmail.hanivisushiva.insurehub.FileUtils;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.ServiceProviderModel;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewCustomerActivity extends AppCompatActivity {

    private static final String TAG = AddNewServiceProviderActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;

    private Uri imageUri;

    EditText first_name,last_name,mobile,email,city,state,pin_code,pan,username,password;
    Button dob,file,submit,add_new_category,all_new_category;
    Spinner gender;
    private int mYear, mMonth, mDay;

    TextView date_of_birth_txt,expire_date_txt;
    ImageView imageView;

    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ArrayList<String> genderStringArrayList = new ArrayList<>();

    String first_name_txt,last_name_txt,mobile_txt,email_txt,city_txt,state_txt,pin_code_txt,pan_txt,username_txt,
            password_txt,dob_txt,gender_txt,edit_id,upload_file;

    RequestBody first_name_requestBody,last_name_requestBody,mobile_requestBody,email_requestBody,city_requestBody,
            state_requestBody,pin_code_requestBody,pan_requestBody,username_requestBody,
            password_requestBody,
            dob_requestBody,gender_requestBody,edit_requestBody;

    private MultipartBody.Part multi_part_file;
    Intent intent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);


        add_new_category = findViewById(R.id.add_category_btn);
        all_new_category = findViewById(R.id.all_category_btn);
        gender = findViewById(R.id.gender);

        genderStringArrayList.add("Male");
        genderStringArrayList.add("Female");
        genderStringArrayList.add("Others");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(AddNewCustomerActivity.this,android.R.layout.simple_dropdown_item_1line,genderStringArrayList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);


        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);

        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pin_code = findViewById(R.id.pincode);
        pan = findViewById(R.id.pan);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);



        dob = findViewById(R.id.date_of_birth);

        file = findViewById(R.id.image);

        submit = findViewById(R.id.submit);

        date_of_birth_txt = findViewById(R.id.date_of_birth_text_view);

        imageView = findViewById(R.id.imageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        intent = getIntent();
        edit_id = intent.getStringExtra("item_id");





        first_name_txt = intent.getStringExtra("firstname");
        last_name_txt = intent.getStringExtra("lastname");
        mobile_txt = intent.getStringExtra("mobile");
        email_txt = intent.getStringExtra("email");
        city_txt = intent.getStringExtra("city");
        state_txt = intent.getStringExtra("state");
        pin_code_txt = intent.getStringExtra("pincode");
        pan_txt = intent.getStringExtra("pan");
        username_txt = intent.getStringExtra("username");
        password_txt = intent.getStringExtra("password");
        dob_txt = intent.getStringExtra("dob");
        gender_txt = intent.getStringExtra("gender");
        upload_file = intent.getStringExtra("file");


        if (first_name_txt != null){
            first_name.setText(first_name_txt);
        }
        if (last_name_txt != null){
            last_name.setText(last_name_txt);
        }
        if (mobile_txt != null){
            mobile.setText(mobile_txt);
        }
        if (email_txt != null){
            email.setText(email_txt);
        }
        if (city_txt != null){
            city.setText(city_txt);
        }

        if (state_txt != null){
            state.setText(state_txt);
        }
        if (pin_code_txt != null){
            pin_code.setText(pin_code_txt);
        }
        if (pan_txt != null){
            pan.setText(pan_txt);
        }
        if (username_txt != null){
            username.setText(username_txt);
        }
        if (password_txt != null){
            password.setText(password_txt);
        }




        if (dob_txt != null){
            date_of_birth_txt.setText(dob_txt);
        }


        if (gender_txt != null){

            if (gender_txt.toLowerCase().equals("male")){
                gender.setSelection(0);
            }else if (gender_txt.toLowerCase().equals("female")){
                gender.setSelection(1);
            }else {
                gender.setSelection(2);
            }
            //gender.setText(gender_txt);
            //int position = Integer.parseInt(gender_txt);
           // gender.setSelection(position);
            //gender.setSelection(2);
        }

        if (upload_file != null){
            //Toast_msg(upload_file);
            Picasso.get().load(upload_file).into(imageView);
        }else {
            Toast_msg("upload file empty");
        }











        if(intent.getStringExtra("edit_status") != null){
            if (intent.getStringExtra("edit_status").equals("edit")){

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle("Edit Customer");
                }

            }else {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle("Add New Customer");
                }
                add_new_category.setVisibility(View.GONE);
            }
        }else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Add New Customer");
            }

            add_new_category.setVisibility(View.GONE);
        }











        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                first_name_txt = first_name.getText().toString();
                last_name_txt = last_name.getText().toString();
                mobile_txt = mobile.getText().toString();
                email_txt = email.getText().toString();
                city_txt = city.getText().toString();
                state_txt = state.getText().toString();
                pin_code_txt = pin_code.getText().toString();
                pan_txt = pan.getText().toString();
                username_txt = username.getText().toString();
                password_txt = password.getText().toString();
                dob_txt = date_of_birth_txt.getText().toString();


                gender_txt = gender.getSelectedItem().toString();



                if(intent.getStringExtra("edit_status") != null){
                    if (intent.getStringExtra("edit_status").equals("edit")){

                        editDataToServer();

                    }else {
                        senddataToServer();
                        add_new_category.setVisibility(View.GONE);
                    }
                }else {
                    senddataToServer();
                    add_new_category.setVisibility(View.GONE);
                }


            }
        });






        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateOfBirthDatePicker();
            }
        });

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (askForPermission()){
                    showChooser();
                }
            }
        });


        add_new_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewCustomerActivity.this,AddCustomerCategoryActivity.class);
                intent.putExtra("id",edit_id);
                startActivity(intent);
            }
        });

        all_new_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewCustomerActivity.this, AllCustomerServicesActivity.class);
                intent.putExtra("id",edit_id);
                //Toast_msg("id"+edit_id);
                startActivity(intent);
            }
        });





    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void senddataToServer(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        first_name_requestBody = createPartFromString(first_name_txt);
        last_name_requestBody = createPartFromString(last_name_txt);
        mobile_requestBody = createPartFromString(mobile_txt);
        email_requestBody = createPartFromString(email_txt);
        city_requestBody = createPartFromString(city_txt);

        state_requestBody = createPartFromString(state_txt);
        pin_code_requestBody = createPartFromString(pin_code_txt);
        pan_requestBody = createPartFromString(pan_txt);
        username_requestBody = createPartFromString(username_txt);

        password_requestBody = createPartFromString(password_txt);
        dob_requestBody = createPartFromString(dob_txt);

        gender_requestBody = createPartFromString(gender_txt);

        multi_part_file = prepareFilePart("image",imageUri);




        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().addNewCustomer(first_name_requestBody,last_name_requestBody,
                gender_requestBody,dob_requestBody,mobile_requestBody,email_requestBody,city_requestBody,state_requestBody,pin_code_requestBody,pan_requestBody,username_requestBody
                ,password_requestBody,multi_part_file);
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





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void editDataToServer(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        first_name_requestBody = createPartFromString(first_name_txt);
        last_name_requestBody = createPartFromString(last_name_txt);
        mobile_requestBody = createPartFromString(mobile_txt);
        email_requestBody = createPartFromString(email_txt);
        city_requestBody = createPartFromString(city_txt);

        state_requestBody = createPartFromString(state_txt);
        pin_code_requestBody = createPartFromString(pin_code_txt);
        pan_requestBody = createPartFromString(pan_txt);
        username_requestBody = createPartFromString(username_txt);

        password_requestBody = createPartFromString(password_txt);
        dob_requestBody = createPartFromString(dob_txt);
        gender_requestBody = createPartFromString(gender_txt);
        edit_requestBody = createPartFromString(edit_id.toString());

        if (imageUri != null){
            multi_part_file = prepareFilePart("image",imageUri);
        }else {
            Toast_msg("image uri is null");
        }

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().editNewCustomer(first_name_requestBody,last_name_requestBody,
                gender_requestBody,dob_requestBody,mobile_requestBody,email_requestBody,city_requestBody,state_requestBody,pin_code_requestBody,pan_requestBody,username_requestBody
                ,password_requestBody,edit_requestBody,multi_part_file);
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



    private void expireDatePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        expire_date_txt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void dateOfBirthDatePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_of_birth_txt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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





    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (data != null) {
                    if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        imageUri = uri;

                        Picasso.get().load(imageUri).into(imageView);
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    /**
     *  Runtime Permission
     */
    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(AddNewCustomerActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddNewCustomerActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(AddNewCustomerActivity.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(AddNewCustomerActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

                return false;
            } else {
                // permission granted and calling function working
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    showChooser();
                } else {
                    // Permission Denied
                    Toast.makeText(AddNewCustomerActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewCustomerActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(AddNewCustomerActivity.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(AddNewCustomerActivity.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }






    private void ShowSuccessAlert(){

        new android.app.AlertDialog.Builder(this)
                .setMessage("Uploaded Successfully")
                .setCancelable(true)
                .setPositiveButton("Ok", null)
                .show();

    }



    private void ShowErrorAlert(){

        new android.app.AlertDialog.Builder(this)
                .setMessage("Failed to Upload")
                .setCancelable(true)
                .setPositiveButton("Ok", null)
                .show();

    }


    private void ShowAlert(String msg){

        new android.app.AlertDialog.Builder(this)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("Ok", null)
                .show();

    }
}

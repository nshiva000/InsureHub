package com.gmail.hanivisushiva.insurehub.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.FileUtils;
import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.CategoryModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.CustomerModel;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.ServiceProviderModel;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewServiceProviderActivity extends AppCompatActivity {


    private static final String TAG = AddNewServiceProviderActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;

    private Uri imageUri;
    ImageView imageView;
    Intent intent;



    EditText name,address,created_by,mail,website,contact,description,user_name,password;
    Spinner category;
    Button file,submit;
    String name_txt,address_txt,mail_txt,website_txt,contact_txt,description_txt,user_name_txt,password_txt,category_txt,edit_id,upload_file;
    RequestBody name_requestBody,address_requestBody,mail_requestBody,website_requestBody,contact_requestBody,description_requestBody,user_name_requestBody,password_requestBody,category_requestBody,edit_id_requestBody;
    private MultipartBody.Part multi_part_file;

    private ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service_provider);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);





        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mail = findViewById(R.id.mail);

        website = findViewById(R.id.website);
        contact = findViewById(R.id.contact);
        description = findViewById(R.id.description);
        user_name = findViewById(R.id.username);
        password = findViewById(R.id.password);

        category = findViewById(R.id.category);
        file = findViewById(R.id.image);
        submit = findViewById(R.id.submit);

        imageView = findViewById(R.id.imageView);


        intent = getIntent();
        get_category();

        edit_id = intent.getStringExtra("item_id");

        if(intent.getStringExtra("edit_status") != null){
            if (intent.getStringExtra("edit_status").equals("edit")){
                // add back arrow to toolbar
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle("Edit Service Provider");
                }

                name_txt = intent.getStringExtra("name");
                address_txt = intent.getStringExtra("address");
                mail_txt = intent.getStringExtra("mail_id");
                website_txt = intent.getStringExtra("website");
                contact_txt = intent.getStringExtra("contact_person");
                description_txt = intent.getStringExtra("description");
                user_name_txt = intent.getStringExtra("user_id");
                password_txt = intent.getStringExtra("password");
                category_txt = intent.getStringExtra("category");
                upload_file = intent.getStringExtra("upload_file");

                if (name_txt != null){
                    name.setText(name_txt);
                }

                if (address_txt != null){
                    address.setText(address_txt);
                }

                if (mail_txt != null){
                    mail.setText(mail_txt);
                }
                if (website_txt != null){
                    website.setText(website_txt);
                }
                if (contact_txt != null){
                    contact.setText(contact_txt);
                }
                if (description_txt != null){
                    description.setText(description_txt);
                }
                if (user_name_txt != null){
                    user_name.setText(user_name_txt);
                }
                if (password_txt != null){
                    password.setText(password_txt);
                }
                if (category_txt != null){
                   // category.setText(category_txt);
                    int position = Integer.parseInt(category_txt);
                    category.setSelection(position);
                }

                if (upload_file != null){
                    //Toast_msg(upload_file);
                    Picasso.get().load(upload_file).into(imageView);
                }else {
                    Toast_msg("upload file empty");
                }







            }else {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle("Add New Service Provider");
                }
            }
        }else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Add New Service Provider");
            }
        }





        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (askForPermission()){
                    showChooser();
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                name_txt = name.getText().toString();
                address_txt = address.getText().toString();
                mail_txt = mail.getText().toString();
                website_txt = website.getText().toString();
                contact_txt = contact.getText().toString();
                description_txt = description.getText().toString();
                user_name_txt = user_name.getText().toString();
                password_txt = password.getText().toString();
                category_txt = category.getSelectedItemPosition()+"";
                Toast_msg(category_txt+"---");

                if (intent.getStringExtra("edit_status") != null){
                    if (intent.getStringExtra("edit_status").equals("edit")){

                        if (imageUri != null){
                            editDataToServer();
                        }else {
                            editDataToServerWithOutImage();
                        }

                    }else{
                        sendDataToServer();
                    }
                }else {
                    sendDataToServer();
                }




            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendDataToServer(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        name_requestBody = createPartFromString(name_txt);
        address_requestBody = createPartFromString(address_txt);
        mail_requestBody = createPartFromString(mail_txt);
        website_requestBody = createPartFromString(website_txt);
        contact_requestBody = createPartFromString(contact_txt);
        description_requestBody = createPartFromString(description_txt);
        user_name_requestBody = createPartFromString(user_name_txt);
        password_requestBody = createPartFromString(password_txt);
        category_requestBody = createPartFromString(category_txt);

        multi_part_file = prepareFilePart("image",imageUri);


        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().addServiceProvider(name_requestBody,address_requestBody,category_requestBody,mail_requestBody,website_requestBody,contact_requestBody,description_requestBody,user_name_requestBody,password_requestBody,multi_part_file);
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

        name_requestBody = createPartFromString(name_txt);
        address_requestBody = createPartFromString(address_txt);
        mail_requestBody = createPartFromString(mail_txt);
        website_requestBody = createPartFromString(website_txt);
        contact_requestBody = createPartFromString(contact_txt);
        description_requestBody = createPartFromString(description_txt);
        user_name_requestBody = createPartFromString(user_name_txt);
        password_requestBody = createPartFromString(password_txt);
        category_requestBody = createPartFromString(category_txt);
        edit_id_requestBody = createPartFromString(edit_id);

        if (imageUri != null){
            multi_part_file = prepareFilePart("image",imageUri);
        }




        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().editServiceProvider(name_requestBody,address_requestBody,category_requestBody,mail_requestBody,website_requestBody,contact_requestBody,description_requestBody,user_name_requestBody,password_requestBody,edit_id_requestBody,multi_part_file);
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
    private void editDataToServerWithOutImage(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please waitout");
        progressDialog.show();

        name_requestBody = createPartFromString(name_txt);
        address_requestBody = createPartFromString(address_txt);
        mail_requestBody = createPartFromString(mail_txt);
        website_requestBody = createPartFromString(website_txt);
        contact_requestBody = createPartFromString(contact_txt);
        description_requestBody = createPartFromString(description_txt);
        user_name_requestBody = createPartFromString(user_name_txt);
        password_requestBody = createPartFromString(password_txt);
        category_requestBody = createPartFromString(category_txt);
        edit_id_requestBody = createPartFromString(edit_id);





        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().editServiceProviderWithoutImage(name_requestBody,address_requestBody,category_requestBody,mail_requestBody,website_requestBody,contact_requestBody,description_requestBody,user_name_requestBody,password_requestBody,edit_id_requestBody);
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewServiceProviderActivity.this,android.R.layout.simple_dropdown_item_1line,stringArrayList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        category.setAdapter(adapter);


                        if(intent.getStringExtra("edit_status") != null){
                            if (intent.getStringExtra("edit_status").equals("edit")){
                                if (category_txt != null){
                                    // category.setText(category_txt);
                                    int position = Integer.parseInt(category_txt);
                                    category.setSelection(position);
                                }
                            }
                        }

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



    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
            int hasCallPermission = ContextCompat.checkSelfPermission(AddNewServiceProviderActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddNewServiceProviderActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(AddNewServiceProviderActivity.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(AddNewServiceProviderActivity.this,
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
                    Toast.makeText(AddNewServiceProviderActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewServiceProviderActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(AddNewServiceProviderActivity.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(AddNewServiceProviderActivity.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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

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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Adapter.AdminoffersAdapter;
import com.gmail.hanivisushiva.insurehub.FileUtils;
import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.AdminOffersModel;
import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.Datum;
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

public class AdminAddOffersActivity extends AppCompatActivity {

    Button btnChoose,btnUpload;
    ImageView imageView;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Datum> arrayList = new ArrayList();


    private static final String TAG = AdminAddOffersActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;

    private Uri imageUri;
    private MultipartBody.Part multi_part_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_offers);

        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imageView);


        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AdminoffersAdapter(AdminAddOffersActivity.this,arrayList);
        recyclerView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Offers");
        }


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (askForPermission()){
                    showChooser();
                }
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                UploadFileToServer();
            }
        });


        getOffers();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UploadFileToServer(){

        if (imageUri != null){
            multi_part_file = prepareFilePart("image",imageUri);
        }else {
            ShowAlert("Choose files to Upload");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait Loading ..");
        progressDialog.show();


        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().admin_add_offers(multi_part_file);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();
                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        ToastMsg("Added successfully");
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


    private void getOffers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();


        Call<AdminOffersModel> call = RetrofitClient.getmInstance().getApi().getOffers();
        call.enqueue(new Callback<AdminOffersModel>() {
            @Override
            public void onResponse(Call<AdminOffersModel> call, Response<AdminOffersModel> response) {
                progressDialog.dismiss();
                AdminOffersModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        arrayList.clear();
                        arrayList.addAll(updateModel.getData());
                        adapter.notifyDataSetChanged();
                    }else {
                        ToastMsg("No Offers to show");
                    }
                }else{
                    ToastMsg("Null response from the server");
                }

            }

            @Override
            public void onFailure(Call<AdminOffersModel> call, Throwable t) {
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
            int hasCallPermission = ContextCompat.checkSelfPermission(AdminAddOffersActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(AdminAddOffersActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(AdminAddOffersActivity.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(AdminAddOffersActivity.this,
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
                    Toast.makeText(AdminAddOffersActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddOffersActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(AdminAddOffersActivity.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(AdminAddOffersActivity.this, android.R.color.holo_red_light));
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



    public void deleteBanner(String id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.show();

        Call<UpdateModel> call = RetrofitClient.getmInstance().getApi().deleteOffers(id);

        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                progressDialog.dismiss();

                UpdateModel updateModel = response.body();

                if (updateModel != null){
                    if (updateModel.getStatus()){
                        getOffers();
                        ShowAlert("Offer Deleted Successfully");

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

}

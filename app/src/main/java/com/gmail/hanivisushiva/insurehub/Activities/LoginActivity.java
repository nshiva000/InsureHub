package com.gmail.hanivisushiva.insurehub.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Model.LoginModel.LoginModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;
    Button login_btn;
    String Str_username, Str_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_username = userName.getText().toString().trim();
                Str_password = password.getText().toString().trim();


                if (TextUtils.isEmpty(Str_username)) {
                    userName.setError("Please enter username");
                    userName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Str_password)) {
                    password.setError("Please enter Password");
                    password.requestFocus();
                    return;
                }

                LoginUser(Str_username,Str_password);

                Log.e("username", Str_password + Str_username);

            }
        });
    }







    private void LoginUser(String userName,String password){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait, Loading ...");
        progressDialog.show();

        Call<LoginModel> call = RetrofitClient.getmInstance().getApi().user_login(userName,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                LoginModel loginModel = response.body();
                if (loginModel != null){
                    if (loginModel.getStatus()){
                        ToastMsg(loginModel.getMessage());
                        SharedPrefManager.get_mInstance(LoginActivity.this).saveUser(loginModel);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        ToastMsg(loginModel.getMessage());
                    }
                }else {
                    ToastMsg("Server Returned Null");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("errorMsg",t.getMessage());
                ToastMsg(t.getMessage()+"");
            }
        });
    }


    private void ToastMsg(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}

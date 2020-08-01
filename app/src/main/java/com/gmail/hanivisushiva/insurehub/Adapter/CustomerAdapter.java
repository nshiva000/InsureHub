package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.AddNewCustomerActivity;
import com.gmail.hanivisushiva.insurehub.Activities.CustomerActivity;
import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public CustomerAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_item,viewGroup,false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder customerViewHolder, int i) {

        final Datum datum = datumList.get(i);


        if (datum.getFirstName() != null){
            customerViewHolder.category_name.setText(datum.getFirstName());

        }

        if (datum.getEmail() != null){
            customerViewHolder.email.setText(datum.getEmail());

        }

        if (datum.getMobileNo() != null){
            customerViewHolder.phone_no.setText(datum.getMobileNo());

        }
        if (datum.getCardNo() != null){
            customerViewHolder.card_no.setText(datum.getCardNo());

        }

        customerViewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((CustomerActivity)context).DeleteCategory(datum.getId());
            }
        });



        if (!SharedPrefManager.get_mInstance(context).getRole().toLowerCase().equals("admin")){
            customerViewHolder.delete_btn.setVisibility(View.GONE);
            customerViewHolder.edit_btn.setVisibility(View.GONE);
        }

        customerViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddNewCustomerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("edit_status","edit");


                intent.putExtra("firstname",datum.getFirstName());
                intent.putExtra("lastname",datum.getLastName());
                intent.putExtra("gender",datum.getGender());
                intent.putExtra("dob",datum.getDateOfBirth());
                intent.putExtra("mobile",datum.getMobileNo());


                intent.putExtra("email",datum.getEmail());
                intent.putExtra("city",datum.getCity());
                intent.putExtra("state",datum.getState());
                intent.putExtra("pincode",datum.getPincode());
                intent.putExtra("pan",datum.getPanCard());

                intent.putExtra("username",datum.getUserId());
                intent.putExtra("password",datum.getPassword());
                intent.putExtra("createdby",datum.getCreatedBy());
                intent.putExtra("card",datum.getCardNo());

                intent.putExtra("expiredate",datum.getExpiryDate());
                intent.putExtra("policyno",datum.getPolicyNo());
                intent.putExtra("file",datum.getUploadFile());


                intent.putExtra("item_id",datum.getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class CustomerViewHolder extends RecyclerView.ViewHolder{
        TextView category_name,email,phone_no,card_no;
        ImageButton edit_btn,delete_btn;


        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

                edit_btn = itemView.findViewById(R.id.edit_btn);
                delete_btn = itemView.findViewById(R.id.delete_btn);
                category_name = itemView.findViewById(R.id.category_name);
            email = itemView.findViewById(R.id.email);
            phone_no = itemView.findViewById(R.id.phone_no);
            card_no = itemView.findViewById(R.id.card_no);
        }
    }





    private void ToastMsg(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}

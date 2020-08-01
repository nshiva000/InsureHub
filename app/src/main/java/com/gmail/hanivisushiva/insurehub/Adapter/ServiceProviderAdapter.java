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
import com.gmail.hanivisushiva.insurehub.Activities.AddNewServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Activities.ServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ServiceProviderViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public ServiceProviderAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public ServiceProviderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_provider_item,viewGroup,false);
        return new ServiceProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderViewHolder serviceProviderViewHolder, int i) {
        final Datum datum = datumList.get(i);
        serviceProviderViewHolder.category_name.setText(datum.getUserId());

        serviceProviderViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNewServiceProviderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("edit_status","edit");
                intent.putExtra("item_id",datum.getId());

                intent.putExtra("name",datum.getName());
                intent.putExtra("address",datum.getAddress());
                intent.putExtra("category",datum.getCategory());
                intent.putExtra("created_by",datum.getCreatedBy());
                intent.putExtra("mail_id",datum.getMailId());
                intent.putExtra("upload_file",datum.getUploadFile());
                intent.putExtra("website",datum.getWebsite());
                intent.putExtra("contact_person",datum.getContactPerson());
                intent.putExtra("description",datum.getDescription());
                intent.putExtra("user_id",datum.getUserId());
                intent.putExtra("password",datum.getPassword());

                context.startActivity(intent);
            }
        });


        serviceProviderViewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DeleteCategory(datum.getId());
                ((ServiceProviderActivity)context).DeleteCategory(datum.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class ServiceProviderViewHolder extends RecyclerView.ViewHolder{

        TextView category_name;
        ImageButton edit_btn,delete_btn;

        public ServiceProviderViewHolder(@NonNull View itemView) {
            super(itemView);

            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }





    private void ToastMsg(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}

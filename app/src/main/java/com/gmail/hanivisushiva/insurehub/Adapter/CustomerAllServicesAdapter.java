package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.CustomerAllServicesModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;

import java.util.List;

public class CustomerAllServicesAdapter extends RecyclerView.Adapter<CustomerAllServicesAdapter.CustomerAllServicesViewHolder> {



    private Context context;
    private List<Datum> datumList;

    public CustomerAllServicesAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerAllServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_all_services_item,viewGroup,false);
        return new CustomerAllServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAllServicesViewHolder customerAllServicesViewHolder, int i) {
        Datum datum = datumList.get(i);
        customerAllServicesViewHolder.name.setText(datum.getName());
        customerAllServicesViewHolder.website_url.setText(datum.getWebsite());
        customerAllServicesViewHolder.category.setText(datum.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class CustomerAllServicesViewHolder extends RecyclerView.ViewHolder{

        TextView name,category,website_url;

    public CustomerAllServicesViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        category = itemView.findViewById(R.id.category_name);
        website_url = itemView.findViewById(R.id.website_url);


    }
}


}

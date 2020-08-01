package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.CustomerProducts.Datum;
import com.gmail.hanivisushiva.insurehub.R;

import java.util.List;

public class CustomerProductsAdapter extends RecyclerView.Adapter<CustomerProductsAdapter.CustomerProductsViewHolder> {


    private Context context;
    private List<Datum> datumList;

    public CustomerProductsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_service_partner_item,viewGroup,false);
        return new CustomerProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerProductsViewHolder customerProductsViewHolder, int i) {
        Datum datum = datumList.get(i);

        customerProductsViewHolder.title.setText(datum.getName());
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class CustomerProductsViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public CustomerProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}

package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.CustomerServicePartners.Datum;
import com.gmail.hanivisushiva.insurehub.R;

import java.util.List;

public class CustomerServicePartnersAdapter extends RecyclerView.Adapter<CustomerServicePartnersAdapter.CustomerServicePartnerViewHolder> {


    private Context context;
    private List<Datum> datumList;

    public CustomerServicePartnersAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerServicePartnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_service_partner_item,viewGroup,false);
        return new CustomerServicePartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerServicePartnerViewHolder customerServicePartnerViewHolder, int i) {
       Datum datum = datumList.get(i);
       customerServicePartnerViewHolder.title.setText(datum.getSname());

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class CustomerServicePartnerViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public CustomerServicePartnerViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}

package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerOffersAdapter extends RecyclerView.Adapter<CustomerOffersAdapter.CustomerOffersViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public CustomerOffersAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerOffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_offers_item,viewGroup,false);
        return new CustomerOffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOffersViewHolder customerOffersViewHolder, int i) {

        Datum datum = datumList.get(i);

        if (datum.getOffer() != null){
            Picasso.get().load(datum.getOffer()).into(customerOffersViewHolder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class CustomerOffersViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public CustomerOffersViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

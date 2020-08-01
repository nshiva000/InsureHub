package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.CustomerAllRequestsModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;

import java.util.List;

public class CustomerAllRequestsAdapter extends RecyclerView.Adapter<CustomerAllRequestsAdapter.CustomerAllRequestViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public CustomerAllRequestsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerAllRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_all_request_item,viewGroup,false);
        return new CustomerAllRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAllRequestViewHolder customerAllRequestViewHolder, int i) {
        Datum datum = datumList.get(i);

        customerAllRequestViewHolder.request.setText(datum.getRequest());

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class CustomerAllRequestViewHolder extends RecyclerView.ViewHolder{

        TextView request;

        public CustomerAllRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            request = itemView.findViewById(R.id.request);
        }
    }
}

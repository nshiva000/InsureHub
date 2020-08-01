package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.CustomerPolicy.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerPolicyAdapter extends RecyclerView.Adapter<CustomerPolicyAdapter.CustomerPolicyViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public CustomerPolicyAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerPolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_policy_item,viewGroup,false);
        return new CustomerPolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPolicyViewHolder customerPolicyViewHolder, int i) {

        Datum datum = datumList.get(i);

        if (datum.getDocumentspath() != null){
            Picasso.get().load(datum.getDocumentspath()).into(customerPolicyViewHolder.imageView);
        }

        if (datum.getDescription() != null){
            customerPolicyViewHolder.description.setText(datum.getDescription());
        }



    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class CustomerPolicyViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        ImageView imageView;

        public CustomerPolicyViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

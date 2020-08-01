package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.AdminCustomerRequestsModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;

import java.util.List;

public class CustomerRequestPolicyAdapter extends RecyclerView.Adapter<CustomerRequestPolicyAdapter.CustomerPolicyViewholder> {


    private Context context;
    private List<Datum> data;

    public CustomerRequestPolicyAdapter(Context context, List<Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CustomerPolicyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_customer_policy_request_item,viewGroup,false);
        return new CustomerPolicyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPolicyViewholder customerPolicyViewholder, int i) {

        Datum datum = data.get(i);

        if (datum.getName() != null){
           customerPolicyViewholder.name.setText(datum.getName());
        }

        if (datum.getRequest() != null){
            customerPolicyViewholder.request.setText(datum.getRequest());
        }

        if (datum.getPhone() != null){
            customerPolicyViewholder.phone.setText(datum.getPhone());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomerPolicyViewholder extends RecyclerView.ViewHolder{

        TextView name,request,phone;

        public CustomerPolicyViewholder(@NonNull View itemView) {
            super(itemView);

            request = itemView.findViewById(R.id.request);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
        }
    }
}

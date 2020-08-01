package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider.AddBillsServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderAllBillsModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;

import java.util.List;

public class ServiceProviderAllBillsAdapter extends RecyclerView.Adapter<ServiceProviderAllBillsAdapter.AllBillsViewHolder> {

    private Context context;
    private List<Datum> list;

    public ServiceProviderAllBillsAdapter(Context context, List<Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllBillsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_provider_all_bills_item,viewGroup,false);
        return new AllBillsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBillsViewHolder allBillsViewHolder, int i) {
        final Datum datum = list.get(i);

        allBillsViewHolder.title.setText(datum.getCardNo());
        allBillsViewHolder.total.setText(datum.getTotalPaid());
        allBillsViewHolder.amount.setText(datum.getBillAmt());
        allBillsViewHolder.discount.setText(datum.getDiscountAmt());

        if (SharedPrefManager.get_mInstance(context).getRole().toLowerCase().equals("admin")){
            allBillsViewHolder.edit_btn.setVisibility(View.GONE);
        }else {
            allBillsViewHolder.edit_btn.setVisibility(View.VISIBLE);
        }

        allBillsViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddBillsServiceProviderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","edit");
                intent.putExtra("cardno",datum.getCardNo());
                intent.putExtra("amount",datum.getBillAmt());
                intent.putExtra("discount",datum.getDiscountAmt());
                intent.putExtra("total",datum.getTotalPaid());
                intent.putExtra("id",datum.getBid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class AllBillsViewHolder extends RecyclerView.ViewHolder{

        TextView title,total,discount,amount;
        ImageButton edit_btn;

        public AllBillsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            total = itemView.findViewById(R.id.total);
            discount = itemView.findViewById(R.id.discount);
            amount = itemView.findViewById(R.id.amount);
            edit_btn = itemView.findViewById(R.id.edit_btn);
        }
    }

    private void Toast_msg(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}

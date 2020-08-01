package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Model.CustomerMyRewards.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerMyRewardsAdapter extends RecyclerView.Adapter<CustomerMyRewardsAdapter.CustomerMyRewardsViewHolder> {

    private Context context;
    private List<Datum> datumList;

    public CustomerMyRewardsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public CustomerMyRewardsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_rewards_activity_item,viewGroup,false);
        return new CustomerMyRewardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerMyRewardsViewHolder customerMyRewardsViewHolder, int i) {

        Datum datum = datumList.get(i);

        if (datum.getReward() != null){
            Picasso.get().load(datum.getReward()).into(customerMyRewardsViewHolder.imageView);
        }

        if (datum.getDescription() != null){
            customerMyRewardsViewHolder.textView.setText(datum.getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class CustomerMyRewardsViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

       public CustomerMyRewardsViewHolder(@NonNull View itemView) {
           super(itemView);

           textView = itemView.findViewById(R.id.description);
           imageView = itemView.findViewById(R.id.imageView);
       }
   }
}

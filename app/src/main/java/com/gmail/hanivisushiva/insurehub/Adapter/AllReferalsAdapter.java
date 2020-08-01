package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.Activities.ReferedByAdminActivity;
import com.gmail.hanivisushiva.insurehub.Model.AllReferals.Datum;
import com.gmail.hanivisushiva.insurehub.R;

import java.util.List;

public class AllReferalsAdapter extends RecyclerView.Adapter<AllReferalsAdapter.AllReferalsViewHolder> {


    private Context context;
    private List<Datum> datumList;

    public AllReferalsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public AllReferalsAdapter.AllReferalsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_referals_item,viewGroup,false);
        return new AllReferalsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllReferalsAdapter.AllReferalsViewHolder allReferalsViewHolder, int i) {

        final Datum datum = datumList.get(i);

        if (datum.getName() != null){
            allReferalsViewHolder.name.setText(datum.getName());
        }

        if (datum.getPhone() != null){
            allReferalsViewHolder.phone.setText(datum.getPhone());
        }

        if (datum.getEmail() != null){
            allReferalsViewHolder.email.setText(datum.getEmail());
        }

        if (datum.getDescription() != null){
            allReferalsViewHolder.description.setText(datum.getDescription());
        }

        if (datum.getRequirement() != null){
            allReferalsViewHolder.requirement.setText(datum.getRequirement());
        }



        allReferalsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dataSise",datum.getUid());
                Intent intent = new Intent(context, ReferedByAdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sid",datum.getUid());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class AllReferalsViewHolder extends RecyclerView.ViewHolder{

        TextView name,phone,email,requirement,description;
        CardView cardView;

        public AllReferalsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            requirement = itemView.findViewById(R.id.requirement);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}

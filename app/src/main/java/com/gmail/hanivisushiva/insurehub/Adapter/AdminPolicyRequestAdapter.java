package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.PolicyRequestListActivity;
import com.gmail.hanivisushiva.insurehub.Model.AdminPolicyRequestModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPolicyRequestAdapter extends RecyclerView.Adapter<AdminPolicyRequestAdapter.PolicyRequestViewHolder> {

    private Context context;
    private List<Datum> data;

    public AdminPolicyRequestAdapter(Context context, List<Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PolicyRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_policy_request_item,viewGroup,false);
        return new PolicyRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyRequestViewHolder policyRequestViewHolder, int i) {
        final Datum datum = data.get(i);

        if (datum.getCardNo() != null){
            policyRequestViewHolder.title.setText("Card No."+datum.getCardNo());
        }else {
            policyRequestViewHolder.title.setText("Card No.");
        }

        if (datum.getStatus() != null){
            policyRequestViewHolder.status.setText(datum.getStatus());
        }


        policyRequestViewHolder.approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((PolicyRequestListActivity)context).Approve(datum.getId());
            }
        });

        policyRequestViewHolder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((PolicyRequestListActivity)context).Reject(datum.getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PolicyRequestViewHolder extends RecyclerView.ViewHolder{

        TextView title,status;
        Button approve_btn,reject_btn;

        public PolicyRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            approve_btn = itemView.findViewById(R.id.approve_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);
            title = itemView.findViewById(R.id.title);
            status = itemView.findViewById(R.id.status);
        }
    }





}

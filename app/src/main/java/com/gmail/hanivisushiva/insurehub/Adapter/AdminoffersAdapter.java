package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.AdminAddOffersActivity;
import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.Datum;
import com.gmail.hanivisushiva.insurehub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminoffersAdapter extends RecyclerView.Adapter<AdminoffersAdapter.AdminoffersViewHolder> {


    private Context context;
    private List<Datum> datumList;

    public AdminoffersAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public AdminoffersAdapter.AdminoffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_image_item,viewGroup,false);
        return new AdminoffersAdapter.AdminoffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminoffersAdapter.AdminoffersViewHolder bannerViewHolder, int i) {

        final Datum datum = datumList.get(i);
        if(!datum.getOffer().equals("")){
            Picasso.get().load(datum.getOffer()).into(bannerViewHolder.imageView);
        }

        bannerViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteBanner(datum.getBaid());
                ((AdminAddOffersActivity)context).deleteBanner(datum.getOid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class AdminoffersViewHolder extends RecyclerView.ViewHolder{

        ImageButton delete;
        ImageView imageView;

        public AdminoffersViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.delete_btn);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }



    private void ToastMsg(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}

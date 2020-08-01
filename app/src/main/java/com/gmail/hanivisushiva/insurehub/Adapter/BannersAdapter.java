package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.BannerAdsActivity;
import com.gmail.hanivisushiva.insurehub.Model.BannerImagesModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannersAdapter extends RecyclerView.Adapter<BannersAdapter.BannerViewHolder> {


    private Context context;
    private List<Datum> datumList;

    public BannersAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_image_item,viewGroup,false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder bannerViewHolder, int i) {

        final Datum datum = datumList.get(i);
        if(!datum.getBanner().equals("")){
            Picasso.get().load(datum.getBanner()).into(bannerViewHolder.imageView);
        }

        bannerViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //deleteBanner(datum.getBaid());
                ((BannerAdsActivity)context).deleteBanner(datum.getBaid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    public class BannerViewHolder extends RecyclerView.ViewHolder{

        ImageButton delete;
        ImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.delete_btn);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }



    private void ToastMsg(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}

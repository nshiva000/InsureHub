package com.gmail.hanivisushiva.insurehub.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.hanivisushiva.insurehub.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context context;
    private int images[];
    private List<String> names;
    private HashMap<String,String> data;

    public HomeAdapter(Context context, int[] images, List<String> names,HashMap<String,String> data) {
        this.context = context;
        this.images = images;
        this.names = names;
        this.data = data;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item,viewGroup,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder homeViewHolder, int i) {

        if (names.get(i) != null){
            homeViewHolder.name.setText(names.get(i));
        }

        if (data.get(names.get(i)) != null){
            if (!data.get(names.get(i)).equals("")){
                homeViewHolder.number.setText(data.get(names.get(i)));
            }else {
                homeViewHolder.number.setText(data.get("0"));
            }
        }
        Picasso.get().load(images[i]).into(homeViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder{

        TextView name,number;
        ImageView imageView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}

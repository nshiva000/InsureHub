package com.gmail.hanivisushiva.insurehub.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.hanivisushiva.insurehub.Activities.CategoryActivity;
import com.gmail.hanivisushiva.insurehub.Activities.EditCategoryActivity;
import com.gmail.hanivisushiva.insurehub.Interface.DeleteCategoryInterface;
import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.Datum;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Datum> datumList;
    private DeleteCategoryInterface deleteCategoryInterface;

    public CategoryAdapter(Context context, List<Datum> datumList, DeleteCategoryInterface deleteCategoryInterface) {
        this.context = context;
        this.datumList = datumList;
        this.deleteCategoryInterface = deleteCategoryInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {

        final Datum datum = datumList.get(i);

        categoryViewHolder.category_name.setText(datum.getCategoryName());

        categoryViewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CategoryActivity)context).DeleteCategory(datum.getId());
            }
        });


        categoryViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditCategoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("category_id",datum.getId());
                intent.putExtra("category_name",datum.getCategoryName());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView category_name;
        ImageButton edit_btn,delete_btn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }






    private void ToastMsg(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}


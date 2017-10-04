package com.example.harmandeepsingh.jsonretro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.models.RecipeparticularDatum;

import java.util.List;

/**
 * Created by Harmandeep singh on 8/24/2017.
 */

public class CategoryRecipesCardAdapter extends RecyclerView.Adapter<CategoryRecipesCardAdapter.MyViewHolder> {
    private Context mContext;
    private List<RecipeparticularDatum> dishList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,detail;
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //detail = (TextView) view.findViewById(R.id.detail);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
    public CategoryRecipesCardAdapter(Context mContext, List<RecipeparticularDatum> dishList) {
        this.mContext = mContext;
        this.dishList = dishList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_card_linear, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecipeparticularDatum CategoryRecipesCardItems=dishList.get(position);
        holder.title.setText(CategoryRecipesCardItems.getPrecipeName());
       // holder.detail.setText(CategoryRecipesCardItems.getPrecipeDetail());
        //holder.category.setText(CategoryRecipesCardItems.getPrecipeImage());
        // loading album cover using Glide library
        String image=CategoryRecipesCardItems.getPrecipeImage();
        if(image.equals("")){
            Glide.with(mContext).load(R.drawable.images123).into(holder.thumbnail);
        }
        else{
            Glide.with(mContext).load(image).into(holder.thumbnail);
        }
      //  Glide.with(mContext).load(CategoryRecipesCardItems.getPrecipeImage()).into(holder.thumbnail);
    }



    @Override
    public int getItemCount() {
        return dishList.size();
    }
}

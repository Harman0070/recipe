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
import com.example.harmandeepsingh.jsonretro.models.Mydatum;

import java.util.List;

/**
 * Created by Harmandeep singh on 10/23/2017.
 */

public class MyRecipesCardAdapter extends RecyclerView.Adapter<MyRecipesCardAdapter.MyViewHolder>{
    private Context mContext;
    private List<Mydatum> dishList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
    public MyRecipesCardAdapter(Context mContext, List<Mydatum> dishList) {
        this.mContext = mContext;
        this.dishList = dishList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Mydatum Mydatum = dishList.get(position);
      //  Log.d("getTitle",""+NewestRecipeName.getPrecipeName()+"    "+NewestRecipeName.getPrecipeImage());
        holder.title.setText(Mydatum.getPrecipeName());
        // loading album cover using Glide library
        //Glide.with(mContext).load(NewestCardItems.getPrecipeImage()).into(holder.thumbnail);
        String imgThumbnail = Mydatum.getPrecipeImage();
        if (imgThumbnail.equals("")){
            Glide.with(mContext).load(R.drawable.images123).into(holder.thumbnail);

        }else {
            Glide.with(mContext).load(imgThumbnail).into(holder.thumbnail);
        }
    }
    @Override
    public int getItemCount() {
        return dishList.size();
    }
}

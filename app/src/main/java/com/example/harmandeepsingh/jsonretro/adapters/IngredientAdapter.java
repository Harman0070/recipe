package com.example.harmandeepsingh.jsonretro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.models.IngredientDatum;

import java.util.List;

/**
 * Created by Harmandeep singh on 8/30/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {
    private Context mContext;
    private List<IngredientDatum> dishListingre;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dishingre,dishnum,dishamt,dt;

        public MyViewHolder(View view) {
            super(view);

            dishingre = (TextView) view.findViewById(R.id.dishIngredient);
            dishnum=(TextView)view.findViewById(R.id.dishNumber);
            dishamt=(TextView)view.findViewById(R.id.dishAmount);
        }
    }
    public IngredientAdapter(Context mContext, List<IngredientDatum> dishList) {
        this.mContext = mContext;
        this.dishListingre = dishList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        IngredientDatum CategoryRecipesCardItems=dishListingre.get(position);

            holder.dishingre.setText(CategoryRecipesCardItems.getIngDetail());
            holder.dishnum.setText(CategoryRecipesCardItems.getIngId());
            holder.dishamt.setText(CategoryRecipesCardItems.getIngAmt());
    }
    @Override
    public int getItemCount() {
        return dishListingre.size();
    }
}

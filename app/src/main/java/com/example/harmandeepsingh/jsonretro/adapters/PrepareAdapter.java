package com.example.harmandeepsingh.jsonretro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.models.Step;

import java.util.List;

/**
 * Created by Harmandeep singh on 9/1/2017.
 */

public class PrepareAdapter extends RecyclerView.Adapter<PrepareAdapter.MyViewHolder> {
    private Context mContext;
    private List<Step> stepList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,category;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            category = (TextView) view.findViewById(R.id.category);
        }
    }

    public PrepareAdapter(Context mContext, List<Step> stepList) {
        this.mContext = mContext;
        this.stepList = stepList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Step CategoryRecipesCardItems = stepList.get(position);
        holder.title.setText(CategoryRecipesCardItems.getStepId());
        holder.category.setText(CategoryRecipesCardItems.getStepDetail());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }
}
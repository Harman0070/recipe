package com.example.harmandeepsingh.jsonretro.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.harmandeepsingh.jsonretro.Activity.IndianActivity;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.models.NewestRecipeName;

import java.util.ArrayList;

import static com.example.harmandeepsingh.jsonretro.helperclasses.Utils.webUrl;

/**
 * Created by Harmandeep singh on 8/23/2017.
 */

public class NewestCardAdapter extends RecyclerView.Adapter<NewestCardAdapter.MyViewHolder> {
     private Context mContext;
     public ArrayList<NewestRecipeName> dishList;
   //  public ArrayList<NewestRecipeName> filterList;

    // CustomFilter filte
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,category;
        ImageView thumbnail;
        View cardview;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            cardview = (CardView) view.findViewById(R.id.card_view);
            cardview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=(int) view.getTag();
            NewestRecipeName newrecipe=dishList.get(pos);
            String id=newrecipe.getPrecipeId();
            Intent i=new Intent(mContext, IndianActivity.class);
            i.putExtra("ID1",id);
            mContext.startActivity(i);
        }
    }


    public NewestCardAdapter(Context mContext, ArrayList<NewestRecipeName> dishList) {
        this.mContext = mContext;
        this.dishList = dishList;
        //this.filterList=dishList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_card, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NewestRecipeName NewestRecipeName = dishList.get(position);

        holder.cardview.setTag(position);

        holder.title.setText(NewestRecipeName.getPrecipeName());
        // loading album cover using Glide library
        //Glide.with(mContext).load(NewestCardItems.getPrecipeImage()).into(holder.thumbnail);

        String imgThumbnail = webUrl+NewestRecipeName.getPrecipeImage();

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

/*    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }*/

/*    public Filter getFilter() {
        //FILTERING OCURS
        return new Filter() {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            Filter.FilterResults results=new Filter.FilterResults();

            //CHECK CONSTRAINT VALIDITY
            if(constraint != null && constraint.length() > 0)
            {
                //CHANGE TO UPPER
                constraint=constraint.toString().toUpperCase();
                //STORE OUR FILTERED PLAYERS

                ArrayList<NewestRecipeName> filteredPlayers=new ArrayList<>();

                for (int i=0;i<filterList.size();i++)
                {
                    //CHECK
                    int recipeid=Integer.parseInt(filterList.get(i).getPrecipeId());
                    getItemId(recipeid);
                    Log.d("Filterlist",""+filterList);
                    if(filterList.get(i).getPrecipeName().toUpperCase().contains(constraint))
                    {
                        //ADD PLAYER TO FILTERED PLAYERS
                        filteredPlayers.add(filterList.get(i));

                    }
                }

                results.count= filteredPlayers.size();
                results.values=filteredPlayers;
            }else
            {
                results.count = filterList.size();
                results.values= filterList;

            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

            dishList= (ArrayList<NewestRecipeName>) results.values;

            //REFRESH
       notifyDataSetChanged();
        }};
    }*/

   public void setFilter(ArrayList<NewestRecipeName> newList){
        dishList=new ArrayList<>();
       dishList.addAll(newList);
       notifyDataSetChanged();
   }
  /*  public NewestRecipeName getItem (int position) {
        return dishList.get(position);
    }*/
/*    @Override
    public long getItemId(int recipeid) {

        int itemID;

        if (filterList == null)
        {
            itemID = recipeid;
        }
        else
        {
            itemID = filterList.indexOf(dishList.get(recipeid));
        }
        return itemID;
    }*/
}

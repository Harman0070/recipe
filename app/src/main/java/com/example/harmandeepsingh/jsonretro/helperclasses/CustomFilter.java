package com.example.harmandeepsingh.jsonretro.helperclasses;

import android.util.Log;
import android.widget.Filter;

import com.example.harmandeepsingh.jsonretro.adapters.NewestCardAdapter;
import com.example.harmandeepsingh.jsonretro.models.NewestRecipeName;

import java.util.ArrayList;

/**
 * Created by Hp on 3/17/2016.
 */
public class CustomFilter extends Filter {

    NewestCardAdapter adapter;
    ArrayList<NewestRecipeName> filterList;


    public CustomFilter(ArrayList<NewestRecipeName> filterList, NewestCardAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

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
    protected void publishResults(CharSequence constraint, FilterResults results) {

       adapter.dishList= (ArrayList<NewestRecipeName>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

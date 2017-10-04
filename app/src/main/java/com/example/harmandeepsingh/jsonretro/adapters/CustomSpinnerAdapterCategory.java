package com.example.harmandeepsingh.jsonretro.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.harmandeepsingh.jsonretro.models.SpinnerCategoryName;

import java.util.List;

/**
 * Created by Harmandeep singh on 9/21/2017.
 */

public class CustomSpinnerAdapterCategory extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private List<SpinnerCategoryName> categoryList;


    public CustomSpinnerAdapterCategory(Context context,List<SpinnerCategoryName> categoryList) {
        this.categoryList=categoryList;
        mContext = context;
    }

    public int getCount()
    {
        return categoryList.size();
    }

    public Object getItem(int i)
    {
        return categoryList.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(categoryList.get(position).getRecipeTypeName());
        //txt.setText(countryList.get(position));
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(mContext);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        //   txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setText(categoryList.get(i).getRecipeTypeName());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

}

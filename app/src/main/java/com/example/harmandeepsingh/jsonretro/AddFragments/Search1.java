package com.example.harmandeepsingh.jsonretro.AddFragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.AddActivity.AddRecipe1;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.CustomSpinnerAdapter;
import com.example.harmandeepsingh.jsonretro.adapters.CustomSpinnerAdapterCategory;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.AddcategoryModel;
import com.example.harmandeepsingh.jsonretro.models.CountryName;
import com.example.harmandeepsingh.jsonretro.models.CountryrecipeModel;
import com.example.harmandeepsingh.jsonretro.models.SpinnerCategoryName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search1 extends Fragment {
    TextView txt1,txt2;
    public String categoryid;
    Spinner spinnerCustom1,spinnerCustom2;
    ProgressDialog pDialog;
    FloatingActionButton fab;
    private List<CountryName> countryList;
    private List<SpinnerCategoryName> categoryList;
    CustomSpinnerAdapter dataAdapter1;
    CustomSpinnerAdapterCategory dataAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.add_fragment_search1, container, false);
        final Toolbar myToolbar = (Toolbar)v. findViewById(R.id.search1Toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt1 = (TextView) v.findViewById(R.id.countrybt);
        txt2 = (TextView) v.findViewById(R.id.categorybt);
        spinnerCustom1=(Spinner)v.findViewById(R.id.spinnerCustom1);
        spinnerCustom2=(Spinner)v.findViewById(R.id.spinnerCustom2);
        fab= (FloatingActionButton)v.findViewById(R.id.fab1);

        countryList = new ArrayList<>();
        dataAdapter1=new CustomSpinnerAdapter(getActivity(),countryList);
        preparecountry();
        spinnerCustom1.setAdapter(dataAdapter1);

        spinnerCustom1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                preparecategory(countryList.get(position).getCountryType());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categoryList = new ArrayList<>();
        dataAdapter2=new CustomSpinnerAdapterCategory(getActivity(),categoryList);
        //preparecategory(countryList.get(position).getCountryType());
        spinnerCustom2.setAdapter(dataAdapter2);
        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCustom2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                categoryid=categoryList.get(position).getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Search2 llf = new Search2();
                ft.replace(R.id.search1, llf);
                //fm.popBackStack();
                 // or 'getSupportFragmentManager();'
                int count = fm.getBackStackEntryCount();
                for(int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }
               // ft.addToBackStack(null);
                ft.commit();
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Search3 llf = new Search3();
                ft.replace(R.id.search1, llf);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Fab", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.app_name);
                // builder.setIcon(R.drawable.ic_launcher);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent i=new Intent(getActivity(), AddRecipe1.class);
                        i.putExtra("cat_id",categoryid);
                        startActivity(i);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        return v;
    }

    public void preparecountry(){
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
        //con_id ="1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<CountryrecipeModel> userCall = service.getcountryid("");
            userCall.enqueue(new Callback<CountryrecipeModel>() {
                @Override
                public void onResponse(Call<CountryrecipeModel> call, Response<CountryrecipeModel> response) {
                    hidepDialog();
                    Log.d("spinnerresponse"," "+response.body().getSuccess());
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            for (int i = 0; i < response.body().getCountryName().size(); i++) {
                                CountryName country = new CountryName();
                                country.setCountryName(response.body().getCountryName().get(i).getCountryName());
                                country.setCountryType(response.body().getCountryName().get(i).getCountryType());

                                Log.d("spinnercountry", " " + response.body().getCountryName().get(i).getCountryName());
                                Log.d("spinnercatid", " " + response.body().getCountryName().get(i).getCountryType());
                                countryList.add(country);
                                dataAdapter1.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CountryrecipeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
        // preparecountry(m);
        dataAdapter1.notifyDataSetChanged();
    }

    public void preparecategory(String countryType){
        categoryList.clear();
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("Loading data...");
        pDialog.setCancelable(false);
        //con_id ="1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<AddcategoryModel> userCall = service.parentid(countryType);
            userCall.enqueue(new Callback<AddcategoryModel>() {
                @Override
                public void onResponse(Call<AddcategoryModel> call, Response<AddcategoryModel> response) {
                    hidepDialog();
                    Log.d("spinnerresponse"," "+response.body().getSuccess());
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            for (int i = 0; i < response.body().getSpinnerCategoryName().size(); i++) {
                                SpinnerCategoryName country1 = new SpinnerCategoryName();
                                country1.setRecipeTypeName(response.body().getSpinnerCategoryName().get(i).getRecipeTypeName());
                                country1.setCategoryId(response.body().getSpinnerCategoryName().get(i).getCategoryId());
                                Log.d("spinnercategory", " " + response.body().getSpinnerCategoryName().get(i).getRecipeTypeName());
                                Log.d("spinnercategoryid", " " + response.body().getSpinnerCategoryName().get(i).getCategoryId());
                                categoryList.add(country1);
                                dataAdapter2.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddcategoryModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
        // preparecountry(m);
        dataAdapter2.notifyDataSetChanged();
    }

    //to show progress dialog
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    //to hide progress
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


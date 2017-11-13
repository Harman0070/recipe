package com.example.harmandeepsingh.jsonretro.AddFragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.CustomSpinnerAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;
import com.example.harmandeepsingh.jsonretro.models.CountryName;
import com.example.harmandeepsingh.jsonretro.models.CountryrecipeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 9/16/2017.
 */

public class Search3 extends Fragment {
    List<CountryName> countryList;
    Spinner spinnerCustomSearch3;
    CustomSpinnerAdapter dataAdapter1;
    ProgressDialog pDialog;
    FloatingActionButton fab;
    EditText country_search2,category_search3;
    String countryid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.add_fragment_search3, container, false);
        spinnerCustomSearch3 =(Spinner)v.findViewById(R.id.spinnerCustomSearch3);
        category_search3=(EditText)v.findViewById(R.id.category_search3);
        countryList=new ArrayList<>();
        dataAdapter1=new CustomSpinnerAdapter(getActivity(),countryList);
        preparecountry();

        spinnerCustomSearch3.setAdapter(dataAdapter1);

        spinnerCustomSearch3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                // Toast.makeText(parent.getContext(),countryList.get(position).getCountryType(), Toast.LENGTH_LONG).show();
                //  countryid=countryList.get(position).getCountryType();

                countryid=countryList.get(position).getCountryType();
                Log.d("mycon",""+countryid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab=(FloatingActionButton)v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate(category_search3.getText().toString())) {
                    Log.d("IDsearch3",""+countryid);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Are you Sure?");
                    // builder.setIcon(R.drawable.ic_launcher);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            addtodatabase(category_search3.getText().toString(),countryid);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
        return v;
    }

    private boolean validate(String categorysearch3){

        if (categorysearch3.equals("") ){
            category_search3.setError("Data is null or short");
            category_search3.setFocusable(true);
            Toast.makeText(getActivity(), "Category Not Selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    public void addtodatabase(String category_name,String countryid){

        //     categoryList.clear();
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(false);
        //con_id ="1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Addcountrysearch2Model> userCall = service.getcountryid(category_name,countryid);
            userCall.enqueue(new Callback<Addcountrysearch2Model>() {
                @Override
                public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                    hidepDialog();
                    // Log.d("spinnerresponse"," "+response.body().getSuccess());
                    Toast.makeText(getActivity(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

                       // startActivity(new Intent(getActivity(), Search1.class));
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Search1 llf = new Search1();
                        ft.replace(R.id.search3, llf);
                        int count = fm.getBackStackEntryCount();
                        for(int i = 0; i < count; ++i) {
                            fm.popBackStack();
                        }
                        // Toast.makeText(getActivity(), "Fab2", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "not success", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<Addcountrysearch2Model> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
        // preparecountry(m);
        //ataAdapter2.notifyDataSetChanged();
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
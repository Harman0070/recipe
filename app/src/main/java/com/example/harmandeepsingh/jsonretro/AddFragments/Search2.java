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
import android.widget.EditText;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 9/16/2017.
 */

public class Search2 extends Fragment {
FloatingActionButton fab;
    EditText country_search2,category_search2;
    String countrysearch2,categorysearch2;
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.add_fragment_search2, container, false);

        country_search2=(EditText)v.findViewById(R.id.country_search2);
        category_search2=(EditText)v.findViewById(R.id.category_search2);


        fab=(FloatingActionButton)v.findViewById(R.id.fab1);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   countrysearch2 = country_search2.getText().toString();
                   // categorysearch2 = category_search2.getText().toString();
                    if(validate(country_search2.getText().toString(),category_search2.getText().toString())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.app_name);
                        builder.setMessage("Are you Sure?");
                        // builder.setIcon(R.drawable.ic_launcher);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                addtodatabase(country_search2.getText().toString(),category_search2.getText().toString());
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

    private boolean validate(String countrysearch2,String categorysearch2){
            if (countrysearch2.equals("") ){
            // country_search2.setError("Data is null or short");
            // country_search2.setFocusable(true);
            Toast.makeText(getActivity(), "Country Not Selected", Toast.LENGTH_SHORT).show();
            return false;
            }
            if (categorysearch2.equals("") ){
            category_search2.setError("Data is null or short");
            category_search2.setFocusable(true);
            Toast.makeText(getActivity(), "Category Not Selected", Toast.LENGTH_SHORT).show();
            return false;
            }
            return true;
            }

    public void addtodatabase(String country_name,String category_name){

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
                Call<Addcountrysearch2Model> userCall = service.countryid(country_name,category_name);
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
                            ft.replace(R.id.search2, llf);
                            int count = fm.getBackStackEntryCount();
                            for(int i = 0; i < count; ++i) {
                                fm.popBackStack();
                            }
                            //ft.addToBackStack(null);

                            ft.commit();
                          /*Intent i = new Intent(getActivity(),AddRecipe1.class);
                           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();*/

                            Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "not success", Toast.LENGTH_SHORT).show();
                        }

                    }

                    private void finish() {
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




package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.AddRecipe2Adapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;
import com.example.harmandeepsingh.jsonretro.models.Ingre1Model;
import com.example.harmandeepsingh.jsonretro.models.IngredientDatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddRecipe2 extends Activity {
    RecyclerView recyclerView;
    Button addBt;
    ImageButton imagebtDelete;
    TextView result;
    TextView ingredienttxt,amounttxt ;
    ProgressDialog pDialog;
    EditText ingredient,amount;
    List<IngredientDatum> dishlistingre;
    TextView dt,dn;
    AddRecipe2Adapter adapteringre;
    String ing_id;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe2);
        //getIntent();
        recyclerView=(RecyclerView)findViewById(R.id.addrecipe2_recycler_view);
fab=(FloatingActionButton)findViewById(R.id.fabaddrecipe2);
        addBt = (Button) findViewById(R.id.addBt);
        imagebtDelete = (ImageButton) findViewById(R.id.imagebtDelete);

        //ingredienttxt=(TextView)findViewById(R.id.ingredienttxt);
       // amounttxt=(TextView)findViewById(R.id.amounttxt);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //  recyclerView.setAdapter();

        // add button listener
        addBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
               // Toast.makeText(AddRecipe2.this, "Add new", Toast.LENGTH_SHORT).show();
                alertdialog1();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareingredients();
                mSwipeRefreshLayout.setRefreshing(false);
                dishlistingre.clear();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddRecipe2.this,AddRecipe3.class);
                startActivity(i);
            }
        });
        /*imagebtDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Toast.makeText(AddRecipe2.this, "Add new", Toast.LENGTH_SHORT).show();
                alertdialog2();
            }
        });*/

    }

    public void alertdialog1() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.prompts, null);
        dialogBuilder.setView(dialogView);

        ingredient = (EditText) dialogView.findViewById(R.id.addIngredients);
        amount = (EditText) dialogView.findViewById(R.id.addAmount);

        dialogBuilder.setTitle("Add Here");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
               // ingredienttxt.setText(ingredient.getText());
                //amounttxt.setText(amount.getText());
             //   if(validate(ingredient.getText().toString(),amount.getText().toString())) {

                    addtoDatabase(ingredient.getText().toString(),amount.getText().toString());
                    hidepDialog();
                    initialize();
                    // Toast.makeText(AddRecipe2.this, "added", Toast.LENGTH_SHORT).show();

            }       
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public boolean validate(String ingTxt,String amtTxt){
        if(!ingTxt.matches("[a-zA-Z ]")||ingTxt.matches("")){
            ingredient.setError("Data is invalid or empty");
            ingredient.setFocusable(true);
            return false;
        }
        if(amtTxt.matches("")){
            amount.setError("Data is empty");
            amount.setFocusable(true);
            return false;
        }

       // Toast.makeText(this, "Invalid Ingredient Entry", Toast.LENGTH_LONG).show();
        return true;
    }
    public void addtoDatabase(String ingredient,String amount){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(true);

        //String cat_id =getIntent().getStringExtra("cat_id");
        String cat_id="7";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();
            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Addcountrysearch2Model> userCall = service.addrecipe2data(cat_id,ingredient,amount);
            userCall.enqueue(new Callback<Addcountrysearch2Model>() {
                @Override
                public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                    hidepDialog();
                    // Log.d("spinnerresponse"," "+response.body().getSuccess());
                   // Toast.makeText(getApplication(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

                        // startActivity(new Intent(getApplication(), AddRecipe2.class));
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_SHORT).show();
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
        //ataAdapter2.notifyDataSetChanged();
    }
    void initialize(){
        dishlistingre=new ArrayList<>();
        adapteringre=new AddRecipe2Adapter(this,dishlistingre);
        dt=(TextView)findViewById(R.id.dishDetail);
        recyclerView=(RecyclerView)findViewById(R.id.addrecipe2_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddRecipe2.this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapteringre);
        prepareingredients();
    }
    void prepareingredients(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
        //String str1 = getActivity().getIntent().getStringExtra("ID1");
        //String recipename = getActivity().getIntent().getStringExtra("recipename");
        //getActivity().setTitle(recipename);
        //getActivity().setTitleColor(Color.parseColor("#ffffff"));
        //Log.d("recipename",""+recipename);
        String cat1_id ="7";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();

            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Ingre1Model> userCall = service.getingredients(cat1_id);
            userCall.enqueue(new Callback<Ingre1Model>() {
                @Override
                public void onResponse(Call<Ingre1Model> call, Response<Ingre1Model> response) {
                    hidepDialog();
                    dishlistingre.clear();
                    Log.d("Indian"," "+response.body().getSuccess());
                    Log.d("DAta",""+response);
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            // Toast.makeText(getActivity(), "ResponseIng"+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                            //dt.setText(response.body().getDetail().getPrecipeDetail());
                            //dn.setText(response.body().getDetail().getPrecipeName());
                            Log.d("Mydata",""+response.body().getDetail().getPrecipeDetail());
                            Log.d("Mydata",""+response.body().getDetail().getPrecipeName());

                            for (int i = 0; i < response.body().getIngredientData().size(); i++) {
                                IngredientDatum recipe = new IngredientDatum();
                                recipe.setIngId(response.body().getIngredientData().get(i).getIngId());
                                recipe.setIngParentId(response.body().getIngredientData().get(i).getIngParentId());
                                recipe.setIngDetail(response.body().getIngredientData().get(i).getIngDetail());
                                recipe.setIngAmt(response.body().getIngredientData().get(i).getIngAmt());
                                Log.d("Ingredient Id",""+response.body().getIngredientData().get(i).getIngId());
                                dishlistingre.add(recipe);
                            }

                            adapteringre.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplication(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplication(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Ingre1Model> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        adapteringre.notifyDataSetChanged();
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

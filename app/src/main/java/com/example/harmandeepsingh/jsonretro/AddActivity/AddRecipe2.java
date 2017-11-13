package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.rgb;
import static com.example.harmandeepsingh.jsonretro.R.style.CustomShowcaseTheme2;


public class AddRecipe2 extends AppCompatActivity {
    RecyclerView recyclerView;
    Button addBt;
    ImageButton imagebtDelete;
    ProgressDialog pDialog;
    EditText ingredient,amount;
    List<IngredientDatum> dishlistingre;
    TextView dt;
    AddRecipe2Adapter adapteringre;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton fab;
    String cat_id_addrecipe2;
    ShowcaseView showcaseView;
    String RecipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe2);
        RecipeName=getIntent().getStringExtra("recipe_name");
        Log.d("RecipeName",""+RecipeName);
        setTitle(RecipeName);
        setTitleColor(rgb(255,255,255));
        recyclerView=(RecyclerView)findViewById(R.id.addrecipe2_recycler_view);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 52)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        ViewTarget target = new ViewTarget(findViewById(R.id.addBt));
        showcaseView=  new ShowcaseView.Builder(this)
                .setStyle(CustomShowcaseTheme2)

                .setContentTitle("Add Ingredients and Amount By Clicking here")
                //.setContentText("Click here and you will get options to navigate to other sections.")
                .useDecorViewAsParent() //this is the difference
                .setTarget(target)
                .build();
        showcaseView.setButtonPosition(lps);

       // noIng=(TextView)findViewById(R.id.noIng);
        fab=(FloatingActionButton)findViewById(R.id.fabaddrecipe2);
        addBt = (Button) findViewById(R.id.addBt);

        imagebtDelete = (ImageButton) findViewById(R.id.imagebtDelete);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

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
                i.putExtra("cat_id_addrecipe3",cat_id_addrecipe2);
                startActivity(i);
            }
        });
    }

    public void alertdialog1() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.prompts, null);
        dialogBuilder.setView(dialogView);

        ingredient = (EditText) dialogView.findViewById(R.id.addIngredients);
        amount = (EditText) dialogView.findViewById(R.id.addAmount);

        dialogBuilder.setTitle("Add");

        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                String amt=amount.getText().toString();
                Log.d("amt",""+amt);

                if (validate()) {
                    addtoDatabase(ingredient.getText().toString(), amount.getText().toString());
                    hidepDialog();
                    initialize();
                }

            }       
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private Boolean validate() {
        String ing = ingredient.getText().toString();
        String amountt = amount.getText().toString();
        if(!ing.matches("[a-zA-Z]+")||ing.equals("")){
            ingredient.setError("Data is invalid or empty");
            ingredient.setFocusable(true);
            Log.d("matchdata:",""+ing);
            Log.d("match:",""+ing.matches("[a-zA-Z]+"));
            return false;
        }
        if(amountt.equals("")){
            amount.setError("Data is empty");
            amount.setFocusable(true);
            // Log.d("amtmatch",""+amtTxt);
            return false;
        }
        return true;
    }

    public void addtoDatabase(String ingredient,String amount){

        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(true);

        cat_id_addrecipe2 =getIntent().getStringExtra("recipe_id");
        Log.d("recipe_id",""+cat_id_addrecipe2);
       // String cat_id="7";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();
            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Addcountrysearch2Model> userCall = service.addrecipe2data(cat_id_addrecipe2,ingredient,amount);
            userCall.enqueue(new Callback<Addcountrysearch2Model>() {
                @Override
                public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                    hidepDialog();
                    // Log.d("spinnerresponse"," "+response.body().getSuccess());
                   // Toast.makeText(getApplication(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

                        // startActivity(new Intent(getApplication(), AddRecipe2.class));
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                      //  noIng.setVisibility(View.GONE);

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
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapteringre);
        prepareingredients();
    }
    void prepareingredients(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
        showpDialog();

        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();

            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Ingre1Model> userCall = service.getingredients(cat_id_addrecipe2);
            userCall.enqueue(new Callback<Ingre1Model>() {
                @Override
                public void onResponse(Call<Ingre1Model> call, Response<Ingre1Model> response) {
                    hidepDialog();
                    dishlistingre.clear();
                    Log.d("Indian"," "+response.body().getSuccess());
                    Log.d("DAta",""+response);
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            for (int i = 0; i < response.body().getIngredientData().size(); i++) {
                                IngredientDatum recipe = new IngredientDatum();
                                recipe.setIngId(response.body().getIngredientData().get(i).getIngId());
                                recipe.setIngParentId(response.body().getIngredientData().get(i).getIngParentId());
                                recipe.setIngDetail(response.body().getIngredientData().get(i).getIngDetail());
                                recipe.setIngAmt(response.body().getIngredientData().get(i).getIngAmt());
                                Log.d("Ingredient Id",""+response.body().getIngredientData().get(i).getIngId());
                                dishlistingre.add(recipe);
                               // noIng.setVisibility(View.GONE);
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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            // do something here
            showcaseView.hide();
            alertdialog1();
        }
        return super.onOptionsItemSelected(item);
    }
    }

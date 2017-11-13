package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Activity.MainActivity;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.AddRecipe3Adapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;
import com.example.harmandeepsingh.jsonretro.models.Step;
import com.example.harmandeepsingh.jsonretro.models.StepsprepareModel;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.harmandeepsingh.jsonretro.R.id.addSteps;
import static com.example.harmandeepsingh.jsonretro.R.style.CustomShowcaseTheme2;

public class AddRecipe3 extends AppCompatActivity {
    Button addstepsBt;
    EditText steps;
    ProgressDialog pDialog;
    List<Step> stepList;
    RecyclerView recyclerView;
    AddRecipe3Adapter adapter;
    TextView noStep;
    String cat_id_addrecipe3;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Button finishBt;
    ShowcaseView showcaseView;
    TextView textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe3);
       // noStep=(TextView)findViewById(R.id.noStep);

        //preparesteps();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //preparesteps();
                preparesteps();
                mSwipeRefreshLayout.setRefreshing(false);
                stepList.clear();
            }
        });


        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 52)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        ViewTarget target = new ViewTarget(findViewById(R.id.addstepsBt));
        showcaseView=  new ShowcaseView.Builder(this)
                .setStyle(CustomShowcaseTheme2)
                .setContentTitle("Add Steps By Clicking here")
                //.setContentText("Click here and you will get options to navigate to other sections.")
                .useDecorViewAsParent() //this is the difference
                .setTarget(target)
                .build();
        showcaseView.setButtonPosition(lps);


      /*  finishBt=(Button)findViewById(R.id.finishBt);
        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=  new Intent(AddRecipe3.this,MainActivity.class);
                startActivity(intent1);
               //startActivity(new Intent(AddRecipe3.this,CategoryRecipes.class));
            }
        });*/
     /*   addstepsBt=(Button)findViewById(R.id.addstepsBt);

        addstepsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog();
            }
        });*/


    }
/*
    TextWatcher watch = new TextWatcher(){

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {
            // TODO Auto-generated method stub
            Log.d("alength",""+s);
            Log.d("alength",""+s.length());

            textWatcher.setText(String.valueOf(150-s.length()));

            //output.setText(s);
            // if(s.length() == 150){
            //Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_LONG).show();

        }};
*/

    public void alertdialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.promptsstep, null);
        dialogBuilder.setView(dialogView);

        steps = (EditText) dialogView.findViewById(addSteps);
      ///  steps.addTextChangedListener(watch);
        dialogBuilder.setTitle("Add Here");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

               addstepstodatabase(steps.getText().toString());
                    hidepDialog();
                initialize();

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


    public void addstepstodatabase(String steps){

        pDialog=new ProgressDialog(this);
        hidepDialog();
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(true);

        cat_id_addrecipe3 =getIntent().getStringExtra("cat_id_addrecipe3");
        Log.d("cat_id",""+cat_id_addrecipe3);
        //String step_parent_id="7";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();
            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Addcountrysearch2Model> userCall = service.addrecipe3data(cat_id_addrecipe3,steps);
            userCall.enqueue(new Callback<Addcountrysearch2Model>() {
                @Override
                public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                   
                    // Log.d("spinnerresponse"," "+response.body().getSuccess());
                    // Toast.makeText(getApplication(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        //hidepDialog();

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
    public void initialize(){
        stepList=new ArrayList<>();
        adapter=new AddRecipe3Adapter(this,stepList);
        recyclerView=(RecyclerView)findViewById(R.id.addrecipe3_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        textWatcher=(TextView)findViewById(R.id.textWatcher);
        preparesteps();
        hidepDialog();
    }

    void preparesteps(){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
Log.d("Addrecipe3_cat_idharman",""+cat_id_addrecipe3);
        //String str = this.getIntent().getStringExtra("ID1");

        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();

            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

          //  Log.d("DataLog",""+par_id);
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<StepsprepareModel> userCall = service.getsteps(cat_id_addrecipe3);
            userCall.enqueue(new Callback<StepsprepareModel>() {
                @Override
                public void onResponse(Call<StepsprepareModel> call, Response<StepsprepareModel> response) {
                    hidepDialog();

                   // Log.d("Indian"," "+response.body().getSuccess());
                    //Log.d("DAta",""+response);
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            //You=(response.body().getVideoID().getPrecipeVideoId());
                           // Log.d("Video ID"," "+response.body().getVideoID().getPrecipeVideoId());
                            for (int i = 0; i < response.body().getSteps().size(); i++) {
                                Step recipe = new Step();
                                recipe.setStepId(""+(i+1));
                                recipe.setStepParentId(response.body().getSteps().get(i).getStepParentId());
                                recipe.setStepDetail(response.body().getSteps().get(i).getStepDetail());
                                recipe.setStepId(response.body().getSteps().get(i).getStepId());
                                Log.d("Step Id",""+response.body().getSteps().get(i).getStepId());
                                stepList.add(recipe);
                              //  noStep.setVisibility(View.GONE);
                            }

                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StepsprepareModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        adapter.notifyDataSetChanged();
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
    class MyClickableSpan extends ApiClient {// extend ClickableSpan

        String clicked;
        public MyClickableSpan(String string) {
            super();
            clicked = string;
        }

        public void onClick(View tv) {
            Toast.makeText(AddRecipe3.this,clicked , Toast.LENGTH_SHORT).show();
        }

        public void updateDrawState(TextPaint ds) {// override updateDrawState
            ds.setUnderlineText(false); // set to false to remove underline
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddrecipe3, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybuttonsteps) {
            // do something here
            showcaseView.hide();
            alertdialog();
        }
        else if(id==R.id.finishButton){
            Intent i=new Intent(AddRecipe3.this,MainActivity.class);
        startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}

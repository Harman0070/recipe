package com.example.harmandeepsingh.jsonretro.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.CategoryRecipesCardAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.RecyclerTouchListener;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.RecipePartModel;
import com.example.harmandeepsingh.jsonretro.models.RecipeparticularDatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRecipes extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog pDialog;
    CategoryRecipesCardAdapter adapter;
    TextView noCategoryDishes;
    private List<RecipeparticularDatum> dishList;
    private int offset=0;
    Toolbar toolbar;
    private boolean loading=false;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_recipes);
        noCategoryDishes=(TextView)findViewById(R.id.noDishes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        recyclerView=(RecyclerView)findViewById(R.id.category_recycler_view);
        dishList = new ArrayList<>();
        adapter = new CategoryRecipesCardAdapter(this, dishList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(!checkInternetState.getInstance(CategoryRecipes.this).isOnline())
        {
            Toast.makeText(CategoryRecipes.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }else{

            new AsyncTaskRunner(CategoryRecipes.this,offset).execute();
        }

        Intent i=getIntent();
        ID= i.getStringExtra("ID:");
        String dishCategory= i.getStringExtra("dishCategory");
        Log.d("dishCategory:",""+dishCategory);
        setTitle(dishCategory);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final RecipeparticularDatum notice = dishList.get(position);
                String recipeparticularid=notice.getPrecipeId();
                String categoryId =notice.getCategoryId();
                String recipename=notice.getPrecipeName();

                Intent intent1=  new Intent(CategoryRecipes.this,IndianActivity.class);
                intent1.putExtra("ID1",recipeparticularid);
                intent1.putExtra("recipename",recipename);
                startActivity(intent1);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)) {
                    if (!loading) {
                        loading = true;
                        if (!checkInternetState.getInstance(CategoryRecipes.this).isOnline()) {
                            Toast.makeText(CategoryRecipes.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                        } else {
                            new AsyncTaskRunner(CategoryRecipes.this, offset).execute();
                        }
                    }
                }

            }
        });
    }
    private class AsyncTaskRunner extends AsyncTask<Void,Void,String>{
    Context ctx;
    int offset;

    AsyncTaskRunner(Context ctx,int offset){
        this.ctx=ctx;
        this.offset=offset;
    }

    @Override
 protected void onPreExecute(){
        pDialog= ProgressDialog.show(ctx, "ProgressDialog", "Loading");
    }

    @Override
    protected String doInBackground(Void... voids) {
        //check internet state
        if (!checkInternetState.getInstance(CategoryRecipes.this).isOnline()) {
            hidepDialog();
            Toast.makeText(CategoryRecipes.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }else {

            int limit=20;
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<RecipePartModel> userCall = service.getparticularrecipes(ID,offset,limit);
            userCall.enqueue(new Callback<RecipePartModel>() {
                @Override
                public void onResponse(Call<RecipePartModel> call, Response<RecipePartModel> response) {
                    hidepDialog();
                    Log.d("Indian"," "+response.body().getSuccess());
                    Log.d("Indian"," "+response.body().getMessage());
                    if (response.isSuccessful()){
                        if (offset<response.body().getOffset()) {
                            offset=response.body().getOffset();
                            Log.d("catoffset",""+response.body().getOffset());
                            for (int i = 0; i < response.body().getRecipeparticularData().size(); i++) {
                                RecipeparticularDatum recipe1 = new RecipeparticularDatum();
                                recipe1.setCategoryId(response.body().getRecipeparticularData().get(i).getCategoryId());
                                recipe1.setCategoryParrentId(response.body().getRecipeparticularData().get(i).getCategoryParrentId());
                                recipe1.setRecipeTypeName(response.body().getRecipeparticularData().get(i).getRecipeTypeName());
                                recipe1.setRecipeImage(response.body().getRecipeparticularData().get(i).getRecipeImage());
                                recipe1.setPrecipeId(response.body().getRecipeparticularData().get(i).getPrecipeId());
                                recipe1.setPrecipeName(response.body().getRecipeparticularData().get(i).getPrecipeName());
                                recipe1.setPrecipeImage(response.body().getRecipeparticularData().get(i).getPrecipeImage());
                                recipe1.setPrecipeParentId(response.body().getRecipeparticularData().get(i).getPrecipeParentId());
                                recipe1.setPrecipeDetail(response.body().getRecipeparticularData().get(i).getPrecipeDetail());
                                recipe1.setPrecipeVideoId(response.body().getRecipeparticularData().get(i).getPrecipeVideoId());

                                Log.d("DataImage",""+response.body().getRecipeparticularData().get(i).getPrecipeImage());
                                Log.d("DataImage",""+response.body().getRecipeparticularData().get(i).getPrecipeName());
                                dishList.add(recipe1);
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            // Toast.makeText(CategoryRecipes.this, "Try Again", Toast.LENGTH_SHORT).show();
                            noCategoryDishes.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RecipePartModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        pDialog.dismiss();
    }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
// finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
/* private void prepareAlbums1() {
        int[] covers = new int[]{
                R.drawable.paneerbutter,
                R.drawable.palakpaneer,
                R.drawable.chanamasala,
                R.drawable.dalmakhani,
                R.drawable.mattarpaneer,


        };
                CategoryRecipesCardItems a1 = new CategoryRecipesCardItems("True Romance", "13", covers[0]);
        dishList.add(a1);

        a1 = new CategoryRecipesCardItems("Xscpae", "8", covers[1]);
        dishList.add(a1);

        a1 = new CategoryRecipesCardItems("Maroon 5", "11", covers[2]);
        dishList.add(a1);

        a1 = new CategoryRecipesCardItems("Born to Die", "12", covers[3]);
        dishList.add(a1);

        a1 = new CategoryRecipesCardItems("Honeymoon", "14", covers[4]);
        dishList.add(a1);

        adapter.notifyDataSetChanged();


    }*/



/*private void preparecategoryrecipes(){
    pDialog=new ProgressDialog(this);
    pDialog.setMessage("fetching data...");
    pDialog.setCancelable(false);
    Intent i=getIntent();
    String ID= i.getStringExtra("ID:");
    String dishCategory= i.getStringExtra("dishCategory");
    Log.d("dishCategory:",""+dishCategory);
    setTitle(dishCategory);
    showpDialog();

    //check internet state
    if (!checkInternetState.getInstance(this).isOnline()) {
        hidepDialog();
        Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
    }else {
int limit=0;
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<RecipePartModel> userCall = service.getparticularrecipes(ID,offset,limit);
        userCall.enqueue(new Callback<RecipePartModel>() {
            @Override
            public void onResponse(Call<RecipePartModel> call, Response<RecipePartModel> response) {
                hidepDialog();
                Log.d("Indian"," "+response.body().getSuccess());
                Log.d("Indian"," "+response.body().getMessage());
                if (response.isSuccessful()){
                    if (response.body().getSuccess()) {
                        for (int i = 0; i < response.body().getRecipeparticularData().size(); i++) {
                            RecipeparticularDatum recipe1 = new RecipeparticularDatum();
                            recipe1.setCategoryId(response.body().getRecipeparticularData().get(i).getCategoryId());
                            recipe1.setCategoryParrentId(response.body().getRecipeparticularData().get(i).getCategoryParrentId());
                            recipe1.setRecipeTypeName(response.body().getRecipeparticularData().get(i).getRecipeTypeName());
                            recipe1.setRecipeImage(response.body().getRecipeparticularData().get(i).getRecipeImage());
                            recipe1.setPrecipeId(response.body().getRecipeparticularData().get(i).getPrecipeId());
                            recipe1.setPrecipeName(response.body().getRecipeparticularData().get(i).getPrecipeName());
                            recipe1.setPrecipeImage(response.body().getRecipeparticularData().get(i).getPrecipeImage());
                            recipe1.setPrecipeParentId(response.body().getRecipeparticularData().get(i).getPrecipeParentId());
                            recipe1.setPrecipeDetail(response.body().getRecipeparticularData().get(i).getPrecipeDetail());
                            recipe1.setPrecipeVideoId(response.body().getRecipeparticularData().get(i).getPrecipeVideoId());

                            Log.d("DataImage",""+response.body().getRecipeparticularData().get(i).getPrecipeImage());
                            Log.d("DataImage",""+response.body().getRecipeparticularData().get(i).getPrecipeName());
                            dishList.add(recipe1);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                       // Toast.makeText(CategoryRecipes.this, "Try Again", Toast.LENGTH_SHORT).show();
                        noCategoryDishes.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecipePartModel> call, Throwable t) {
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
    adapter.notifyDataSetChanged();
}*/





/*public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }*/
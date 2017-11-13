package com.example.harmandeepsingh.jsonretro.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.NewestCardAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.NewestModel;
import com.example.harmandeepsingh.jsonretro.models.NewestRecipeName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<NewestRecipeName> dishList;
    private RecyclerView recyclerView;
    private NewestCardAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dishList = new ArrayList<>();
        adapter = new NewestCardAdapter(SearchActivity.this,dishList);
        recyclerView = (RecyclerView) findViewById(R.id.newest_recycler_view);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(3), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        String query=getIntent().getStringExtra("query");
        if(!checkInternetState.getInstance(SearchActivity.this).isOnline())
        {
            Toast.makeText(SearchActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }else{

            new AsyncTaskRunner(SearchActivity.this,query).execute();
        }


       // initialize(getIntent().getStringExtra("query"));
        Log.d("q",""+getIntent().getStringExtra("query"));
    }
    public void initialize(String query){
int limit=20;
        if(!checkInternetState.getInstance(SearchActivity.this).isOnline()){
            Toast.makeText(SearchActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        } else{
            APIService service = ApiClient.getClient().create(APIService.class);
            //String recipename="test";
            Call<NewestModel> userCall=service.searchrecipe(query,limit);
            userCall.enqueue(new Callback<NewestModel>() {

                @Override
                public void onResponse(Call<NewestModel> call, Response<NewestModel> response) {
//                    dishList.clear();
                    if(response.isSuccessful()){
                        Log.d("isSuccess",""+response.isSuccessful());
                        Toast.makeText(SearchActivity.this, "Response Success", Toast.LENGTH_SHORT).show();
                        if(response.body().getSuccess()){
                            Log.d("getSuccess",""+response.body().getSuccess());
                            Toast.makeText(SearchActivity.this, "Data Success", Toast.LENGTH_SHORT).show();
                            for(int i=0;i<response.body().getNewestRecipeName().size();i++){
                                NewestRecipeName name=new NewestRecipeName();
                                name.setPrecipeId(response.body().getNewestRecipeName().get(i).getPrecipeId());
                                name.setPrecipeName(response.body().getNewestRecipeName().get(i).getPrecipeName());
                                name.setPrecipeDetail(response.body().getNewestRecipeName().get(i).getPrecipeDetail());
                                name.setUserId(response.body().getNewestRecipeName().get(i).getUserId());
                                name.setPrecipeParentId(response.body().getNewestRecipeName().get(i).getPrecipeParentId());
                                name.setPrecipeVideoId(response.body().getNewestRecipeName().get(i).getPrecipeVideoId());
                                name.setPrecipeImage(response.body().getNewestRecipeName().get(i).getPrecipeImage());
                                Log.d("PrecipeId",""+response.body().getNewestRecipeName().get(i).getPrecipeId());
                                dishList.add(name);
                                adapter.notifyDataSetChanged();

                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(SearchActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SearchActivity.this, "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NewestModel> call, Throwable t) {
                    Log.d("onFailure",""+t.getMessage());
                }
            });
        }
    }
    private class AsyncTaskRunner extends AsyncTask<Void, Void, String> {
        Context ctx;
        String query;

        public AsyncTaskRunner(Context ctx, String query) {
            this.ctx = ctx;
            this.query = query;
        }
        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(SearchActivity.this, "Loading", "Please Wait...");

        }

        @Override
        protected String doInBackground(Void... voids) {
            if (!checkInternetState.getInstance(SearchActivity.this).isOnline()) {
                Toast.makeText(SearchActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            } else {
                int limit=20;
                APIService service = ApiClient.getClient().create(APIService.class);
                //String recipename="test";

                Call<NewestModel> userCall = service.searchrecipe(query,limit);
                userCall.enqueue(new Callback<NewestModel>() {

                    @Override
                    public void onResponse(Call<NewestModel> call, Response<NewestModel> response) {
//                    dishList.clear();
                        if (response.isSuccessful()) {
                            Log.d("isSuccess", "" + response.isSuccessful());
                            Toast.makeText(SearchActivity.this, "Response Success", Toast.LENGTH_SHORT).show();
                            if (response.body().getSuccess()) {
                                Log.d("getSuccess", "" + response.body().getSuccess());
                                Toast.makeText(SearchActivity.this, "Data Success", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < response.body().getNewestRecipeName().size(); i++) {
                                    NewestRecipeName name = new NewestRecipeName();
                                    name.setPrecipeId(response.body().getNewestRecipeName().get(i).getPrecipeId());
                                    name.setPrecipeName(response.body().getNewestRecipeName().get(i).getPrecipeName());
                                    name.setPrecipeDetail(response.body().getNewestRecipeName().get(i).getPrecipeDetail());
                                    name.setUserId(response.body().getNewestRecipeName().get(i).getUserId());
                                    name.setPrecipeParentId(response.body().getNewestRecipeName().get(i).getPrecipeParentId());
                                    name.setPrecipeVideoId(response.body().getNewestRecipeName().get(i).getPrecipeVideoId());
                                    name.setPrecipeImage(response.body().getNewestRecipeName().get(i).getPrecipeImage());
                                    Log.d("PrecipeId", "" + response.body().getNewestRecipeName().get(i).getPrecipeId());
                                    dishList.add(name);
                                    adapter.notifyDataSetChanged();

                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(SearchActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, "not success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewestModel> call, Throwable t) {
                        Log.d("onFailure", "" + t.getMessage());
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            pDialog.dismiss();
            //finalResult.setText(result);
        }
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

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
}

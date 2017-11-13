package com.example.harmandeepsingh.jsonretro.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Activity.MainActivity;
import com.example.harmandeepsingh.jsonretro.Activity.SearchActivity;
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

/**
 * Created by Harmandeep singh on 8/22/2017.
 */

public class NewestFragment extends Fragment  {

    private ArrayList<NewestRecipeName> dishList;
    public ArrayList<NewestRecipeName> filterList;
    private RecyclerView recyclerView;
    private NewestCardAdapter adapter;
    TextView nodishes;
    ProgressDialog pDialog;
    private int offset=0;
    private boolean loading=false;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.LayoutManager mLayoutManager;
    public NewestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_newest, container, false);

        initialize(v);

        ((MainActivity) getActivity()).setActionBarTitle("Newest Recipes");
        return v;
    }
    private void initialize(View v){
       mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeToRefresh);
       mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
       nodishes=(TextView)v.findViewById(R.id.noDishes);
       dishList = new ArrayList<>();
       adapter = new NewestCardAdapter(getActivity(),dishList);

       recyclerView = (RecyclerView) v.findViewById(R.id.newest_recycler_view);

       mLayoutManager = new GridLayoutManager(getActivity(), 2);
       recyclerView.setLayoutManager(mLayoutManager);
       recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(3), true));
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.setAdapter(adapter);

       if(!checkInternetState.getInstance(getActivity()).isOnline())
       {
           Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
       }else{

           new AsyncTaskRunner(getActivity(),offset).execute();
       }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               offset=0;
               if(!checkInternetState.getInstance(getActivity()).isOnline())
               {
                   Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
               }else{
                  dishList.clear();
                   new AsyncTaskRunner(getActivity(),offset).execute();
                           }
             }
       });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {

                    if (!loading) {
                        loading = true;
                        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
                            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Scroll"+ offset, Toast.LENGTH_SHORT).show();
                            new AsyncTaskRunner(getContext(), offset).execute();
                            Log.d("scrolloffset", "" + offset);
                        }
                    }
                }
            }
        });
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
           @Override
           public void onClick(View view, int position) {
               final NewestRecipeName newrecipename = dishList.get(position);
               String dishcategoryid=newrecipename.getPrecipeId();
               Intent intent=  new Intent(getActivity(),IndianActivity.class);
               intent.putExtra("ID1",dishcategoryid);
               //intent.putExtra("ID1", adapter.getItemId(position) );
               startActivity(intent);
               if(filterList!=null){
                   intent.putExtra("ID1", adapter.getItemId(position) );
               }
               else{
                   intent.putExtra("ID1",dishcategoryid);
               }
               startActivity(intent);
           }

           @Override
           public void onLongClick(View view, int position) {

           }
       }));*/
    }

    public class AsyncTaskRunner extends AsyncTask<Void, Void, String> {
            Context ctx;
            public int offset1;

        public AsyncTaskRunner(Context ctx, int offset) {
            this.ctx = ctx;
            this.offset1 = offset;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(getContext(), "ProgressDialog", "Loading");
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(Void... voids) {

                APIService service = ApiClient.getClient().create(APIService.class);
                int limit=20;
                Call<NewestModel> userCall = service.getnewestrecipeid(limit,offset1);
                userCall.enqueue(new Callback<NewestModel>() {
                    @Override
                    public void onResponse(Call<NewestModel> call, Response<NewestModel> response) {
                        Log.d("response"," "+response.body());
                        hidepDialog();
                      //  dishList.clear();
                        Log.d("Indian"," "+response.body().getSuccess());
                        if (response.isSuccessful()){
                            if(response.body().getSuccess()){
                            loading = false;
                            if (offset1<response.body().getOffset()) {
                                offset=response.body().getOffset();
                                Log.d("Getoffset",""+response.body().getOffset());

                                for (int i = 0; i < response.body().getNewestRecipeName().size(); i++) {
                                    Log.d("Indiannewest"," "+response.body().getNewestRecipeName().size());
                                    NewestRecipeName recipe = new NewestRecipeName();
                                    recipe.setPrecipeId(response.body().getNewestRecipeName().get(i).getPrecipeId());
                                    recipe.setPrecipeName(response.body().getNewestRecipeName().get(i).getPrecipeName());
                                    recipe.setPrecipeImage(response.body().getNewestRecipeName().get(i).getPrecipeImage());
                                    recipe.setPrecipeParentId(response.body().getNewestRecipeName().get(i).getPrecipeParentId());
                                    recipe.setPrecipeDetail(response.body().getNewestRecipeName().get(i).getPrecipeDetail());
                                    recipe.setPrecipeVideoId(response.body().getNewestRecipeName().get(i).getPrecipeVideoId());
                                    Log.d("Data1",""+response.body().getNewestRecipeName().get(i).getPrecipeName());
                                    Log.d("Data2",""+response.body().getNewestRecipeName().get(i).getPrecipeId());
                                    Log.d("Data3",""+response.body().getNewestRecipeName().get(i).getPrecipeDetail());
                                    dishList.add(recipe);
                                   // scroll(offset);
                                }
                                adapter.notifyDataSetChanged();
                                mSwipeRefreshLayout.setRefreshing(false);
                            }else{
                                //Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                                nodishes.setVisibility(View.VISIBLE);
                            }
                        }
                        }else{
                            Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<NewestModel> call, Throwable t) {
                        Log.d("onFailure", t.toString());
                    }
                });
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            pDialog.dismiss();

            mSwipeRefreshLayout.setRefreshing(false);
            Log.d("offset",""+offset);
            mSwipeRefreshLayout.setEnabled(true);
            //finalResult.setText(result);
        }
    }

   /*void preparenewrecipes(){
       Toast.makeText(getActivity(),"new", Toast.LENGTH_SHORT).show();
       pDialog=new ProgressDialog(getContext());
       pDialog.setMessage("fetching data...");
       pDialog.setCancelable(false);

       showpDialog();
       //check internet state
       if (!checkInternetState.getInstance(getActivity()).isOnline()) {
           hidepDialog();
           Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
       }else {
        APIService service = ApiClient.getClient().create(APIService.class);
           Call<NewestModel> userCall = service.getnewestrecipeid("  ");
           userCall.enqueue(new Callback<NewestModel>() {
               @Override
               public void onResponse(Call<NewestModel> call, Response<NewestModel> response) {
                   Log.d("response"," "+response.body());
                   hidepDialog();

                   Log.d("Indian"," "+response.body().getSuccess());
                   if (response.isSuccessful()){
                       if (response.body().getSuccess()) {
                           //Toast.makeText(getActivity(), "This is response", Toast.LENGTH_SHORT).show();

                           for (int i = 0; i < response.body().getNewestRecipeName().size(); i++) {
                               Log.d("Indiannewest"," "+response.body().getNewestRecipeName().size());
                               NewestRecipeName recipe = new NewestRecipeName();
                               recipe.setPrecipeId(response.body().getNewestRecipeName().get(i).getPrecipeId());
                               recipe.setPrecipeName(response.body().getNewestRecipeName().get(i).getPrecipeName());
                               recipe.setPrecipeImage(response.body().getNewestRecipeName().get(i).getPrecipeImage());
                               recipe.setPrecipeParentId(response.body().getNewestRecipeName().get(i).getPrecipeParentId());
                               recipe.setPrecipeDetail(response.body().getNewestRecipeName().get(i).getPrecipeDetail());
                               recipe.setPrecipeVideoId(response.body().getNewestRecipeName().get(i).getPrecipeVideoId());
                               Log.d("Data1",""+response.body().getNewestRecipeName().get(i).getPrecipeName());
                               Log.d("Data2",""+response.body().getNewestRecipeName().get(i).getPrecipeId());
                               Log.d("Data3",""+response.body().getNewestRecipeName().get(i).getPrecipeDetail());
                               dishList.add(recipe);
                               //filterList.add(recipe);
                           }
                           adapter.notifyDataSetChanged();
                       }else{
                           //Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                           nodishes.setVisibility(View.VISIBLE);
                       }
                   }else{
                       Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                   }
               }
               @Override
               public void onFailure(Call<NewestModel> call, Throwable t) {
                   Log.d("onFailure", t.toString());
               }
           });
       }
       adapter.notifyDataSetChanged();
   }*/
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

  /*  @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("query",""+query);
       // search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       // adapter.getFilter().filter(newText);
      *//*  newText =newText.toLowerCase();
        ArrayList<NewestRecipeName> newList=new ArrayList<>();
        for(NewestRecipeName recipe:dishList){
            String name= recipe.getPrecipeName().toLowerCase();
            if(name.contains(newText))
        newList.add(recipe);
        }
       adapter.setFilter(newList);*//*
       // adapter.getFilter().filter(newText);

        //search();
        return true;
    }*/
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
      menuInflater.inflate(R.menu.options_menu, menu);
      MenuItem menuItem=menu.findItem(R.id.searchRecipe);
      final SearchView searchView =(SearchView) MenuItemCompat.getActionView(menuItem);

      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

          @Override
          public boolean onQueryTextSubmit(String query) {
     //         Toast.makeText(getContext(), "submit", Toast.LENGTH_SHORT).show();
              query=searchView.getQuery().toString();
       //       Log.d("query",""+query);
              //search(query);
              Intent i=new Intent(getContext(), SearchActivity.class);
              i.putExtra("query",query);
              startActivity(i);
              return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
              return false;
          }
      });
      super.onCreateOptionsMenu(menu, menuInflater);
  }

    /*private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album10,
                R.drawable.album11,
               };
        RandomCardItems a = new RandomCardItems("north Indian", "13", covers[0]);
        dishList.add(a);

        a = new RandomCardItems("South Indian", "14", covers[1]);
        dishList.add(a);

        a = new RandomCardItems("East Indian", "15", covers[2]);
        dishList.add(a);

        a = new RandomCardItems("WestIndian", "16", covers[3]);
        dishList.add(a);

        a = new RandomCardItems("Middle East", "17", covers[4]);
        dishList.add(a);

        adapter.notifyDataSetChanged();
    }*/

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
public void scroll(final int offset){
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(!recyclerView.canScrollVertically(1)) {

                if (!loading) {
                    loading = true;
                    if (!checkInternetState.getInstance(getActivity()).isOnline()) {
                        Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getContext(), "Scroll", Toast.LENGTH_SHORT).show();
                        new AsyncTaskRunner(getContext(), offset).execute();
                        Log.d("scrolloffset",""+offset);
                    }
                }
            }

                /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
       Toast.makeText(getContext(), "This is Scroll", Toast.LENGTH_SHORT).show();
        if(!recyclerView.canScrollVertically(1)){
            if(!loading){
                loading=true;
                if(!checkInternetState.getInstance(getActivity()).isOnline())
                {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                }else{

                    new AsyncTaskRunner(getActivity(),offset).execute();
                }
            }
        }


    }
});*/
/*               int total = mLayoutManager.getItemCount();
               int firstVisibleItemCount = mLayoutManager.findFirstVisibleItemPosition();
               int lastVisibleItemCount = mLayoutManager.findLastVisibleItemPosition();

               //to avoid multiple calls to loadMore() method
               //maintain a boolean value (isLoading). if loadMore() task started set to true and completes set to false
               if (!loading) {
                   if (total > 0)
                       if ((total - 1) == lastVisibleItemCount){
                           new AsyncTaskRunner(getActivity(),offset).execute();//your HTTP stuff goes in this method
                           //loadingProgress.setVisibility(View.VISIBLE);
                           pDialog= ProgressDialog.show(getContext(), "ProgressDialog", "Loading");
                       }else{
                           Toast.makeText(getContext(), "Hide", Toast.LENGTH_SHORT).show();
                           //loadingProgress.setVisibility(View.GONE);
                       }

               }*/
        }
    });
};

}

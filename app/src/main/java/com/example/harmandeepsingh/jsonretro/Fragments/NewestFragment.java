package com.example.harmandeepsingh.jsonretro.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Activity.IndianActivity;
import com.example.harmandeepsingh.jsonretro.Activity.MainActivity;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.NewestCardAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.RecyclerTouchListener;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.NewestModel;
import com.example.harmandeepsingh.jsonretro.models.NewestRecipeName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 8/22/2017.
 */

public class NewestFragment extends Fragment {

    private List<NewestRecipeName> dishList;
    private RecyclerView recyclerView;
    private NewestCardAdapter adapter;
    TextView nodishes;
    ProgressDialog pDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public NewestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
       adapter = new NewestCardAdapter(getActivity(), dishList);

       recyclerView = (RecyclerView) v.findViewById(R.id.newest_recycler_view);
       RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
       recyclerView.setLayoutManager(mLayoutManager);
       recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(3), true));
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.setAdapter(adapter);
       preparenewrecipes();

       mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               preparenewrecipes();
               mSwipeRefreshLayout.setRefreshing(false);
               dishList.clear();
           }
       });
       try {
           //Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
       } catch (Exception e) {
           e.printStackTrace();
       }
       recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
           @Override
           public void onClick(View view, int position) {
               final NewestRecipeName newrecipename = dishList.get(position);
               String dishcategoryid=newrecipename.getPrecipeId();
               Intent intent=  new Intent(getActivity(),IndianActivity.class);
               intent.putExtra("ID1",dishcategoryid);
               startActivity(intent);
           }
           @Override
           public void onLongClick(View view, int position) {
           }
       }));
   }

   void preparenewrecipes(){
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

}

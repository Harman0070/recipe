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

import com.example.harmandeepsingh.jsonretro.Activity.CategoryRecipes;
import com.example.harmandeepsingh.jsonretro.Activity.MainActivity;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.IndianCardAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.RecyclerTouchListener;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.RecipeModel;
import com.example.harmandeepsingh.jsonretro.models.Recipetypename;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 8/22/2017.
 */

public class IndianFragment extends Fragment {
     List<Recipetypename> dishList1;
    private RecyclerView recyclerView;
    private IndianCardAdapter adapter1;
    TextView nodishes;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int itemid;
    String cat_id;
    ProgressDialog pDialog;
    public IndianFragment() {
        // Required empty public constructor
    }
    public IndianFragment(int itemid) {
        this.itemid=itemid;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_indian, container, false);
       // Toast.makeText(getActivity(), "Indian: "+v1, Toast.LENGTH_SHORT).show();
        initialize(v1);
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle("Indian Recipes");
        return v1;
    }
    private void initialize(View v1){
        mSwipeRefreshLayout = (SwipeRefreshLayout) v1.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        nodishes=(TextView)v1.findViewById(R.id.noDishes);
        recyclerView = (RecyclerView) v1.findViewById(R.id.indian_recycler_view);
        cat_id = "0";
        dishList1 = new ArrayList<>();
        adapter1 = new IndianCardAdapter(getActivity(), dishList1);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(3), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter1);
        prepareData();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareData();
                mSwipeRefreshLayout.setRefreshing(false);
                dishList1.clear();
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
                final Recipetypename notice = dishList1.get(position);
                String dishName =notice.getRecipeTypeName();
                String dishCategory = notice.getCategoryId();
                String dishcategoryid=notice.getCategoryId();
                String dishcategoryname=notice.getRecipeTypeName();

                Intent intent=  new Intent(getActivity(),CategoryRecipes.class);
                intent.putExtra("ID:",dishcategoryid);
                intent.putExtra("dishCategory",dishcategoryname);
                Log.d("dishcategoryname:",""+dishcategoryname);
                //intent.putExtra("dishName",dishName);
                //intent.putExtra("dishCategory",dishCategory);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
    private void prepareData(){
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
        cat_id = "1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();

            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<RecipeModel> userCall = service.getcategoryrecipes(itemid);
            userCall.enqueue(new Callback<RecipeModel>() {
                @Override
                public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response) {
                    hidepDialog();
                    Log.d("Indian"," "+response.body().getSuccess());
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            for (int i = 0; i < response.body().getRecipetypename().size(); i++) {
                                Recipetypename recipe = new Recipetypename();
                               recipe.setCountryType(response.body().getRecipetypename().get(i).getCountryType());
                               recipe.setCountryName(response.body().getRecipetypename().get(i).getCountryName());
                               recipe.setCategoryId(response.body().getRecipetypename().get(i).getCategoryId());
                               recipe.setRecipeTypeName(response.body().getRecipetypename().get(i).getRecipeTypeName());
                               recipe.setCategoryParrentId(response.body().getRecipetypename().get(i).getCategoryParrentId());
                               recipe.setRecipeImage(response.body().getRecipetypename().get(i).getRecipeImage());
                               Log.d("DataImage",""+response.body().getRecipetypename().get(i).getRecipeImage());
                               dishList1.add(recipe);
                            }
                            adapter1.notifyDataSetChanged();
                        }else{
                            //Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                            nodishes.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RecipeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        adapter1.notifyDataSetChanged();
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
   /* private void prepareData() {
        int[] covers1 = new int[]{
                R.drawable.dalmakhani,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
        };
        IndianCardItems a1 = new IndianCardItems("North Indian", "13", covers1[0]);
        dishList1.add(a1);

        a1 = new IndianCardItems("South Indian", "14", covers1[1]);
        dishList1.add(a1);

        a1 = new IndianCardItems("East Indian", "15", covers1[2]);
        dishList1.add(a1);

        a1 = new IndianCardItems("WestIndian", "16", covers1[3]);
        dishList1.add(a1);

        a1 = new IndianCardItems("Middle East", "17", covers1[4]);
        dishList1.add(a1);

        adapter1.notifyDataSetChanged();
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

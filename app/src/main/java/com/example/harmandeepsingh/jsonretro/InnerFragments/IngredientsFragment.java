package com.example.harmandeepsingh.jsonretro.InnerFragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.IngredientAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Ingre1Model;
import com.example.harmandeepsingh.jsonretro.models.IngredientDatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 8/23/2017.
 */

public class IngredientsFragment extends Fragment {
    List<IngredientDatum>dishlistingre;
    TextView dt,dn;
    IngredientAdapter adapteringre;
    RecyclerView recyclerView;
    ProgressDialog pDialog;

    public IngredientsFragment() {
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
        View v= inflater.inflate(R.layout.fragment_layout_ingredients, container, false);

        initialize(v);
        return v;
    }
  void initialize(View v){
      dishlistingre=new ArrayList<>();
      adapteringre=new IngredientAdapter(getActivity(),dishlistingre);
      dt=(TextView)v.findViewById(R.id.dishDetail);
      recyclerView=(RecyclerView)v.findViewById(R.id.ingredient_recycler_view);
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(mLayoutManager);
      //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(adapteringre);
      prepareingredients();
  }
  void prepareingredients(){
      pDialog=new ProgressDialog(getContext());
      pDialog.setMessage("fetching data...");
      pDialog.setCancelable(false);
      String str1 = getActivity().getIntent().getStringExtra("ID1");
      String recipename = getActivity().getIntent().getStringExtra("recipename");
      getActivity().setTitle(recipename);
      getActivity().setTitleColor(Color.parseColor("#ffffff"));
      Log.d("recipename",""+recipename);
      //cat1_id = 7;
      showpDialog();
      //check internet state
      if (!checkInternetState.getInstance(getActivity()).isOnline()) {
          hidepDialog();

          Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

      }else {

          APIService service = ApiClient.getClient().create(APIService.class);
          Call<Ingre1Model> userCall = service.getingredients(str1);
          userCall.enqueue(new Callback<Ingre1Model>() {
              @Override
              public void onResponse(Call<Ingre1Model> call, Response<Ingre1Model> response) {
                  hidepDialog();
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
                          Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                      }
                  }else{
                      Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
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

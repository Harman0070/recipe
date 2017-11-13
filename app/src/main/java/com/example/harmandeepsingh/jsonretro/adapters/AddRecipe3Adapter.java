package com.example.harmandeepsingh.jsonretro.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Ingre1Model;
import com.example.harmandeepsingh.jsonretro.models.Step;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddRecipe3Adapter extends RecyclerView.Adapter<AddRecipe3Adapter.MyViewHolder> {
    private Context mContext;
    private List<Step> stepList;

    ProgressDialog pDialog;
    Step CategoryRecipesCardItems;
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dishsteps;
        ImageButton imagebtDelete;
        //String id;

        public MyViewHolder(View view) {
            super(view);

            dishsteps = (TextView) view.findViewById(R.id.addSteps);
            imagebtDelete=(ImageButton)view.findViewById(R.id.imagebtDelete);
        }
    }
    public AddRecipe3Adapter(Context mContext, List<Step> dishsteps) {
        this.mContext = mContext;
        this.stepList = dishsteps;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_card_addrecipe3, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CategoryRecipesCardItems=stepList.get(position);

        holder.dishsteps.setText(CategoryRecipesCardItems.getStepDetail());

        holder.imagebtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String id=CategoryRecipesCardItems.getIngId();
                //alertdialog(CategoryRecipesCardItems.getStepId(),position);
                alertdialog(stepList.get(position).getStepId());

                Log.d("stepcard"," "+stepList.get(position).getStepId());
                // dishListingre.remove(position);
                //notifyDataSetChanged();
            }
        });
        Toast.makeText(mContext, ""+CategoryRecipesCardItems.getStepId(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return stepList.size();
    }
    public void alertdialog(final String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage("Are you Sure?");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                deletefromDatabase(id);
                Log.d("idcard3:",""+id);

                //  hidepDialog();
                //deletefromDatabase();
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

    public void deletefromDatabase(String id){
        pDialog=new ProgressDialog(mContext);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);

        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(mContext).isOnline()) {
            hidepDialog();

            Toast.makeText(mContext, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {
            Log.d("deleteid:",""+id);
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Ingre1Model> userCall = service.getingredientid(id);
            userCall.enqueue(new Callback<Ingre1Model>() {
                @Override
                public void onResponse(Call<Ingre1Model> call, Response<Ingre1Model> response) {
                    hidepDialog();

                    Log.d("Indian"," "+response.body().getSuccess());
                    Log.d("DAta",""+response);
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            //dishListingre.remove(position);
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "Swipe To Refresh", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mContext, "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Ingre1Model> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        notifyDataSetChanged();
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

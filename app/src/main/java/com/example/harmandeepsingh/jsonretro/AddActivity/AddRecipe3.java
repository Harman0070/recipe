package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecipe3 extends AppCompatActivity {
    Button addstepsBt;
    EditText steps;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe3);
        addstepsBt=(Button)findViewById(R.id.addstepsBt);
        addstepsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog();
            }
        });
    }
    public void alertdialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.promptsstep, null);
        dialogBuilder.setView(dialogView);

        steps = (EditText) dialogView.findViewById(R.id.addSteps);


        dialogBuilder.setTitle("Add Here");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               addstepstodatabase(steps.getText().toString());
                hidepDialog();
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
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(true);

        //String cat_id =getIntent().getStringExtra("cat_id");
        String step_parent_id="7";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();
            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<Addcountrysearch2Model> userCall = service.addrecipe3data(step_parent_id,steps);
            userCall.enqueue(new Callback<Addcountrysearch2Model>() {
                @Override
                public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                   
                    // Log.d("spinnerresponse"," "+response.body().getSuccess());
                    // Toast.makeText(getApplication(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

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

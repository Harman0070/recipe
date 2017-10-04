package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecipe1 extends Activity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    ProgressDialog pDialog;
    EditText addRecipeName,addRecipeDetail,addVideoId,addTime,addServings;
    ImageView imageView;
    String cat_id;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe1);
       // Intent i=getIntent();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        addRecipeName=(EditText)findViewById(R.id.addRecipeName);
        addRecipeDetail=(EditText)findViewById(R.id.addRecipeDetail);
        addVideoId=(EditText)findViewById(R.id.addVideoId);
        addTime=(EditText)findViewById(R.id.addTime);
        addServings=(EditText)findViewById(R.id.addServings);
        imageView=(ImageView)findViewById(R.id.addImage);
        fab = (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                String recipeName = addRecipeName.getText().toString();
                String recipeDetail = addRecipeDetail.getText().toString();
                String videoId = addVideoId.getText().toString();
                String time = addTime.getText().toString();
                String servings = addServings.getText().toString();
                 Drawable image=imageView.getDrawable();
              //  if (validate(recipeName, recipeDetail, videoId, time, servings,image))
                if(true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipe1.this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Are you Sure?");
                    // builder.setIcon(R.drawable.ic_launcher);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                           // startActivity(new Intent(getApplication(), AddRecipe2.class));
                            addtodatabase(addRecipeName.getText().toString(),addRecipeDetail.getText().toString(), addVideoId.getText().toString(), addTime.getText().toString(), addServings.getText().toString(),imageView.getDrawable());
                            Intent i=new Intent(getApplication(), AddRecipe2.class);
                            i.putExtra("cat_id_addrecipe1",cat_id);
                            startActivity(i);
                            Log.d("cat_id_addrecipe1",""+cat_id);

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        Button addbutton = (Button) findViewById(R.id.addbt);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    private boolean validate(String recipeName,String recipeDetail,String videoId,String time,String servings,Drawable image) {
        if (recipeName.equals("")) {
            addRecipeName.setError("Data is null or short");
            addRecipeName.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Recipe Name is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (recipeDetail.equals("")) {
            addRecipeDetail.setError("Data is null or short");
            addRecipeDetail.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Details are empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (videoId.equals("")) {
            addVideoId.setError("Data is null or short");
            addVideoId.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Video ID is not given", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (time.equals("")) {
            addTime.setError("Data is null or short");
            addTime.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Time is not given", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (servings.equals("")) {
            addServings.setError("Data is null or short");
            addServings.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Servings are not written", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (image.equals("")) {
            /*addServings.setError("Data is null or short");
            addServings.setFocusable(true);
            */Toast.makeText(getApplicationContext(), "Image is not selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView = (ImageView) findViewById(R.id.addImage);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
   public void addtodatabase(String getname,String getdetail,String getvideoid,String gettime,String getservings,Drawable imageview){
       pDialog=new ProgressDialog(this);
       pDialog.setMessage("Adding data...");
       pDialog.setCancelable(false);

        cat_id =getIntent().getStringExtra("cat_id");
       Log.d("cat_id_addrecipe1data",""+cat_id);
       showpDialog();
       //check internet state
       if (!checkInternetState.getInstance(this).isOnline()) {
           hidepDialog();
           Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

       }else {

           APIService service = ApiClient.getClient().create(APIService.class);
           Call<Addcountrysearch2Model> userCall = service.addData(cat_id,getname,getdetail,getvideoid,gettime,getservings,imageview);
           userCall.enqueue(new Callback<Addcountrysearch2Model>() {
               @Override
               public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                   hidepDialog();
                   // Log.d("spinnerresponse"," "+response.body().getSuccess());
                   Toast.makeText(getApplication(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                   if (response.isSuccessful()) {

                      // startActivity(new Intent(getApplication(), AddRecipe2.class));
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
       // preparecountry(m);
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
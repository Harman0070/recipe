package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.CategoryRecipesCardAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.PrefManager;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;
import com.example.harmandeepsingh.jsonretro.models.RecipeparticularDatum;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class AddRecipe1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NumberPicker.OnValueChangeListener, ProgressRequestBody.UploadCallbacks {

    FloatingActionButton fab;
    EditText addRecipeName,addRecipeDetail,addVideoId,addServings;
    ImageView imageView;
    String cat_id;
    Button btnMinute;
    TextView txtMinute,txtSecond;
    String recipe_id,recipeName;
    CategoryRecipesCardAdapter adapter;
    TextView noCategoryDishes;
    String selectedImage;
    private List<RecipeparticularDatum> dishList;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedImage ="";

        txtMinute=(TextView)findViewById(R.id.in_minutes);
        txtSecond=(TextView)findViewById(R.id.in_second);

        btnMinute=(Button)findViewById(R.id.btn_minute);
        btnMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddRecipe1.this, "Minute", Toast.LENGTH_SHORT).show();
                shownumber();
            }
        });

        addRecipeName=(EditText)findViewById(R.id.addRecipeName);
        addRecipeDetail=(EditText)findViewById(R.id.addRecipeDetail);
        addVideoId=(EditText)findViewById(R.id.addVideoId);
        addServings=(EditText)findViewById(R.id.addServings);

        imageView=(ImageView)findViewById(R.id.addImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedImage.equals("")) {
                    Log.d("selectedImage123",""+selectedImage);
                    recipeName = addRecipeName.getText().toString();
                    String m = txtMinute.getText().toString();
                    String recipeDetail = addRecipeDetail.getText().toString();
                    String videoId = addVideoId.getText().toString();
                    String servings = addServings.getText().toString();

                    if (validate(recipeName, recipeDetail, videoId, m, servings, selectedImage)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipe1.this);
                        builder.setTitle(R.string.app_name);
                        //builder.setMessage("Are you Sure?");
                        // builder.setIcon(R.drawable.ic_launcher);
                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                int mi = (Integer.parseInt(txtMinute.getText().toString()));
                                int se = (Integer.parseInt(txtSecond.getText().toString()));
                                String min = ((mi < 10) ? ("0" + mi) : (" " + mi)) + ":" + ((se < 10) ? ("0" + se) : (" " + se));

                                uploadFile(addRecipeName.getText().toString(), addRecipeDetail.getText().toString(), addVideoId.getText().toString(), min, addServings.getText().toString(), selectedImage);
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                } else {
                    Toast.makeText(getApplication(), "Some Details are missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

        Log.i("value is",""+i1);
    }

    public void shownumber()
    {
        final Dialog d = new Dialog(AddRecipe1.this);
        d.setTitle("HH:MM:SS");
        d.setContentView(R.layout.dialog_time);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker np2 = (NumberPicker) d.findViewById(R.id.numberPicker2);
        np2.setMaxValue(59); // max value 100
        np2.setMinValue(0);   // min value 0
        np2.setWrapSelectorWheel(false);
        np2.setOnValueChangedListener(this);

        final NumberPicker np3 = (NumberPicker) d.findViewById(R.id.numberPicker3);
        np3.setMaxValue(59); // max value 100
        np3.setMinValue(0);   // min value 0
        np3.setWrapSelectorWheel(false);
        np3.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
              //  txtHour.setText(valueOf(np1.getValue()));
                txtMinute.setText(valueOf(np2.getValue()));//set the value to textview
                txtSecond.setText(valueOf(np3.getValue()));
                //Toast.makeText(AddRecipe1.this, ""+String.valueOf(np.getValue()), Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    private boolean validate(String recipeName,String recipeDetail,String videoId,String time,String servings,String image) {
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
        if (videoId.equals("")&&videoId.equals("'%(?:youtube(?:-nocookie)?\\.com/(?:[^/]+/.+/|(?:v|e(?:mbed)?)/|.*[?&]v=)|youtu\\.be/)([^\"&?/ ]{11})%i'")) {
            addVideoId.setError("Data is null or short");
            addVideoId.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Video ID is not given", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (time.equals("")) {
            txtSecond.setError("Data is null or short");
            txtSecond.setFocusable(true);
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
            addRecipeDetail.setError("Image not set");
            addRecipeDetail.setFocusable(true);
            Toast.makeText(getApplicationContext(), "Details are empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = getRealPathFromURI(data.getData());
            imageView = (ImageView) findViewById(R.id.addImage);
            imageView.setImageURI(Uri.parse(selectedImage.toString()));
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
            Log.d("URLresult1",""+result);
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            result = cursor.getString(idx);
            Log.d("URLresult2",""+result);
            cursor.close();
        }
        return result;
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

    private void uploadFile(final String getname,final String getdetail,final String getvideoid,final String gettime,final String getservings,final String filepath){
        final ProgressDialog pd =new ProgressDialog(AddRecipe1.this);
        pd.setMessage("Adding Data");
        pd.setCancelable(false);
        pd.show();

        File file = new File(filepath);
        ProgressRequestBody fileBody = new ProgressRequestBody(file,this);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("precipe_image", file.getName(), fileBody);
        RequestBody Ruser_id = RequestBody.create(MediaType.parse("multipart/form-data"), getUserid());
        RequestBody RName = RequestBody.create(MediaType.parse("multipart/form-data"), getname);
        RequestBody RDetail = RequestBody.create(MediaType.parse("multipart/form-data"), getdetail);
        RequestBody RVideoID = RequestBody.create(MediaType.parse("multipart/form-data"), getvideoid);
        RequestBody RTime = RequestBody.create(MediaType.parse("multipart/form-data"), gettime);
        RequestBody RServing = RequestBody.create(MediaType.parse("multipart/form-data"), getservings);
        RequestBody RCatID = RequestBody.create(MediaType.parse("multipart/form-data"), getIntent().getStringExtra("cat_id"));

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<Addcountrysearch2Model> call = service.uploadimage(fileToUpload,Ruser_id,RName,RDetail,RVideoID,RTime,RServing,RCatID);
        call.enqueue(new Callback<Addcountrysearch2Model>() {
            @Override
            public void onResponse(Call<Addcountrysearch2Model> call, Response<Addcountrysearch2Model> response) {
                pd.hide();
                if (response.body().getSuccess()) {

                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent i2=new Intent(AddRecipe1.this, AddRecipe2.class);

                    recipe_id=""+response.body().getRecipe_id();
                    i2.putExtra("recipe_id",recipe_id);

                    recipeName = addRecipeName.getText().toString();
                    i2.putExtra("recipe_name",recipeName);
                    startActivity(i2);
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    Log.e("Message",response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Addcountrysearch2Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
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
    private String getUserid() {
        return new PrefManager(getApplicationContext()).getUserId();
    }

    @Override
    public void onProgressUpdate(int percentage) {
    }

    @Override
    public void onError() {
    }

    @Override
    public void onFinish() {
    }
}
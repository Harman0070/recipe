package com.example.harmandeepsingh.jsonretro.InnerFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.MyBounceInterpolator;
import com.example.harmandeepsingh.jsonretro.helperclasses.PrefManager;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.LikeModel;
import com.example.harmandeepsingh.jsonretro.models.Noofrowsmodel;
import com.example.harmandeepsingh.jsonretro.models.TimeModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.harmandeepsingh.jsonretro.R.id.backdrop;
import static com.example.harmandeepsingh.jsonretro.helperclasses.Utils.webUrl;

/**
 * Created by Harmandeep singh on 9/6/2017.
 */

public class IntroFragment extends Fragment {
TextView dishdetail,time,servings,likes;
   ImageView image1;
    ToggleButton ToggleButton;
    ProgressDialog pDialog;
    public RatingBar ratingBar;
    private int count;
    Boolean success;
    CoordinatorLayout mCoordinatorLayout;
    CollapsingToolbarLayout collapsingToolbar;

    public IntroFragment() {
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
        View v= inflater.inflate(R.layout.intro_layout, container, false);
        initialize(v);
        count();
        return v;
    }
   void count(){
       String recipe_id = getActivity().getIntent().getStringExtra("ID1");
       if (!checkInternetState.getInstance(getActivity()).isOnline()) {
           hidepDialog();
           Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
       }else {
           final APIService service = ApiClient.getClient().create(APIService.class);
           Call<Noofrowsmodel> userCall = service.getnooflikes(getUserid(),recipe_id);
           userCall.enqueue(new Callback<Noofrowsmodel>() {
               @Override
               public void onResponse(Call<Noofrowsmodel> call, Response<Noofrowsmodel> response) {
                   hidepDialog();
                   if (response.isSuccessful()){
                       ToggleButton.setChecked(response.body().getSuccess());
                       //Toast.makeText(getActivity(), ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                       Log.e("DAta",""+response.body().getSuccess());
                       likes.setText(""+response.body().getTotalikes());

                       Log.d("Total Likes",""+response.body().getTotalikes());
                   }else{
                       Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                   }
               }
               @Override
               public void onFailure(Call<Noofrowsmodel> call, Throwable t) {
                   //hidepDialog();
                   Log.d("onFailure", t.toString());
               }
           });
        }
   }

    void initialize(final View v){
        final Toolbar myToolbar = (Toolbar)v. findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar=(CollapsingToolbarLayout)v.findViewById(R.id.collapsingToolbar1);
        mCoordinatorLayout = (CoordinatorLayout)v.findViewById(R.id.coordinatorLayout1);
        collapsingToolbar.setTitleEnabled(true);
     //   ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        image1=(ImageView)v.findViewById(backdrop);
        dishdetail=(TextView)v.findViewById(R.id.dishDetail);
        time=(TextView)v.findViewById(R.id.time);
        servings=(TextView)v.findViewById(R.id.servings);
        likes=(TextView)v.findViewById(R.id.likes);
        ToggleButton=(ToggleButton)v.findViewById(R.id.tb);
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        prepareintro();

        ToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("State",""+ToggleButton.isChecked());
                ToggleButton.startAnimation(myAnim);
                updatelikeindatabase(ToggleButton.isChecked());
              //  initialize(v);
                count();
                //Toast.makeText(getActivity(), "Updating State... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
   /* public void didTapButton(View view) {
        Button button = (Button)findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        button.startAnimation(myAnim);
    }*/
   /*public void rateMe(View view){

       Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
   }*/
    void prepareintro(){
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
       String str1 = getActivity().getIntent().getStringExtra("ID1");
        //String str2 = getActivity().getIntent().getStringExtra("position");
        Log.d("CheckintroparentID:",""+str1);
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }else {
            final APIService service = ApiClient.getClient().create(APIService.class);
            Call<TimeModel> userCall = service.getpreptime(str1);
            userCall.enqueue(new Callback<TimeModel>() {
                @Override
                public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                    hidepDialog();

                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                           String image=webUrl+response.body().getTimedetails().getPrecipeImage();
                            Glide.with(getActivity()).load(image).into(image1);
                            collapsingToolbar.setTitle(response.body().getTimedetails().getPrecipeName());
                            dishdetail.setText(response.body().getTimedetails().getPrecipeDetail());
                            time.setText(response.body().getTimedetails().getTime());
                            servings.setText(response.body().getTimedetails().getServings());
                       ;}else{
                            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TimeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
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

    void updatelikeindatabase(boolean checked){
        String str = getActivity().getIntent().getStringExtra("ID1");
            if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();

            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {
            final APIService service = ApiClient.getClient().create(APIService.class);
            Call<LikeModel> userCall = service.getlikesid(getUserid(),str,"like",checked);
            userCall.enqueue(new Callback<LikeModel>() {

                @Override
                public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                    hidepDialog();

                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            // Toast.makeText(getActivity(), "liked", Toast.LENGTH_SHORT).show();
                        }else{
                           // Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LikeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
    }
    private String getUserid() {
        return new PrefManager(getActivity()).getUserId();
    }
}

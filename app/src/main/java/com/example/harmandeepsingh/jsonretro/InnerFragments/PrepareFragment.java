package com.example.harmandeepsingh.jsonretro.InnerFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Activity.Config;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.adapters.PrepareAdapter;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.Step;
import com.example.harmandeepsingh.jsonretro.models.StepsprepareModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 8/23/2017.
 */

public class PrepareFragment extends Fragment {
    private YouTubePlayer YPlayer;
    ProgressDialog pDialog;
    List<Step> stepList;
    RecyclerView recyclerView;
    PrepareAdapter adapterprepare;
    String par_id;
    String You;

    public PrepareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prepare, container, false);
        initialize(rootView);
        return rootView;

    }
    void initialize(View rootView){
        stepList=new ArrayList<>();
        adapterprepare=new PrepareAdapter(getActivity(),stepList);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.prepare_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterprepare);

        //YoutubeView
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_view, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(Config.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer=youTubePlayer;
                    //YPlayer.setFullscreen(true);
                    YPlayer.loadVideo(You);
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        preparesteps();
    }
    void preparesteps(){
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);

        String str = getActivity().getIntent().getStringExtra("ID1");
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(getActivity()).isOnline()) {
            hidepDialog();

            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            Log.d("DataLog",""+par_id);
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<StepsprepareModel> userCall = service.getsteps(str);
            userCall.enqueue(new Callback<StepsprepareModel>() {
                @Override
                public void onResponse(Call<StepsprepareModel> call, Response<StepsprepareModel> response) {
                    hidepDialog();

                    Log.d("Indian"," "+response.body().getSuccess());
                    Log.d("DAta",""+response);
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            You=(response.body().getVideoID().getPrecipeVideoId());
                            Log.d("Video ID"," "+response.body().getVideoID().getPrecipeVideoId());
                            for (int i = 0; i < response.body().getSteps().size(); i++) {
                                Step recipe = new Step();
                                recipe.setStepId(""+(i+1));
                                recipe.setStepParentId(response.body().getSteps().get(i).getStepParentId());
                                recipe.setStepDetail(response.body().getSteps().get(i).getStepDetail());
                                Log.d("Step Id",""+response.body().getSteps().get(i).getStepId());
                                stepList.add(recipe);
                            }

                            adapterprepare.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StepsprepareModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        adapterprepare.notifyDataSetChanged();
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


 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_prepare, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_view, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("DEVELOPER_KEY", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(true);
                    YPlayer.loadVideo("2zNSgSzhBfM");
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
    }
*/

   /* @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if (!b) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
           // int getLastPosition = getIntent().getIntExtra("Value", 0);
           // Toast.makeText(this, ""+getLastPosition, Toast.LENGTH_SHORT).show();
            youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//            if (getLastPosition == 0) {
//                youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE);
//                // Hiding player controls
//                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//            } else if (getLastPosition == 1) {
//                youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE1);
//                // Hiding player controls
//                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//            } else if (getLastPosition == 2) {
//                youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE2);
//                // Hiding player controls
//                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//
//            } else if (getLastPosition == 3) {
//                youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE3);
//                // Hiding player controls
//                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
   *//*     if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }*//*
    }
*//*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }*/





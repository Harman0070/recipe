package com.example.harmandeepsingh.jsonretro.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.AddActivity.SearchContainerActivity;
import com.example.harmandeepsingh.jsonretro.Fragments.IndianFragment;
import com.example.harmandeepsingh.jsonretro.Fragments.My_Recipes_Fragment;
import com.example.harmandeepsingh.jsonretro.Fragments.NewestFragment;
import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.PrefManager;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.CountryrecipeModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //String CURRENT_TAG = TAG_HOME;

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    String[] activityTitles;
    FloatingActionButton fab;
    ProgressDialog pDialog;
    public Menu m;
    String con_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getUseremail().equals("")||getUserid().equals("")){
            //Toast.makeText(this, "Email:  "+getUseremail(), Toast.LENGTH_SHORT).show();
            Intent i=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Intent i=new Intent(MainActivity.this,SearchContainerActivity.class);
                startActivity(i);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        int color = Color.rgb(45,153,247);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        m = navigationView.getMenu();
        preparecountry(m);
      /*  MenuItem nav_camara = m.findItem(R.id.nav_newest);
        nav_camara.setTitle("New Recipes");*/
        navigationView.setNavigationItemSelectedListener(this);

       // m.add(0, 0, 0, "SriLankan");
       // m.add(0, 1, 0, "American");
        View header = navigationView.getHeaderView(0);
        header.setBackgroundColor(color);

        displaySelectedScreen(R.id.nav_newest);
    }

    public void preparecountry(Menu m1){
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
       //con_id ="1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(this).isOnline()) {
            hidepDialog();

            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<CountryrecipeModel> userCall = service.getcountryid("");
            userCall.enqueue(new Callback<CountryrecipeModel>() {
                @Override
                public void onResponse(Call<CountryrecipeModel> call, Response<CountryrecipeModel> response) {
                    hidepDialog();
                    Log.d("Indian"," "+response.body().getSuccess());
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {

                            for (int i = 0; i < response.body().getCountryName().size(); i++) {
                                int j=Integer.parseInt(response.body().getCountryName().get(i).getCountryType());
                               m.add(0,j,0,response.body().getCountryName().get(i).getCountryName());
                               // Toast.makeText(MainActivity.this, ""+response.body().getCountryName().get(i).getCountryName(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            //Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                            //nodishes.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CountryrecipeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
       // preparecountry(m);
        //adapter.notifyDataSetChanged();
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
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    private String getUseremail() {
   return new PrefManager(this).getUserEmail();
    }
    private String getUserid() {
        return new PrefManager(this).getUserId();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displayscreen(item.getItemId());
        displaySelectedScreen(item.getItemId());

        // Handle navigation view item clicks here.
        return true;
    }
   void displayscreen(int itemId) {
       //Toast.makeText(this, "item= "+itemId, Toast.LENGTH_SHORT).show();
       IndianFragment fragment=null;
       fragment=new IndianFragment(itemId);
       //replacing the fragment
       if (fragment!= null) {
           FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           ft.replace(R.id.content_frame, fragment);
           ft.commit();
       }
       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
   }

    private void displaySelectedScreen(int itemId){
        //creating fragment object
        Fragment fragment = null;
        //Toast.makeText(this, ""+itemId, Toast.LENGTH_SHORT).show();
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_newest:
               fragment = new NewestFragment();
                break;

           case R.id.nav_myrecipes:
                fragment = new My_Recipes_Fragment();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /*
    public void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
       *//* if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }*//*

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
       *//* if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
*//*
        // show or hide the fab button
        // toggleFab();

        //Closing drawer on item click
       *//* drawer.closeDrawers();*//*

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // random


            case 1:
                // Indian fragment
                IndianFragment indianFragment = new IndianFragment();
                return indianFragment;
            case 2:
                // Chinese fragment
                ChineseFragment chineseFragment = new ChineseFragment();
                return chineseFragment;

            case 3:
                // French fragment
                FrenchFragment frenchFragment = new FrenchFragment();
                return frenchFragment;
            case 4:
                // Italian fragment
                ItalianFragment italianFragment = new ItalianFragment();
                return italianFragment;
            case 5:
                // support fragment
                // support fragment
                SupportFragment supportFragment = new SupportFragment();
                return supportFragment;

            default:
                return new newestFragment();
        }
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);

    private void setToolbarTitle() {
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }*/
}

package com.example.harmandeepsingh.jsonretro.AddActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.harmandeepsingh.jsonretro.AddFragments.Search1;
import com.example.harmandeepsingh.jsonretro.R;

public class SearchContainerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    /*@Override
    public void onBackPressed() {
        // do something on back.
        finish();
        return;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_container);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Search1 llf = new Search1();
        ft.replace(R.id.listFragment, llf);
        ft.commit();
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
          // onBackPressed();
            popBackStack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
 /*   @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() == 1 ) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }*/
    public void popBackStack(){
        finish();
    }
}

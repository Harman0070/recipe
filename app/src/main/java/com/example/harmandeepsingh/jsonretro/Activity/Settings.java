package com.example.harmandeepsingh.jsonretro.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.PrefManager;

public class Settings extends AppCompatActivity {
    CardView logoutBt,aboutUs;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView designationTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Toolbar settingsToolbar= (Toolbar)findViewById(R.id.settingsToolbar);
        setSupportActionBar(settingsToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sharedPreferences=getApplicationContext().getSharedPreferences("LoginDetails",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        setContentView(R.layout.activity_settings);

        designationTextview=(TextView)findViewById(R.id.designationTextview);
        designationTextview.setText(getUseremail());

        logoutBt =(CardView)findViewById(R.id.card_view1);
        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove("alreadyloggedin");
                editor.commit();
                Intent intent=new Intent(Settings.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        aboutUs=(CardView)findViewById(R.id.card_view2);
        aboutUs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Settings.this,AboutUs.class));
        }
    });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
// finish the activity
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getUseremail() {
        return new PrefManager(this).getUserEmail();
    }
}

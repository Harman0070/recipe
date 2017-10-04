
package com.example.harmandeepsingh.jsonretro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.SubjectsDetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    EditText fname,lname,email1,password;
    Button registerbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email1 = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        registerbt = (Button) findViewById(R.id.registerbt);

        registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SignUp.this, "Signup", Toast.LENGTH_SHORT).show();
                String Firstname = fname.getText().toString();
                String Lastname = lname.getText().toString();
                String email = email1.getText().toString();
                String Password = password.getText().toString();

                if(validate(Firstname, Lastname, email, Password)) {
                    prepareAlbums(Firstname, Lastname, email, Password);
                }
            }
        });
    }

    private boolean validate(String firstname, String lastname, String email, String password1) {
        if (firstname.equals("") || firstname.length()<3){
            //Toast.makeText(this, ""+firstname.length(), Toast.LENGTH_SHORT).show();
            fname.setError("Data is null or short");
            fname.setFocusable(true);
            return false;
        }

        if (lastname.equals("") || lastname.length()<3){
            lname.setError("Data is null or short");
            lname.setFocusable(true);
            return false;
        }
        if (email.equals("") || !validateEmailAddress(email)){
            email1.setError("Email not valid");
            email1.setFocusable(true);
            return false;
        }
        if (password1.equals("") || password1.length()<6){
            password.setError("Password not Valid");
            email1.setFocusable(true);
            return false;
        }

        return true;
    }

    private boolean validateEmailAddress(String emailAddress){
        String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private void prepareAlbums(String firstname, String lastname, String email, String password) {
        if (!checkInternetState.getInstance(SignUp.this).isOnline()) {

            Toast.makeText(SignUp.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        } else {
            APIService service = ApiClient.getClient().create(APIService.class);
            //  Toast.makeText(this, "Service: "+service, Toast.LENGTH_SHORT).show();
            Call<SubjectsDetails> userCall = service.getUserDetails(firstname,lastname,email,password);
            userCall.enqueue(new Callback<SubjectsDetails>() {
                @Override
                public void onResponse(Call<SubjectsDetails> call, Response<SubjectsDetails> response) {

                    if (response.isSuccessful()) {
                        Intent i=new Intent(SignUp.this,LoginActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<SubjectsDetails> call, Throwable t) {

                    Log.d("onFailure", t.toString());
                }
            });
        }
    }
   /* private String getParentLoginDetails(){
        return new PrefManager(SignUp.this).getParentLoginDetails();
    }
    private String sharedKey()
    {
        return new PrefManager(SignUp.this).getParentKey();
    }
    private int sharedId() {
        return new PrefManager(SignUp.this).getParentId();
    }*/
}

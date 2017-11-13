package com.example.harmandeepsingh.jsonretro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.jsonretro.Interface.APIService;
import com.example.harmandeepsingh.jsonretro.Interface.ApiClient;
import com.example.harmandeepsingh.jsonretro.R;
import com.example.harmandeepsingh.jsonretro.helperclasses.PrefManager;
import com.example.harmandeepsingh.jsonretro.helperclasses.checkInternetState;
import com.example.harmandeepsingh.jsonretro.models.UserLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText emailEdt,passwordEdt;
    TextView signupText;
    Button loginBt;
    LinearLayout loginLayout;
    CheckBox show_hide_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEdt=(EditText)findViewById(R.id.email);
        passwordEdt=(EditText)findViewById(R.id.password);
        loginBt=(Button) findViewById(R.id.loginbt);
        signupText=(TextView)findViewById(R.id.signupText);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        show_hide_password=(CheckBox)findViewById(R.id.show_hide_password);

        show_hide_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordEdt.setSelection(passwordEdt.length());
*/
                passwordEdt.setTransformationMethod(new PasswordTransformationMethod());
                passwordEdt.setTransformationMethod(null);
            }
        });
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();

                String Email = emailEdt.getText().toString();
                String Password = passwordEdt.getText().toString();
                if(validate(Email,Password)) {
                    loginUser(Email,Password);
                }
            }
        });


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class );
                startActivity(intent);
            }
        });


    }
   private Boolean validate(String email ,String password){
       boolean valid = true;

       if (email.isEmpty() || !validateEmailAddress(email)) {
           emailEdt.requestFocus();
           emailEdt.setError("enter a valid email address");
           valid = false;
       } else {
           emailEdt.setError(null);
       }

       if (password.isEmpty()) {
           ///passwordEdt.setError("Password is empty");
           requestFocus(passwordEdt);
           valid = false;
       } else {
           passwordEdt.setError(null);
       }
       return valid;
   }
    private boolean validateEmailAddress(String emailAddress){
        String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void loginUser( String email, String password) {
        if (!checkInternetState.getInstance(LoginActivity.this).isOnline()) {

            Toast.makeText(LoginActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        } else {
            APIService service = ApiClient.getClient().create(APIService.class);
            // Toast.makeText(this, "Service: "+service, Toast.LENGTH_SHORT).show();
            Call<UserLogin> userCall = service.getLoginDetsils(email,password);
            userCall.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                    if (response.body().getSuccess().equals("1")) {
                            saveLoginDetailsUser(response.body().getEmail(),response.body().getUserid() );
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(i);
                    }else{
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {

                    Log.d("onFailure", t.toString());
                }
            });
        }
    }
    private void saveLoginDetailsUser(String email, String userid) {
        new PrefManager(this).saveLoginDetails(email , userid);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}


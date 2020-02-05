package com.siddarthglobalschool.ui.login;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.RequestUserPermission;
import com.siddarthglobalschool.Util.UtilMethods;

public class LoginActivity extends AppCompatActivity {

   
     EditText usernameEditText,passwordEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
RequestUserPermission requestUserPermission=new RequestUserPermission(this);
requestUserPermission.verifyStoragePermissions();
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);



        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                     }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    CustomLoader loader = new CustomLoader(LoginActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

                    if(UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)){
                    UtilMethods.INSTANCE.Login(LoginActivity.this,usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),loader);
                }
                else{
                    UtilMethods.INSTANCE.dialogOk(LoginActivity.this,"Attention!","No Internet Connection");
                }
                } else {
                }
            }
        });
    }

    private boolean validate() {
        int i=0;
        if(usernameEditText.getText().toString().isEmpty()){
            usernameEditText.setError("Enter EnrolmentNo");
            i=1;
        }   if(passwordEditText.getText().toString().isEmpty()){
            passwordEditText.setError("Enter Password");
            i=1;
        }
        if(i==1)
            return false;
        else
            return true;
    }



}

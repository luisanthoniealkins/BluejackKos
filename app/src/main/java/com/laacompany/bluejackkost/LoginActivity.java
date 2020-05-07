package com.laacompany.bluejackkost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mETUsername,mETPassword;


    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, LoginActivity.class);
        return intent;
    }

    private void init(){
        mETUsername = findViewById(R.id.id_login_edt_username);
        mETPassword = findViewById(R.id.id_login_edt_password);
        
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    public void clickLogin(View view) {

    }

    public void clickRegister(View view) {

    }
}

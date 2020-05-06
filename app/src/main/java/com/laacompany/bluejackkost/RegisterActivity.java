package com.laacompany.bluejackkost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText mETUsername,mETPhone, mETPassword,mETCPassword;
    private RadioButton mRBMale;
    private CheckBox mCBTerms;

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, RegisterActivity.class);
        return intent;
    }

    private void init(){
        mETUsername = findViewById(R.id.id_register_edt_username);
        mETPhone = findViewById(R.id.id_register_edt_phone);
        mETPassword = findViewById(R.id.id_register_edt_password);
        mETCPassword = findViewById(R.id.id_register_edt_cpassword);
        mRBMale = findViewById(R.id.id_register_rb_male);
        mCBTerms = findViewById(R.id.id_register_cb_terms);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    public void clickDOB(View view) {

    }

    public void clickRegister(View view) {

    }
}

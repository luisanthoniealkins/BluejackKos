package com.laacompany.bluejackkost;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.laacompany.bluejackkost.Handle.Handler;
import com.laacompany.bluejackkost.ObjectClass.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText mETUsername, mETPhone, mETPassword, mETCPassword;
    private RadioButton mRBMale;
    private CheckBox mCBTerms;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegisterActivity.class);
        return intent;
    }

    private void init() {
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
        String username = mETUsername.getText().toString();
        String password = mETPassword.getText().toString();
        String cpassword = mETCPassword.getText().toString();
        String phone = mETPhone.getText().toString();
        String dob = "20/03/2000";
        boolean isMale = mRBMale.isChecked(), isTermCross = mCBTerms.isChecked();


        /*
        VALIDATION GOES HERE (RETURN; IF ERROR FOUND)



         */

        String user_id = Handler.getUserID();
        Handler.sUsers.add(new User(user_id, username, password, phone, dob, (isMale) ? "Male" : "Female"));
        Handler.insertUser(new User(user_id, username, password, phone, dob, (isMale) ? "Male" : "Female"));

        String sms_message = "Registration Successful!";
        SmsManager.getDefault().sendTextMessage(phone,null, sms_message, null, null);
//        SmsManager.getSmsManagerForSubscriptionId( SubscriptionManager.from(this).getActiveSubscriptionInfoList().get(1).getSubscriptionId()).sendTextMessage(phone, "123", sms_message, null, null);
        finish();
    }

}

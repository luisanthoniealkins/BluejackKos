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
        boolean isMale = mRBMale.isChecked(), isTermCross = mCBTerms.isChecked(), valid = true;


        /*
        VALIDATION GOES HERE (RETURN; IF ERROR FOUND)



         */
        boolean usernameHasAlphabet=false, usernameHasNumber=false;
        for(int i=0;i<username.length();++i){
            Character character = username.charAt(i);
            if(usernameHasAlphabet && usernameHasNumber){
                break;
            }else if((character>='A' && character<='Z') || (character>='a' && character<='z')){
                usernameHasAlphabet = true;
            }else if(Character.isDigit(character)){
                usernameHasNumber = true;
            }
        }
        if (username.length()<3 || username.length()>25 || !usernameHasAlphabet || !usernameHasNumber) {
            mETUsername.setError("Username must be 3 to 25 digits containing at least one letter and one digit");
            valid = false;
        } else {
            mETUsername.setError(null);
        }

        boolean passwordHasUppercase=false, passwordHasLowercase=false , passwordHasNumber=false;
        for(int i=0;i<password.length();++i){
            Character character = password.charAt(i);
            if(passwordHasUppercase && passwordHasLowercase && passwordHasNumber){
                break;
            }else if(character>='A' && character<='Z'){
                passwordHasUppercase = true;
            }else if(character>='a' && character<='z'){
                passwordHasLowercase = true;
            }else if(Character.isDigit(character)){
                passwordHasNumber = true;
            }
        }
        if (password.length()<7 || !passwordHasUppercase || !passwordHasLowercase || !passwordHasNumber) {
            mETPassword.setError("Password must be more than 6 digits containing at least one uppercase letter, one lowercase letter, and one digit");
            valid = false;
        } else {
            mETPassword.setError(null);
        }

        if (!cpassword.equals(password)) {
            mETCPassword.setError("Password and Confirmation Password must be the same");
            valid = false;
        } else {
            mETCPassword.setError(null);
        }

        boolean phoneIsNumeric=true;
        for(int i=0;i<phone.length();++i){
            Character character = phone.charAt(i);
            if(!Character.isDigit(character)){
                phoneIsNumeric=false;
                break;
            }
        }
        if (phone.length()<10 || phone.length()>12 || !phoneIsNumeric) {
            mETPhone.setError("Phone number must be 10 to 12 digits containing only numbers");
            valid = false;
        } else {
            mETPhone.setError(null);
        }

        // VALIDASI GENDER??

        if(!isTermCross){
            mCBTerms.setError("You must agree with our terms of service to continue");
            valid = false;
        } else {
            mCBTerms.setError(null);
        }

        if(!valid) return;


        String user_id = Handler.getUserID();
        Handler.sUsers.add(new User(user_id, username, password, phone, dob, (isMale) ? "Male" : "Female"));
        Handler.insertUser(new User(user_id, username, password, phone, dob, (isMale) ? "Male" : "Female"));

        String sms_message = "Registration Successful!";
        SmsManager.getDefault().sendTextMessage(phone,null, sms_message, null, null);
//        SmsManager.getSmsManagerForSubscriptionId( SubscriptionManager.from(this).getActiveSubscriptionInfoList().get(1).getSubscriptionId()).sendTextMessage(phone, "123", sms_message, null, null);
        finish();
    }

}

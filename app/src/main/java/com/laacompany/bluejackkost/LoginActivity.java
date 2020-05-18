package com.laacompany.bluejackkost;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.laacompany.bluejackkost.Database.DBSchema;
import com.laacompany.bluejackkost.Database.DatabaseHelper;
import com.laacompany.bluejackkost.Handle.Handler;
import com.laacompany.bluejackkost.ObjectClass.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mTILUsername, mTILPassword;
    private EditText mETUsername,mETPassword;
    SQLiteDatabase database;

    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, LoginActivity.class);
    }

    private void init(){
        mETUsername = findViewById(R.id.id_login_edt_username);
        mETPassword = findViewById(R.id.id_login_edt_password);
        mTILUsername = findViewById(R.id.id_login_til_username);
        mTILPassword = findViewById(R.id.id_login_til_password);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        init();
        requestAppPermissions();
    }

    public void clickLogin(View view) {

            User userLogin = validation();

            if (userLogin != null){
                SharedPreferences pref = getSharedPreferences(Handler.SP_USER, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Handler.SP_KEY_ID,userLogin.getId());

                editor.apply();
                Handler.sCurrentUser = userLogin.getId();
                Toast.makeText(this, "Welcome " + userLogin.getUsername(),Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    private User validation() {

        String login_username = mETUsername.getText().toString();
        String login_password = mETPassword.getText().toString();

        boolean valid = true;

        if (login_username.isEmpty()) {
            mTILUsername.setError("Username must be filled");
            valid = false;
        } else {
            mTILUsername.setError(null);
        }

        if (login_password.isEmpty()) {
            mTILPassword.setError("Password must be filled");
            valid = false;
        } else {
            mTILPassword.setError(null);
        }

        if (!valid) return null;

        for(User user : Handler.sUsers){
            if (user.getUsername().equals(login_username)){
                if(user.getPassword().equals(login_password)) {
                    return user;
                } else {
                    valid = false;
                }
            }
        }

        //user found with mismatched credentials
        if(!valid){
            mTILUsername.setError("Username and Password not matched");
            mTILPassword.setError("Username and Password not matched");
        } else {
            mTILUsername.setError("Username not found");
            mTILPassword.setError("Username not found");
        }
        return null;
    }

    public void clickRegister(View view) {
        startActivity(RegisterActivity.newIntent(this));
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit Application");
        alertDialog.setMessage("Are you sure you want to quit?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    private void requestAppPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE}, 0);
    }

}

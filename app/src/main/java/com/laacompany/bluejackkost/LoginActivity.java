package com.laacompany.bluejackkost;

import android.content.Context;
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
    }



    public void clickLogin(View view) {

            User userLogin = validation();

            if (userLogin != null){
                SharedPreferences pref = getSharedPreferences(Handler.SP_USER, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Handler.SP_KEY_ID,userLogin.getId());

                editor.apply();
                Handler.sCurrentUser = userLogin.getId();
                Toast.makeText(this, "Welcome " + userLogin.getId(),Toast.LENGTH_SHORT).show();
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
            if (user.getUsername().equals(login_username) && user.getPassword().equals(login_password)) {
                return user;
            }
        }

        mTILUsername.setError("Username and Password not matched");
        mTILPassword.setError("Username and Password not matched");
        return null;
    }


    public void clickRegister(View view) {
        startActivity(RegisterActivity.newIntent(this));
    }
}

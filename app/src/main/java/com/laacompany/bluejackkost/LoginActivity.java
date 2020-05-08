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

import com.laacompany.bluejackkost.Database.DBSchema;
import com.laacompany.bluejackkost.Database.DatabaseHelper;
import com.laacompany.bluejackkost.Handle.Handler;
import com.laacompany.bluejackkost.ObjectClass.User;

public class LoginActivity extends AppCompatActivity {

    private EditText mETUsername,mETPassword;
    SQLiteDatabase database;


    public static Intent newIntent(Context packageContext){
        return new Intent(packageContext, LoginActivity.class);
    }

    private void init(){
        mETUsername = findViewById(R.id.id_login_edt_username);
        mETPassword = findViewById(R.id.id_login_edt_password);
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

            String login_username = mETUsername.getText().toString();
            String login_password = mETPassword.getText().toString();

            if(login_username.isEmpty() && (login_password.isEmpty() ) ) {
                Toast.makeText(LoginActivity.this,"Username & Password is empty",Toast.LENGTH_SHORT).show();
            }else if(login_username.isEmpty() && !(login_password.isEmpty()) ){
                Toast.makeText(LoginActivity.this,"Username is empty",Toast.LENGTH_SHORT).show();
            }else if(!(login_username.isEmpty()) && login_password.isEmpty()  ){
                Toast.makeText(LoginActivity.this,"Password is empty",Toast.LENGTH_SHORT).show();
            }else{
                databasevalidation();
            }

        }

    private void databasevalidation() {

        String login_username = mETUsername.getText().toString();
        String login_password = mETPassword.getText().toString();

        User userLogin = null;

        for(User user : Handler.sUsers){
            if (user.getUsername().equals(login_username) && user.getPassword().equals(login_password)) {
                userLogin = user;
                break;
            }
        }

        if(userLogin != null){
            SharedPreferences pref = getSharedPreferences(Handler.SP_USER, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Handler.SP_KEY_ID,userLogin.getId());

            editor.apply();
            Handler.sCurrentUser = userLogin.getId();
//            Log.d("halo", userLogin.getId());
            Toast.makeText(this, "Welcome " + userLogin.getId(),Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(LoginActivity.this,"Data is not registered",Toast.LENGTH_SHORT).show();
        }


    }


    public void clickRegister(View view) {
        startActivity(RegisterActivity.newIntent(this));
    }
}

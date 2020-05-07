package com.laacompany.bluejackkost;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.laacompany.bluejackkost.Database.DBSchema;
import com.laacompany.bluejackkost.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText mETUsername,mETPassword;
    SQLiteDatabase database;


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

        long count = GetUserCount();
        int id = 0;
        boolean benar = false;

        while ( id <= count){
            Cursor cursor = getcursor(id);
            cursor.moveToNext();

            String database_username = cursor.getString(cursor.getColumnIndex( DBSchema.UserTable.Cols.USERNAME));
            String database_password = cursor.getString(cursor.getColumnIndex(DBSchema.UserTable.Cols.PASSWORD));

            if(login_username.equals(database_username)  && login_password.equals(database_password) ){
                benar = true;
                break;
            }else{
                benar = false;
            }
        }

        if(benar){
            Toast.makeText(LoginActivity.this,"Data is not registered",Toast.LENGTH_SHORT).show();
        }else if(!benar){
            finish();
        }


    }

    private Cursor getcursor(int id) {
        String query_select = "SELECT * FROM " + DBSchema.UserTable.NAME + " WHERE " + DBSchema.UserTable.Cols.USER_ID + " = " + id;
        return database.rawQuery(query_select,null);
    }

    private int GetUserCount(){
        String countquery = "SELECT * FROM " + DBSchema.UserTable.NAME;
        Cursor cursor = database.rawQuery(countquery,null);
        cursor.close();

        return cursor.getCount();
    }


    public void clickRegister(View view) {
        Intent intent = RegisterActivity.newIntent(this);
        startActivity(intent);
    }
}

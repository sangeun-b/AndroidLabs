package com.example.androidlabs1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        EditText email1 = (EditText) findViewById(R.id.e1);
        //EditText pass = (EditText) findViewById(R.id.ed2);

        SharedPreferences prefs = getSharedPreferences("PR", Context.MODE_PRIVATE);
        String s1 = prefs.getString("ReserveName", "");
        email1.setText(s1);



        Button login = (Button)findViewById(R.id.b1);
        login.setOnClickListener(bt -> {
            Intent goToProfile  = new Intent(MainActivity.this,ProfileActivity.class);
            goToProfile.putExtra("EMAIL",email1.getText().toString());

            startActivity(goToProfile);
        });



    }
    @Override
    protected void onPause(){
        super.onPause();
        EditText email1 = (EditText) findViewById(R.id.e1);

        SharedPreferences prefs = getSharedPreferences("PR",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("ReserveName",email1.getText().toString());
        edit.commit();
    }
}


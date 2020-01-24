package com.example.androidlabs1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);

       Button bu2 = (Button) findViewById(R.id.button2);
        bu2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();
            }
        });
        Switch sw2 = (Switch) findViewById((R.id.switch2));
        sw2.setOnCheckedChangeListener( (sw, isChecked) -> {
            if(isChecked==true){
                Snackbar.make( sw,"This switch is now on", Snackbar.LENGTH_LONG).
                setAction("undo", click -> sw2.setChecked(!isChecked)).show();

            }else if(isChecked==false) {
                Snackbar.make( sw,"This switch is now off", Snackbar.LENGTH_LONG)
                .setAction("undo", click -> sw2.setChecked(!isChecked)).show();


            }
        });


            CheckBox ck2 = (CheckBox) findViewById((R.id.checkbox2));
            ck2.setOnCheckedChangeListener( (ck, isChecked2) -> {
                if(isChecked2==true){
                    Snackbar.make( ck,"This check is now on", Snackbar.LENGTH_LONG).
                            setAction("undo", click -> ck2.setChecked(!isChecked2)).show();

                }else if(isChecked2==false) {
                    Snackbar.make( ck,"This check is now off", Snackbar.LENGTH_LONG)
                            .setAction("undo", click -> ck2.setChecked(!isChecked2)).show();


                }
                });

        }
}


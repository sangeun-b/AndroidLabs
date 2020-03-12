package com.example.androidlabs1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        EditText email1= (EditText) findViewById(R.id.ed2);

        Intent fromMain = getIntent();
        String se = fromMain.getStringExtra("EMAIL");
        email1.setText(se);

        mImageButton = (ImageButton) findViewById(R.id.ib1);
        mImageButton.setOnClickListener(bt -> dispatchTakePictureIntent());


        Button chatroom = (Button)findViewById(R.id.ch);
        chatroom.setOnClickListener(btn -> {
            Intent goToChat = new Intent(ProfileActivity.this,ChatRoomActivity.class);
            startActivity(goToChat);
        } );

        Button weatherCk = (Button)findViewById(R.id.we);
        weatherCk.setOnClickListener(btn->{
            Intent goToWeather = new Intent(ProfileActivity.this,WeatherForecast.class);
            startActivity(goToWeather);
        });

        Log.e(ACTIVITY_NAME, "In function:" + "onCreate");

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton = (ImageButton) findViewById(R.id.ib1);
            mImageButton.setImageBitmap(imageBitmap);

        }
      //  Log.e(ACTIVITY_NAME, "In function:" + "onActivityResult");

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + "onStart");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + "onPause");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");
    }


}

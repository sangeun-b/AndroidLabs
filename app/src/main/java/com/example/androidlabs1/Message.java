package com.example.androidlabs1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Message extends AppCompatActivity {

    private String message;
    private boolean sendMessage;

    public Message(){

    }
    public Message(boolean sendMessage, String message){
        this.sendMessage=sendMessage;
        this.message=message;
    }
    public boolean isSendMessage(){
        return sendMessage;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }*/
}

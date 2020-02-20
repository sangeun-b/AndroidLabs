package com.example.androidlabs1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Message extends AppCompatActivity {

    protected String message;
    protected boolean isSent;
    protected long id;

    public Message(){

    }
    public Message(long id, String message, boolean isSent){
        this.isSent=isSent;
        this.message=message;
        this.id=id;
    }
    public boolean isSent(){
        return isSent;
    }
    public String getMessage(){
        return message;
    }
    public long getId(){ return id;  }

}

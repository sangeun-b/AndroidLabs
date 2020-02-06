package com.example.androidlabs1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Message> chatArray = new ArrayList<>();
    ListView chatList;
    BaseAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chatList = (ListView) findViewById(R.id.chatList);
        myAdapter=new MyListAdapter();
        chatList.setAdapter(myAdapter);

        Button sendButton = (Button) findViewById(R.id.sendButton);
        Button receiveButton = (Button) findViewById(R.id.receiveButton);

        chatList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.select)+(position+1) + "\n"+getString(R.string.db)+id)
                    .setPositiveButton(getString(R.string.y), (click,arg)-> {
                        chatArray.remove(position);
                        myAdapter.notifyDataSetChanged();

            })
                    .setNegativeButton(getString(R.string.n), (click,arg) -> {

                            }).create().show();

            return true ;
        });

        EditText chatEdit = (EditText) findViewById(R.id.type);
        sendButton.setOnClickListener( bt -> {
            chatArray.add(new Message(true,chatEdit.getText().toString()));
            chatEdit.getText().clear();
            myAdapter.notifyDataSetChanged();
        });

        receiveButton.setOnClickListener(btn -> {
            chatArray.add(new Message(false ,chatEdit.getText().toString()));
            chatEdit.getText().clear();
            myAdapter.notifyDataSetChanged();
        });

    }
    public class MyListAdapter extends BaseAdapter{

        public int getCount(){
            return chatArray.size();
        }
        public Message getItem(int position){
            return chatArray.get(position);

        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View v = convertView;

            Message msg = getItem(position);


            if (msg.isSendMessage()==true) {
                v = inflater.inflate(R.layout.activity_send, null);
                TextView sendTextView = v.findViewById(R.id.sendTextView);
                sendTextView.setText(msg.getMessage());

            } else if (msg.isSendMessage()==false) {
                v = inflater.inflate(R.layout.activity_receive, null);
                TextView receiveTextView = v.findViewById(R.id.receiveTextView);
                receiveTextView.setText(msg.getMessage());

            }

            return v;

           /* if (chatArray.get(position).isSendMessage()) {
                v = inflater.inflate(R.layout.activity_send,parent, false);
                TextView sendTextView = v.findViewById(R.id.sendTextView);
                sendTextView.setText(chatArray.get(position).getMessage());

            } else if (chatArray.get(position).isSendMessage()) {
                v = inflater.inflate(R.layout.activity_receive,parent,false);
                TextView receiveTextView = v.findViewById(R.id.receiveTextView);
                receiveTextView.setText(chatArray.get(position).getMessage());

            }

            return v;*/

        }
        public long getItemId(int position){
            return position;

        }

    }
}

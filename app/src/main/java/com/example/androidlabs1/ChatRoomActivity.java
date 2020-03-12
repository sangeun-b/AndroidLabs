package com.example.androidlabs1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;


import static com.example.androidlabs1.MyOpener.*;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Message> messageArray = new ArrayList<>();
    BaseAdapter myAdapter;
    SQLiteDatabase db;
    public static final String ACTIVITY_NAME="ChatRoomActivity";
    public static final String MESSAGE_SELECTED="Message";
    public static final String MESSAGE_ID="Id";
    public static final String MESSAGE_POSITION="Position";
    public static final String MESSAGE_TYPE="Type";
    DetailsFragment dFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView messageListView = (ListView) findViewById(R.id.chatList);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        Button receiveButton = (Button) findViewById(R.id.receiveButton);

        loadDataFromDatabase();

        myAdapter = new MyListAdapter();
        messageListView.setAdapter(myAdapter);

        EditText chatEdit = (EditText) findViewById(R.id.type);

        boolean isTablet = findViewById(R.id.fragmentLocation) != null;



        sendButton.setOnClickListener(bt -> {
            String isSend = "1";
            String message = chatEdit.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MESSAGE, message);
            newRowValues.put(MyOpener.COL_IS_SEND, isSend);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            //Message newMessage = new Message(true,message,id);
            messageArray.add(new Message(newId, message, true));
            chatEdit.getText().clear();
            myAdapter.notifyDataSetChanged();
            //chatEdit.setText("");
        });

        receiveButton.setOnClickListener(btn -> {
            String isSend = "0";
            String message = chatEdit.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MESSAGE, message);
            newRowValues.put(MyOpener.COL_IS_SEND, isSend);
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            messageArray.add(new Message(newId, message, false));
            chatEdit.getText().clear();
            myAdapter.notifyDataSetChanged();
            //chatEdit.setText("");
        });
        //int selectedMessage = messageListView.getId();


        messageListView.setOnItemClickListener((list,view,pos,id)->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString(MESSAGE_SELECTED, messageArray.get(pos).getMessage());
            //dataToPass.putInt(MESSAGE_POSITION, pos);
            dataToPass.putLong(MESSAGE_ID, messageArray.get(pos).getId());
            dataToPass.putBoolean(MESSAGE_TYPE, messageArray.get(pos).isSent());

            if(isTablet) {
                dFragment = new DetailsFragment();//add a DetailFragment
                dFragment.setArguments(dataToPass);//pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit();
            }else{
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });

        messageListView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.delete))
                    .setMessage(getString(R.string.select) + (position + 1) + "\n" + getString(R.string.db) + id)
                    .setPositiveButton(getString(R.string.y), (click, arg) -> {
                        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{Long.toString(id)});
                        messageArray.remove(position);
                        if(isTablet) {
                            getSupportFragmentManager().beginTransaction().remove(dFragment).commit();
                        }

                        myAdapter.notifyDataSetChanged();

                    })
                    .setNegativeButton(getString(R.string.n), (click, arg) -> {

                    }).create().show();
            return true;
        });


        String[] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_IS_SEND};
        Cursor c = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(c,MyOpener.VERSION_NUM);

    }

    private void loadDataFromDatabase() {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_IS_SEND};
        Cursor result = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int messageColumnIndex = result.getColumnIndex(MyOpener.COL_MESSAGE);
        int sendColumnIndex = result.getColumnIndex(MyOpener.COL_IS_SEND);
        int idColumnIndex = result.getColumnIndex(MyOpener.COL_ID);

        while (result.moveToNext()) {
            String message = result.getString(messageColumnIndex);
            int send = result.getInt(sendColumnIndex);
            long id = result.getLong(idColumnIndex);

            boolean isSend;
            if (send == 0) {
                isSend = false;
            } else {
                isSend = true;
            }
            messageArray.add(new Message(id, message, isSend));
        }

    }

    /*protected void deleteMessage(int dbId){
        db.delete(TABLE_NAME,COL_ID+"=?", new String[] {Long.toString(messageListView.getId())});
    }*/
    public class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return messageArray.size();
        }

        public Message getItem(int position) {
            return messageArray.get(position);

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View v = convertView;
            Message msg = getItem(position);

            if (msg.isSent() == true) {
                //if(msg.isSent()){
                v = inflater.inflate(R.layout.activity_send, null);
                TextView sendTextView = v.findViewById(R.id.sendTextView);
                sendTextView.setText(msg.getMessage());

            } else if (msg.isSent() == false) {
                v = inflater.inflate(R.layout.activity_receive, null);
                TextView receiveTextView = v.findViewById(R.id.receiveTextView);
                receiveTextView.setText(msg.getMessage());
            }
            return v;

        }

        public long getItemId(int position) {
            return getItem(position).getId();

        }

    }

    public void printCursor(Cursor c, int version) {

        Log.e(ACTIVITY_NAME, "My database version:" + version);
        Log.e(ACTIVITY_NAME, "Number of columns:" + c.getColumnCount());
        Log.e(ACTIVITY_NAME, "Name of the columns:" + Arrays.toString(c.getColumnNames()));
        Log.e(ACTIVITY_NAME, "Number of results:" + c.getCount());
        //Log.e(ACTIVITY_NAME, "Row of results:");

        c.moveToFirst();
           while (!c.isAfterLast()) {
                 Log.e(ACTIVITY_NAME,"Row of results\t Id: "+c.getString(c.getColumnIndex(COL_ID)) + ", Message: "+
                        c.getString(c.getColumnIndex(COL_MESSAGE))+", Send: "+
                        c.getString(c.getColumnIndex(COL_IS_SEND)));
                c.moveToNext();
            }
        }

    }




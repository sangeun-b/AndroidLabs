package com.example.androidlabs1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private Bundle dataFromActivity;
    private long id;
    private boolean isTablet;
    /**public DetailsFragment() {
        // Required empty public constructor
    }     */
    public void setTablet(boolean tablet) { isTablet = tablet; }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.MESSAGE_ID );

        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        TextView message = (TextView)result.findViewById(R.id.lab7t1);
        message.setText(dataFromActivity.getString(ChatRoomActivity.MESSAGE_SELECTED));

        TextView messageId = (TextView)result.findViewById(R.id.lab7t2);
        messageId.setText("ID=" + id);

        CheckBox type = result.findViewById(R.id.lab7ck1);
        type.setChecked(dataFromActivity.getBoolean(ChatRoomActivity.MESSAGE_TYPE));
        Button hideButton = (Button)result.findViewById(R.id.lab7b1);

        hideButton.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            if(isTablet){
            ChatRoomActivity parent = (ChatRoomActivity) getActivity();
            //parent.deleteMessageId((int)id);
            parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
        }else {

                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(ChatRoomActivity.MESSAGE_ID, dataFromActivity.getLong(ChatRoomActivity.MESSAGE_ID));
                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
                /**EmptyActivity parentActivity = (EmptyActivity) getActivity();
                Intent previousActivity = new Intent();
                previousActivity.putExtra("id",id);
                parentActivity.setResult(Activity.RESULT_OK,previousActivity);
                parentActivity.finish();*/
            }
        });


        return result;


    }
}

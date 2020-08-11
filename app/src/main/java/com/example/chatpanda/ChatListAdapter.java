package com.example.chatpanda;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**@author : Swaraj Deshmukh
 *  Date : 22/07/2020
 *
 */

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseRef;
    private String myUsername;
    private ArrayList<DataSnapshot> mSnapShot;



    private ChildEventListener mListner  = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             mSnapShot.add(dataSnapshot); //adding data changed to snapshot
             notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    //Constructor
    public ChatListAdapter(Activity activity,DatabaseReference Ref, String name){
        mActivity = activity;
        myUsername = name;
        mDatabaseRef = Ref.child("CHATS");
        mSnapShot = new ArrayList<>();

        //adding Listner
        mDatabaseRef.addChildEventListener(mListner);

    }


    //static class

    static  class ViewHolder{
        TextView senderNames;
        TextView chatBody;
//Align left and right messages
        LinearLayout.LayoutParams layoutParams;
    }


    @Override
    public int getCount() {
        return mSnapShot.size();
    }

    @Override
    public MessageDetail getItem(int position) {

        DataSnapshot snapshot = mSnapShot.get(position);
        return snapshot.getValue(MessageDetail.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_message,parent,false);


            final ViewHolder holder = new ViewHolder();
            holder.senderNames = (TextView) convertView.findViewById(R.id.author);
            holder.chatBody = (TextView) convertView.findViewById(R.id.message);
            holder.layoutParams = (LinearLayout.LayoutParams) holder.senderNames.getLayoutParams();

            convertView.setTag(holder);
            //No view that's why we have to setTag
        }

        final MessageDetail message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();


        boolean isMe = message.getAuthor().equals(myUsername);
        //ChatStyling method call............................
        ChatStyling(isMe,holder);

        String author = message.getAuthor();
        holder.senderNames.setText(author);

        String msg = message.getMessage();
        holder.chatBody.setText(msg);


        return convertView;
    }


    private void ChatStyling(boolean isMe, ViewHolder  holder){
        if (isMe){
            holder.layoutParams.gravity = Gravity.END;
            holder.senderNames.setTextColor(Color.GRAY);

        }else {
            holder.layoutParams.gravity = Gravity.START;
            holder.senderNames.setTextColor(Color.BLACK);

        }

        holder.senderNames.setLayoutParams(holder.layoutParams);
        holder.chatBody.setLayoutParams(holder.layoutParams);
    }

    //makes your app less laggy
    public void FreeupResources(){
        mDatabaseRef.removeEventListener(mListner);
    }

}

package com.example.chatpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**@author : Swaraj Deshmukh
 *  Date : 22/07/2020
 *
 */
public class ChatActivity extends AppCompatActivity {

    private String mUsername;
    private ListView chatlistview;
    private EditText myChatText;
    private ImageButton mySendButton;
    private DatabaseReference mDatabaseRef;

    private ChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setUpDisplayName();

        //Database instance root
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        chatlistview = (ListView) findViewById(R.id.chat_list_view);
        myChatText = (EditText) findViewById(R.id.messageinput);
        mySendButton = (ImageButton) findViewById(R.id.SendButton);


        //push chat to firebase
        mySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushCahtFirebase();
            }
        });

        //call push method on keyboard event

        myChatText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                PushCahtFirebase();
                return  true;
            }
        });




    }
    //TODO: Logout button

    //Push chat to Firebase
    private void PushCahtFirebase(){
        String chatInput = myChatText.getText().toString();
        if(!chatInput.equals("")){
            MessageDetail chat = new MessageDetail(chatInput,mUsername);
            mDatabaseRef.child("CHATS").push().setValue(chat);
            myChatText.setText("");
        }
    }








    //Set username for user
    private void setUpDisplayName(){
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.Chat_Pref,MODE_PRIVATE);
        mUsername = prefs.getString(RegisterActivity.User_Name,null);

        if(mUsername == null){
            mUsername = "Panda";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this,mDatabaseRef,mUsername);
        //just hooking the listview with the adapter
        chatlistview.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.FreeupResources();
    }
}
package com.example.chatpanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**@author : Swaraj Deshmukh
*  Date : 22/07/2020
*
 */
public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button Login;
    private ImageButton Eyeoff;


    //Firebase reference
    private FirebaseAuth mAuth;

    //flag set to default value
    private int passwordvisible = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btn_login);
        Eyeoff = findViewById(R.id.imageButton_ShowHideButton);
        Eyeoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordvisible == 1) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etPassword.setSelection(etPassword.length());
                    Eyeoff.setImageResource(R.drawable.eye_on);
                    passwordvisible = 0;
                }else{
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setSelection(etPassword.length());
                    Eyeoff.setImageResource(R.drawable.eye_off);
                    passwordvisible = 1;
                }

            }
        });


        //Firebase instance
        mAuth = FirebaseAuth.getInstance();



    }

    //Login button clicked
    public void LoginUser(View v){
        loginUserWithFirebase();


    }



    //Login user with Firebase
    private void loginUserWithFirebase(){

        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(etUsername.equals("") || etPassword.equals("")){
            Toast.makeText(this,"Please enter correct credentials",Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this,"Logging In... ",Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("LOGIN","User was logged in: " +task.isSuccessful());

                if(!task.isSuccessful()){
                    ErrorDialog("There was a problem logging in");
                    Log.i("LOGIN2","Message: ",task.getException());
                }else {
                    Intent intent =  new Intent(MainActivity.this, ChatActivity.class);
                    finish();
                    startActivity(intent);

                }




            }
        });

    }






    //Move user to register activity
    public void RegisterNewUser(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);


    }



    //create error dialog box
    private void ErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
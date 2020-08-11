package com.example.chatpanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**@author : Swaraj Deshmukh
 *  Date : 22/07/2020
 *
 */
public class RegisterActivity extends AppCompatActivity {

    public static final String Chat_Pref = "ChatPref";
    public static final String User_Name = "UserName";

   private EditText Username, ConfirmPassowrd , Password, Email;
   private Button Register;

   //Firebase Reference
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.et_Register_Username);
        ConfirmPassowrd = findViewById(R.id.et_Register_ConfirmPassword);
        Password = findViewById(R.id.et_Register_Password);
        Email = findViewById(R.id.et_Register_Email);
        Register = findViewById(R.id.btn_Register);


        //Firebase Instance
        mAuth = FirebaseAuth.getInstance();


    }
    //Methods on clicking
    public void Register(View v){
        UserRegister();

    }


    //Registration
    private void UserRegister(){

        Email.setError(null);
        Password.setError(null);

        String email = Email.getText().toString();
        String password = Password.getText().toString();

        boolean cancel = false;
        View focusView = null; //kill switch

        //Password
        if(!ValidatePassword(password)){
            Password.setError(getString(R.string.Invalid_Password));
            focusView = Password;
            cancel = true;
        }

        //Email
        if (TextUtils.isEmpty(email) && !ValidateEmail(email)){
            Email.setError(getString(R.string.Invalid_Email));
            focusView = Email;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus(); //focusView implementation
        }else{
            createUser(); //createUser method calling
        }

    }




    //Validate email
    private boolean ValidateEmail(String email){
        return email.contains("@");
    }

    //Validate Passwords
    private boolean ValidatePassword(String password){
        String confirmPassword = ConfirmPassowrd.getText().toString();
        return ((!password.equals(""))
               && (password !=null)
               && (confirmPassword.equals(password))
               && (password.length()>6));
    }




    //Use shared preferences for saving Username
    private void SaveUsername(){
        String UserNamePref = Username.getText().toString();
        SharedPreferences Pref = getSharedPreferences(Chat_Pref,0);
        Pref.edit().putString(DISPLAY_SERVICE,UserNamePref).apply();

    }


    //SignUp a User in Firebase
    private void createUser(){

        String email = Email.getText().toString();
        String password = Password.getText().toString();

         //call method from firebase
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Delete before production
                Log.i("UserCreate","User creation was: " + task.isSuccessful());

                if(!task.isSuccessful()){
                    ErrorDialog("Oops! Registration Failed");
                }else {
                    SaveUsername();
                    Toast.makeText(getApplicationContext(),"Registration is completed",Toast.LENGTH_LONG).show();

                    //Moving user to Login Screen`on Success
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });

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
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

/**
 * @author : Swaraj Deshmukh
 * Date : 22/07/2020
 */
public class RegisterActivity extends AppCompatActivity {

    public static final String CHAT_PREF = "ChatPref";
    public static final String USER_NAME = "UserName";

    private EditText etUsername, etPassword, etConfirmPassword, etEmail;
    private Button btnRegister;

    // Firebase Auth instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        etUsername = findViewById(R.id.et_Register_Username);
        etPassword = findViewById(R.id.et_Register_Password);
        etConfirmPassword = findViewById(R.id.et_Register_ConfirmPassword);
        etEmail = findViewById(R.id.et_Register_Email);
        btnRegister = findViewById(R.id.btn_Register);

        // Firebase instance
        mAuth = FirebaseAuth.getInstance();

        // Set click listener
        btnRegister.setOnClickListener(v -> registerUser());
    }

    /** Handles user registration */
    private void registerUser() {
        // Reset errors
        etEmail.setError(null);
        etPassword.setError(null);

        // Get user input
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Validate password
        if (!isPasswordValid(password, confirmPassword)) {
            etPassword.setError(getString(R.string.Invalid_Password));
            focusView = etPassword;
            cancel = true;
        }

        // Validate email
        if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.Invalid_Email));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            createUser(email, password);
        }
    }

    /** Validates email format */
    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && email.contains("@");
    }

    /** Validates password and confirm password */
    private boolean isPasswordValid(String password, String confirmPassword) {
        return !TextUtils.isEmpty(password)
                && password.length() > 6
                && password.equals(confirmPassword);
    }

    /** Saves username in SharedPreferences */
    private void saveUsername() {
        String username = etUsername.getText().toString().trim();
        SharedPreferences pref = getSharedPreferences(CHAT_PREF, MODE_PRIVATE);
        pref.edit().putString(USER_NAME, username).apply();
    }

    /** Creates Firebase user */
    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.i("UserCreate", "User creation successful: " + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        showErrorDialog("Oops! Registration Failed");
                    } else {
                        saveUsername();
                        Toast.makeText(getApplicationContext(),
                                "Registration completed successfully",
                                Toast.LENGTH_LONG).show();

                        // Navigate to MainActivity
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    /** Shows an alert dialog with error message */
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

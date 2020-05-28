package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button mLoginBtn;
    TextView mCreateUserBtn;
    EditText  mEmail, mPassword;
    FirebaseAuth mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mFirebase = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginbtn);
        mCreateUserBtn = findViewById(R.id.daftarUser);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Silahkan Masukkan Username");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Silahkan Masukkan Password");
                    return;
                }
                if (password.length() < 6){
                    mPassword.setError("Password harus lebih dari 6 karakter");
                    return;
                }

                mFirebase.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task){
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Selamat Datang di Tictac Toe Game.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "ERROR !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        mCreateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText mNamaLengkap, mUsername, mEmail, mPassword, mNohandphone;
    Button mRegisterBtn;
    FirebaseAuth mFirebaseAuth;
    ProgressBar mProgressBar;
    TextView mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        mNamaLengkap = findViewById(R.id.namaLengkap);
        mUsername = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mNohandphone = findViewById(R.id.noHandphone);
        mRegisterBtn = findViewById(R.id.register);
        mLogin = findViewById(R.id.login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressBar = findViewById(R.id.progressBar);

        if(mFirebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Silahkan Masukkan Email");
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

                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task){
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Telah Dibuat.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "ERROR !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}

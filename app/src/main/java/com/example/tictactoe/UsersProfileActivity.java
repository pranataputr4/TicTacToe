package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UsersProfileActivity extends AppCompatActivity {
    TextView namaLengkapProfil, emailProfil, noHpProfil, usernameProfil;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button backLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        emailProfil = findViewById(R.id.emailProfil);
        namaLengkapProfil = findViewById(R.id.namaLengkapProfil);
        noHpProfil = findViewById(R.id.noHpProfil);
        usernameProfil = findViewById(R.id.usernameProfil);
        backLayout = findViewById(R.id.backLayout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                emailProfil.setText(documentSnapshot.getString("Email"));
                namaLengkapProfil.setText(documentSnapshot.getString("Nama Lengkap"));
                noHpProfil.setText(documentSnapshot.getString("Nomor Handphone"));
                usernameProfil.setText(documentSnapshot.getString("Username"));
            }
        });

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersProfileActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}

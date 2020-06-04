package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GamePage extends AppCompatActivity implements View.OnClickListener{

    Button[][] buttons = new Button[3][3];
    boolean player1Turn = true;
    int roundCount;

    int player1Points=0;
    int player2Points=0;

    TextView textViewPlayer1;
    TextView textViewPlayer2;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String usernameProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);


        textViewPlayer1 = findViewById(R.id.text_p1);
        textViewPlayer2 = findViewById(R.id.text_p2);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                usernameProfil += documentSnapshot.getString("Username");
            }
        });
        textViewPlayer1.setText(usernameProfil+": "+player1Points);

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++){
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }

        if (player1Turn){
            ((Button) v).setText("X");
        }else{
            ((Button) v).setText("O");
        }
        roundCount++;

        if (checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount == 9){
            draw();
        }else{
            player1Turn = !player1Turn;

            //insert bot function turn here
            if (player1Turn){

            }else{
                botTurn();
            }

        }

    }

    private void botTurn(){

        int highest=0;
        int b[][] = new int[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){

                if (buttons[i][j].getText().toString().equals("")){
                    //poin mengganggu menang user
                    for (int x=0; x<3; x++){
                        for (int y=0; y<3; y++){
                            if(buttons[x][y].getText().toString().equals("X")){
                                b[i][j] += 1;
                            }
                        }
                    }
                    //poin menggagalkan menang user
                    if(i==0 && j==0){
                        if(buttons[0][1].getText().toString().equals("X")&&buttons[0][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][1].getText().toString().equals("X")&&buttons[2][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][0].getText().toString().equals("X")&&buttons[2][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==0 && j==1){
                        if(buttons[0][0].getText().toString().equals("X")&&buttons[0][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][1].getText().toString().equals("X")&&buttons[2][1].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==0 && j==2){
                        if(buttons[0][1].getText().toString().equals("X")&&buttons[0][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][1].getText().toString().equals("X")&&buttons[2][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][2].getText().toString().equals("X")&&buttons[2][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==1 && j==0){
                        if(buttons[0][0].getText().toString().equals("X")&&buttons[2][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][1].getText().toString().equals("X")&&buttons[1][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==1 && j==1){
                        if(buttons[0][0].getText().toString().equals("X")&&buttons[2][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[0][1].getText().toString().equals("X")&&buttons[2][1].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[0][2].getText().toString().equals("X")&&buttons[2][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][0].getText().toString().equals("X")&&buttons[1][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==1 && j==2){
                        if(buttons[0][2].getText().toString().equals("X")&&buttons[2][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][1].getText().toString().equals("X")&&buttons[1][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==2 && j==0){
                        if(buttons[0][0].getText().toString().equals("X")&&buttons[0][1].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][1].getText().toString().equals("X")&&buttons[0][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[2][1].getText().toString().equals("X")&&buttons[2][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==2 && j==1){
                        if(buttons[0][1].getText().toString().equals("X")&&buttons[1][1].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[2][0].getText().toString().equals("X")&&buttons[2][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }
                    if(i==2 && j==2){
                        if(buttons[0][0].getText().toString().equals("X")&&buttons[1][1].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[1][2].getText().toString().equals("X")&&buttons[0][2].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                        if(buttons[2][1].getText().toString().equals("X")&&buttons[2][0].getText().toString().equals("X")){
                            b[i][j] += 5;
                        }
                    }

                    //memastikan bot pilih menang
                    if(i==0 && j==0){
                        if(buttons[0][1].getText().toString().equals("O")&&buttons[0][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][1].getText().toString().equals("O")&&buttons[2][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][0].getText().toString().equals("O")&&buttons[2][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==0 && j==1){
                        if(buttons[0][0].getText().toString().equals("O")&&buttons[0][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][1].getText().toString().equals("O")&&buttons[2][1].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==0 && j==2){
                        if(buttons[0][1].getText().toString().equals("O")&&buttons[0][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][1].getText().toString().equals("O")&&buttons[2][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][2].getText().toString().equals("O")&&buttons[2][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==1 && j==0){
                        if(buttons[0][0].getText().toString().equals("O")&&buttons[2][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][1].getText().toString().equals("O")&&buttons[1][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==1 && j==1){
                        if(buttons[0][0].getText().toString().equals("O")&&buttons[2][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[0][1].getText().toString().equals("O")&&buttons[2][1].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[0][2].getText().toString().equals("O")&&buttons[2][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][0].getText().toString().equals("O")&&buttons[1][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==1 && j==2){
                        if(buttons[0][2].getText().toString().equals("O")&&buttons[2][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][1].getText().toString().equals("O")&&buttons[1][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==2 && j==0){
                        if(buttons[0][0].getText().toString().equals("O")&&buttons[0][1].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][1].getText().toString().equals("O")&&buttons[0][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[2][1].getText().toString().equals("O")&&buttons[2][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==2 && j==1){
                        if(buttons[0][1].getText().toString().equals("O")&&buttons[1][1].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[2][0].getText().toString().equals("O")&&buttons[2][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }
                    if(i==2 && j==2){
                        if(buttons[0][0].getText().toString().equals("O")&&buttons[1][1].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[1][2].getText().toString().equals("O")&&buttons[0][2].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                        if(buttons[2][1].getText().toString().equals("O")&&buttons[2][0].getText().toString().equals("O")){
                            b[i][j] += 100;
                        }
                    }


                    //menentukan pilihan bot
                    if(b[i][j] > highest){
                        highest = b[i][j];
                    }else if(b[i][j] == highest){
                        b[i][j]-=1;
                    }

                }

            }
        }

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if(highest == b[i][j]){
                    buttons[i][j].setText("O");
                }
            }
        }
        roundCount++;
        if (checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount == 9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0; i<3; i++){
            if(field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) &&
                    !field[i][0].equals("")){
                return true;
            }
        }

        for (int i=0; i<3; i++){
            if(field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0]) &&
                !field[0][2].equals("")){
            return true;
        }

        return false;

    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayer1.setText(usernameProfil+": "+player1Points);
        textViewPlayer2.setText("Player 2: "+player2Points);
    }
    private void resetBoard(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }


}

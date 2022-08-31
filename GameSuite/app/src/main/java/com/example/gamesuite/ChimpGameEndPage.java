package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Chimp Game End Page.
 *
 * @author Shijie Wang
 * @version 2.0
 */

public class ChimpGameEndPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chimp_game_end_page);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Initialize TextView
        TextView number = findViewById(R.id.number);
        TextView strike = findViewById(R.id.strike);

        // Initialize Button
        Button returnButton = findViewById(R.id.button_chimp_game_end_page_return);
        Button continueButton = findViewById(R.id.continue_button);

        // TextView setup
        number.setText(String.valueOf(ChimpGame.getNum()));
        strike.setText(ChimpGame.getStrike() + " of 3");

        // Button click setup
        returnButton.setOnClickListener(backChimpStartPage -> {
            SoundEffect.playSound(0);
            ChimpGame.setNum(4);
            Intent intent = new Intent(ChimpGameEndPage.this, ChimpGameStartPage.class);
            finish();
            startActivity(intent);
        });
        continueButton.setOnClickListener(continueGame -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(ChimpGameEndPage.this, ChimpGame.class);
            finish();
            startActivity(intent);
        });
    }
}
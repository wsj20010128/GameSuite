package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ChimpGameScorePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chimp_game_score_page);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // initialize TextView
        TextView score = findViewById(R.id.score);

        // initialize Button
        Button saveScoreButton = findViewById(R.id.save_score_button);
        Button tryAgainButton = findViewById(R.id.try_again_button);

        // TextView setup
        score.setText(String.valueOf(ChimpGame.getNum()));

        // Button click setup
        tryAgainButton.setOnClickListener(saveScore -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(ChimpGameScorePage.this, ChimpGameStartPage.class);
            finish();
            startActivity(intent);
        });
    }
}
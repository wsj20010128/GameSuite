package com.example.gamesuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * End page of game 2048
 *
 * @author Zixiang Xu
 * @version 1.0
 */
public class Game2048EndPage extends AppCompatActivity {

    private static String message;
    private static int score;

    /**
     * Create the activity of game 2048 page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_game_end_page);

        // Buttons setup
        Button tryButton = findViewById(R.id.game_2048_try_again_button);
        Button returnButton = findViewById(R.id.game2048_button_end_page_return);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Buttons click setup
        tryButton.setOnClickListener(backGame2048 -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Game2048EndPage.this, Game2048.class);
            finish();
            startActivity(intent);
        });
        returnButton.setOnClickListener(backGame2049StartPage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Game2048EndPage.this, Game2048StartPage.class);
            finish();
            startActivity(intent);
        });

        // Set messages and score of the end page
        TextView messageView = findViewById(R.id.game_2048_score_title);
        TextView scoreView = findViewById(R.id.game2048_score);
        messageView.setText(Game2048EndPage.message);
        scoreView.setText(String.valueOf(Game2048EndPage.score));
    }

    /**
     * Set the message of end page
     *
     * @param message the message of end page
     */
    public static void setMessage(final String message) {
        Game2048EndPage.message = message;
    }

    /**
     * Set the score of end page
     *
     * @param score the score of end page
     */
    public static void setScore(final int score) {
        Game2048EndPage.score = score;
    }
}

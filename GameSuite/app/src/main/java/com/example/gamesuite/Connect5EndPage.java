package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Connect5EndPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5_end_page);

        // Initialize sound pool
        SoundEffect.initSound(this);

        Button dungeonEndReturnButton = findViewById(R.id.gameDungeon_end_page_return_button);
        Button dungeonRestartButton = findViewById(R.id.dungeonRestartGameBtn);
        TextView dungeonEndText = findViewById(R.id.dungeonEndPage_text_score);

        dungeonEndReturnButton.setOnClickListener(dungeonEndReturn -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Connect5EndPage.this, MainScreenGUI.class);
            finish();
            startActivity(intent);
        });

        dungeonRestartButton.setOnClickListener(dungeonRestart -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Connect5EndPage.this, Connect5StartPage.class);
            finish();
            startActivity(intent);
        });

        dungeonEndText.setText(MyView.getRes());
    }
}
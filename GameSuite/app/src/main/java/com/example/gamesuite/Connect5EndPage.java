package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.gamesuite.R;
import com.example.gamesuite.MainScreenGUI;

public class Connect5EndPage extends AppCompatActivity {
    private Button dungeonEndReturnButton;
    private Button dungeonRestartButton;
    private TextView dungeonEndText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5_end_page);
        dungeonEndReturnButton = findViewById(R.id.gameDungeon_end_page_return_button);
        dungeonRestartButton = findViewById(R.id.dungeonRestartGamebtn);
        dungeonEndText = findViewById(R.id.dungeonEndPage_text_score);

        dungeonEndReturnButton.setOnClickListener(dungeonEndReturn -> {
            Intent intent = new Intent(Connect5EndPage.this, MainScreenGUI.class);
            finish();
            startActivity(intent);
        });

        dungeonRestartButton.setOnClickListener(dungeonRestart -> {
            Intent intent = new Intent(Connect5EndPage.this, Connect5StartPage.class);
            finish();
            startActivity(intent);
        });

        dungeonEndText.setText(MyView.getRes());
    }
}
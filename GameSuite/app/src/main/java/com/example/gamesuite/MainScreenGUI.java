package com.example.gamesuite2340;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainScreenGUI extends AppCompatActivity {
    private Button teamInfoBtn;
    private Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_gui);

        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> exitApp());
        teamInfoBtn = findViewById(R.id.button);
        teamInfoBtn.setOnClickListener(v -> openTeamInfo());
    }

    public void openTeamInfo() {
        Intent intent = new Intent(this, TeamInfo.class);
        startActivity(intent);
    }
    public void exitApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
    }
}
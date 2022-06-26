package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button teamInfoBtn;
    private Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
package com.example.gamesuite2340;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; 
import android.widget.Button;

public class TeamInfo extends AppCompatActivity {

    private Button homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        homeBtn = (Button) findViewById(R.id.buttonHome);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, MainScreenGUI.class);
        startActivity(intent);
    }
}
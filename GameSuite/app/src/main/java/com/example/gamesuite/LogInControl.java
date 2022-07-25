package com.example.gamesuite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Log in screen control class
 *
 * @author Zixiang Xu
 * @version 1.0
 */
public class LogInControl extends AppCompatActivity {

    private HashMap<String, String> userData;
    private EditText username;
    private EditText password;
    private DataSecurity security;

    /**
     * Create the activity of log in page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Initialize security
        try {
            security = new DataSecurity();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Initialize the buttons
        Button buttonSignIn = findViewById(R.id.button_login_signin);
        Button buttonSignUp = findViewById(R.id.button_login_signup);


        // Initialize the text of username and password from user
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signin_password);

        // Initialize the alert dialog for current activity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Initialize the userdata storage for login
        this.userData = new HashMap<>();

        // Read login data from local userdata file
        try {
            readUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Click the [Sign In] button
        buttonSignIn.setOnClickListener(signIn -> {

            // Play click sound
            SoundEffect.playSound(0);

            // Getting an iterator for the local userdata hashmap
            Iterator<Map.Entry<String, String>> userDataItr = userData.entrySet().iterator();

            // Set a sentinel signal for match username and password
            boolean match = false;

            // Iterating through local userdata and check if the input data match one of them
            while (userDataItr.hasNext()) {

                // Get each userdata key-value pair
                Map.Entry<String, String> userDataPair = userDataItr.next();

                // If the comparison matches, set the match signal as true and break the loop
                if (userDataPair.getKey().equals(username.getText().toString()) &&
                    userDataPair.getValue().equals(password.getText().toString())) {
                    match = true;
                    break;
                }
            }

            // If a match is found, user confirmed
            if (match) {
                Intent k = new Intent(LogInControl.this, MainScreenGUI.class);
                finish();
                startActivity(k);

            // If a match is not found, alert
            } else {
                builder.setMessage("Please try again.").setTitle("Wrong username or password!");
                builder.setPositiveButton("OK", (dialog, id) -> SoundEffect.playSound(0));
                builder.show();
            }
        });

        // Click the [Sign Up] button
        buttonSignUp.setOnClickListener(signUp -> {
            try {
                SoundEffect.playSound(0);
                Intent k = new Intent(LogInControl.this, SignUpControl.class);
                finish();
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Read user data from the local data storage.
     *
     * @throws java.io.IOException Exception when file is not found / cannot be created
     */
    private void readUserData() throws java.io.IOException{

        // Create the File tag for the data file
        File file = new File(getFilesDir(), "userData.txt");
        boolean createSuccess;

        // Determine if the data file exists.
        if (!file.exists()) {

            // Create the data file if the file does not exist
            createSuccess = file.createNewFile();

            // Print warning if createNewFile return false
            if (!createSuccess) {
                System.err.println("Create file failed");
            }

        } else {

            // Create scanner for the input file
            Scanner scan = new Scanner(file);

            // Initialize the index and temporary userdata storage
            int index = 0;
            String[] data = new String[2];

            // Read userdata from the data file line by line
            while (scan.hasNextLine()) {

                // Read the data from each line
                // data[0] = username
                // data[1] = password
                data[index++] = scan.nextLine();

                // When a pair of userdata (username + password) is read
                if (index == 2) {

                    // Clear the index of temporary userdata storage
                    index = 0;

                    // Store the decrypted userdata into permanent login userdata storage
                    try {
                        this.userData.put(
                                security.decrypt(data[0]),
                                security.decrypt(data[1]));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            }

            // Close the scanner after finishing the work
            scan.close();
        }
    }
}

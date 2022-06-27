package com.example.gamesuite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Sign up screen control class
 *
 * @author Zixiang Xu
 * @version 1.0
 */
public class SignUpControl extends AppCompatActivity {

    private HashMap<String, String> userData;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    /**
     * Create the activity of sign up page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize the buttons
        Button buttonSignUp = findViewById(R.id.button_signup_signup);
        Button buttonSignUpBack = findViewById(R.id.button_signup_back);

        // Initialize the text of username, password, and confirm password from user
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.confirm_password);

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

        // Click the [Sign Up] button
        buttonSignUp.setOnClickListener(signUp -> {

            // Getting an iterator for the local userdata hashmap
            Iterator<Map.Entry<String, String>> userDataItr = userData.entrySet().iterator();

            // Set a sentinel signal for match username
            boolean match = false;

            // Get the username, password, and confirm username
            String currentUsername = username.getText().toString();
            String currentPassword = password.getText().toString();
            String currentConfirmPassword = confirmPassword.getText().toString();

            // If password != confirm username, retry the password
            if (!currentPassword.equals(currentConfirmPassword)) {
                builder.setMessage("Please enter the same password.").setTitle("Password mismatches!");
                builder.setPositiveButton("OK", null);
                builder.show();
            } else{

                // Iterating through local userdata and check if the input data match one of them
                while (userDataItr.hasNext()) {

                    // Get each userdata key-value pair
                    Map.Entry<String, String> userDataPair = userDataItr.next();

                    // If the comparison matches, set the match signal as true and break the loop
                    if (userDataPair.getKey().equals(currentUsername)) {
                        match = true;
                        break;
                    }
                }

                // If a match is found, alert
                if (match) {
                    builder.setMessage("Please try another username.").setTitle("Username have already existed!");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                } else {

                    // Store the new username and password into the local data
                    File file = new File(getFilesDir(), "userData.txt");

                    // Initialize the sentinel signal for checking the existence of the file
                    boolean existCheck = true;

                    // Determine if the data file exists.
                    if (!file.exists()) {

                        // Create the data file if the file does not exist
                        try {
                            boolean warning = file.createNewFile();

                            // Print warning if createNewFile return false
                            if (warning) {
                                System.err.println("Create file failed");
                            }

                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                            builder.setMessage("Please check if your disk is full or no permission allowed.");
                            builder.setTitle("Data file cannot be created!");
                            builder.setPositiveButton("OK", null);
                            builder.show();
                            existCheck = false;
                        }
                    }

                    // If the file cannot be created, jump the rest procedure
                    if (existCheck) {

                        // Determine if the file is writable
                        if (file.canWrite()) {
                            try {

                                // Successfully write all data in file
                                FileWriter writer = new FileWriter(file, true);
                                writer.write(encrypt(currentUsername)+"\n");
                                writer.write(encrypt(currentPassword)+"\n");
                                writer.flush();
                                writer.close();

                                // Set the congratulation dialog
                                builder.setMessage("Username: " + currentUsername + "\nPassword: " + currentPassword + "\n");
                                builder.setTitle("Your account has been created!");
                                builder.setPositiveButton("OK", (dialog, id) -> {
                                    Intent k = new Intent(SignUpControl.this, LogInControl.class);
                                    SignUpControl.this.finish();
                                    startActivity(k);
                                });
                                builder.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {

                            // Data file is not writable
                            builder.setMessage("Please check if the file is occupied by other programs or is readonly.");
                            builder.setTitle("Data file not writable!");
                            builder.setPositiveButton("OK", null);
                            builder.show();
                        }
                    }
                }
            }
        });

        // Click the [Back] button
        buttonSignUpBack.setOnClickListener(signUpBack -> {
            Intent k = new Intent(SignUpControl.this, LogInControl.class);
            finish();
            startActivity(k);
        });
    }

    /**
     * Read user data from the local data storage
     *
     * @throws java.io.IOException Exception when file is not found / cannot be created
     */
    private void readUserData() throws java.io.IOException{

        // Create the File tag for the data file
        File file = new File(getFilesDir(), "userData.txt");
        boolean warning;

        // Determine if the data file exists.
        if (!file.exists()) {

            // Create the data file if the file does not exist
            warning = file.createNewFile();

            // Print warning if createNewFile return false
            if (warning) {
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
                scan.nextLine();

                // When a pair of userdata (username + password) is read
                if (index == 2) {

                    // Clear the index of temporary userdata storage
                    index = 0;

                    // Store the decrypted userdata into permanent login userdata storage
                    this.userData.put(decrypt(data[0]), decrypt(data[1]));
                    }
            }

            // Close the scanner after finishing the work
            scan.close();
        }
    }

    /**
     * Encryption for user data using BASE64.
     *
     * @param str the input raw user data
     * @throws java.lang.IllegalArgumentException throw exception if the input userdata is null
     * @return the encrypted user data
     */
    private String encrypt(String str) {

        return null;
    }

    /**
     * Decryption for user data using BASE64.
     *
     * @param str the input encrypted user data
     * @throws java.lang.IllegalArgumentException throw exception if the input userdata is null
     * @return the decrypted user data
     */
    private String decrypt(String str) {

        return null;
    }
}

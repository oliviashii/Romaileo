package com.example.romaileo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/*
    Created by Olivia Shi and Priyanka Sarangabany for CS125 Spring 2020 Final Project at UIUC.
    Our app is made for hopeless romantics who wish to spread love and joy
    through the virtual "Romeo", RomaileoBot.
    We learned about the JavaMailAPI from the following sources:
    JavaMail, 'JavaMail Reference Implementation'
    https://javaee.github.io/javamail/
    Tutorials Point, 'Learn Java Mail API'
    https://www.tutorialspoint.com/javamail_api/index.htm
    Musfick Jamil, 'How to Send an Email via JavaMailAPI'
    https://youtu.be/RahBCY5BfS0

    MainActivity class that is run when app is executed.
    Gets the e-mail of the user's secret admirer, the subject of their message, and their message
    Implements the JavaMailAPI class to send the message anonymously through RomaileoBot
 */
public class MainActivity extends AppCompatActivity {
    //information retrieved from user
    public EditText getEmail;
    public EditText getSubject;
    public EditText getMessage;
    public Button getSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //retrieves information from user
        getEmail = findViewById(R.id.to);
        getMessage = findViewById(R.id.message);
        getSubject = findViewById(R.id.subject);

        //adds functionality to send button
        getSend = findViewById(R.id.send);
        getSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }
    //functionality of send button, implements JavaMailAPI.java class
    private void sendMail() {
        //retrieve information for use in the JavaMailAPI.java class
        String email = getEmail.getText().toString().trim();
        String message = getMessage.getText().toString();
        String subject = getSubject.getText().toString().trim();

        //create an instance of JavaMailAPI class to communicate this information and implement email functions
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,email,subject,message);
        //begin process of sending mail
        javaMailAPI.execute();
    }
}

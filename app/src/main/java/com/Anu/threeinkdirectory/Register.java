package com.shiva.threeinkdirectory;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Register extends AppCompatActivity {
    private EditText email;
    private EditText username;
    private EditText password;

    //Send button
    private TextView buttonSend;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        buttonSend = findViewById(R.id.register);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static boolean isLength(String email) {
        if (email.length()<2)
            return true;
        return false;
    }


        public  static boolean isMatched(String frst, String second){
        if (frst == second){
            return true;
        }
        return false;
    }

    private void sendEmail() {
        String emailtxt = email.getText().toString().trim();
        //Getting content for email
        //yaha check garna parxa sab.. function mathi lekheko xa sabai
        if (isValidEmailAddress(emailtxt)) {
            double random = (double) Math.random()*(10000);
            int resultrand = (int) random;
            sharedPreferences  = getSharedPreferences("RegisteredUser",0);
            editor = sharedPreferences.edit();
            editor.putString("username", username.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.apply();
            String subject = "Account Registration";
            String message = "Your registration code is " + resultrand;

            //Creating SendMail object
            SendMail sm = new SendMail(this, emailtxt, subject, message);

            //Executing sendmail to send email
            sm.execute();
        }else{
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
    }

}

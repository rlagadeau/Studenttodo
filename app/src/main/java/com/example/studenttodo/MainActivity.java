package com.example.studenttodo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user_email;
    EditText user_password;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Login");

        databaseHandler = new DatabaseHandler(this);
        user_email = findViewById(R.id.loginEmailInput);
        user_password = findViewById(R.id.loginPasswordInput);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the app?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Authenticate(View view){
        if(user_email.getText().toString().isEmpty() && user_password.getText().toString().isEmpty()){
            Toast.makeText(this, "The fields are empty. Please fill them in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(user_email.getText().toString().isEmpty()){
            Toast.makeText(this, "The email field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(user_password.getText().toString().isEmpty()){
            Toast.makeText(this, "The password field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        String loginUser = databaseHandler.loginUser(user_email.getText().toString(), user_password.getText().toString());

        user_email.getText().clear();
        user_password.getText().clear();

        switch(loginUser){
            case "login success":
                Toast.makeText(this, "Successfully authenticated!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                break;
            case "password incorrect":
                Toast.makeText(this, "Email or password incorrect. Please try again!",Toast.LENGTH_LONG).show();
                break;
            case "not found":
                Toast.makeText(this, "User does not exist. Please sign up to login!",Toast.LENGTH_LONG).show();
                break;
            case "false":
                Toast.makeText(this, "Please sign up before logging in!",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Something went wrong. Please contact administrator!",Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}

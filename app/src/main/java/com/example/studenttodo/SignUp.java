package com.example.studenttodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText firstname, lastname, birthday, address, email, password;
    Button signup;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHandler = new DatabaseHandler(this);

        firstname = findViewById(R.id.signupFirstnameField);
        lastname = findViewById(R.id.signupLastnameField);
        birthday = findViewById(R.id.signupDateField);
        address = findViewById(R.id.signupAddressField);
        email = findViewById(R.id.signupEmailField);
        password = findViewById(R.id.signupPasswordField);
        signup = findViewById(R.id.signupBtn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void SignUp(View view){
        if(firstname.getText().toString().isEmpty() && lastname.getText().toString().isEmpty() && birthday.getText().toString().isEmpty() && address.getText().toString().isEmpty() && email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
            Toast.makeText(this, "The fields are empty. Please fill them in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(firstname.getText().toString().isEmpty()){
            Toast.makeText(this, "The firstname field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(lastname.getText().toString().isEmpty()){
            Toast.makeText(this, "The lastname field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(birthday.getText().toString().isEmpty()){
            Toast.makeText(this, "The date of birth field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(address.getText().toString().isEmpty()){
            Toast.makeText(this, "The address field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "The email field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "The password field is empty. Please fill this in!",Toast.LENGTH_LONG).show();
            return;
        }

        if(!databaseHandler.selectUserEmail(email.getText().toString())) {
            boolean inserted_value = databaseHandler.addUsers(new Users(firstname.getText().toString(), lastname.getText().toString(), birthday.getText().toString(), address.getText().toString(), email.getText().toString(), password.getText().toString()));

            if (inserted_value) {
                Toast.makeText(this, "Successfully added " + email.getText().toString() + " as a new user.", Toast.LENGTH_LONG).show();

                firstname.getText().clear();
                lastname.getText().clear();
                birthday.getText().clear();
                address.getText().clear();
                email.getText().clear();
                password.getText().clear();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
                firstname.getText().clear();
                lastname.getText().clear();
                birthday.getText().clear();
                address.getText().clear();
                email.getText().clear();
                password.getText().clear();
                Toast.makeText(this, "Sign up failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "User with the emailaddress "+email.getText().toString()+ " already exists!", Toast.LENGTH_LONG).show();
            firstname.getText().clear();
            lastname.getText().clear();
            birthday.getText().clear();
            address.getText().clear();
            email.getText().clear();
            password.getText().clear();
        }
    }
}


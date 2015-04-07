package com.kathylynne.chowsense.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.kathylynne.chowsense.app.model.Favorites;
import com.parse.*;

/**
 * Created by Kate on 2015-03-16.
 */
public class RegisterActivity extends ActionBarActivity{

    ParseUser user;
    //String userId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //TODO verify whether these keys are appropriate to deploy  (there are multiple keys offered by the framework)
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");


}

    public void buttonClick(View v){
        Button registerButton = (Button)findViewById(R.id.registerButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        //TODO refactor the user variables to page specific types, and add the confirm password
        String name = ((EditText) findViewById(R.id.registerNameText)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        user = new ParseUser();

        if(v == registerButton) {
            String passwordConfirm = ((EditText) findViewById(R.id.confirmPasswordText)).getText().toString();
            //verify passwords match before sending to Parse
            if (password.equals(passwordConfirm)) {

                user.setUsername(name);
                user.setEmail(email);
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        Context context = getApplicationContext();
                        if (e == null) {

                            Toast.makeText(context, "Registration Success!", Toast.LENGTH_SHORT).show();

                            loginNewUser();

                        } else {
                            // Sign up didn't succeed. Look at the ParseException to figure out what went wrong
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Passwords do not match, Please Try again.", Toast.LENGTH_LONG).show();
            }
        } else if (v == cancelButton) {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }//end of buttonClick


    public void loginNewUser() {

        //Needs to remain a seperate method, the calls cannot be nested into buttonClick.
        String name = ((EditText) findViewById(R.id.registerNameText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        ParseUser.logInInBackground(name, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    ParseUser userLog = user;
                    userName = userLog.getUsername();
                    // Hooray! The user is logged in.
                    Favorites f = new Favorites();
                    f.setUserId();
                    f.saveInBackground();
                    Toast.makeText(getApplicationContext(), "FAVOURITES" + f.getUserId() + " ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Welcome " + userName + "!", Toast.LENGTH_SHORT).show();
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    //This is presently working, should only present errors relevant to the user (ie wrong password)
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //move application forward (start DrawerActivity in default state, with navigation fragment.
        Intent mainIntent = new Intent(this, DrawerActivity.class);
        startActivity(mainIntent);
    }

    //custom action bar for user prior to login.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_before_login, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

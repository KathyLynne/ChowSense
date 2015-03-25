package com.kathylynne.chowsense.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.*;

/**
 * Created by Kate on 2015-03-16.
 */
public class RegisterActivity extends ActionBarActivity{

    ParseUser user;
    String userId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //TODO verify whether these keys are appropriate to deploy  (there are multiple keys offered by the framework)
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View v){
        Button registerButton = (Button)findViewById(R.id.registerButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        //TODO refactor the user variables to page specific types, and add the confirm password
        String name = ((EditText)findViewById(R.id.nameText)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        user = new ParseUser();

        if(v == registerButton) {


            //to be removed, used for testing
            TextView test = (TextView)findViewById(R.id.textView);

            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(password);


            test.setText( email +" "+ name+" "+ password);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Context context = getApplicationContext();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else if (v == cancelButton){
            //Intent login = new Intent(a)
            //TODO return user to login page

        }
        //user.logInInBackground(name, password);
        //String test = user.getObjectId();
        //Context context = getApplicationContext();
        //Toast.makeText(context, test, Toast.LENGTH_SHORT).show();


    }

    public void testButton(View v) {

        //HOLY FUCK it works. But it won't work as a further nested class in the buttonClick method up there.
        String name = ((EditText) findViewById(R.id.nameText)).getText().toString();

        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        ParseUser.logInInBackground(name, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    ParseUser userLog = user;
                    userName = userLog.getUsername();
                    // Hooray! The user is logged in.
                    Context context = getApplicationContext();

                    Toast.makeText(context, "Hi " + userName, Toast.LENGTH_SHORT).show();
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    //This is presently working, should only present errors relevant to the user (ie wrong password)
                    Context context = getApplicationContext();
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        String test = user.getObjectId();
        Context context = getApplicationContext();
        Toast.makeText(context, test, Toast.LENGTH_SHORT).show();
    }

}

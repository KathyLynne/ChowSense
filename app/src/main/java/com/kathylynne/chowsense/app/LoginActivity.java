package com.kathylynne.chowsense.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
//import com.facebook.Session;
//the above import will be used to connect to Facebook at a later date.
/**
 * Created by Kate on 2015-03-16.
 */
public class LoginActivity extends ActionBarActivity {

    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    //method to allow the login click to ba actionable.  Referenced in the xml.
    public void loginClick(View v){

        String name = ((EditText)findViewById(R.id.userName)).getText().toString();

        String password = ((EditText)findViewById(R.id.userPassword)).getText().toString();

        ParseUser.logInInBackground(name, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    ParseUser userLog = user;
                    userName = userLog.getObjectId();
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
    }

}

package com.kathylynne.chowsense.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.Parse;
import com.parse.ParseUser;

///This class will have to be set to only run once, or possibly removed from the production version of the app
public class MainActivity extends ActionBarActivity {

    public static final String USER_TOKEN = "chowsense.user.token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ParseObject.registerSubclass(Ingredient.class);
        //ParseObject.registerSubclass(Recipe.class);
        // TODO remember to check these keys on parse.
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //user has local session token. launch NavigationActivity  (for now set to add recipe activity)
            Intent launchLoggedIn = new Intent(MainActivity.this, AddRecipeActivity.class);
            launchLoggedIn.putExtra(MainActivity.USER_TOKEN, currentUser.toString());
            startActivity(launchLoggedIn);

        } else {
            Intent launchLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(launchLogin);
        }

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
}

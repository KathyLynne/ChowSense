package com.kathylynne.chowsense.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

///This class will have to be set to only run once, or possibly removed from the production version of the app
public class MainActivity extends ActionBarActivity {

    public static final String USER_TOKEN = "chowsense.user.token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseObject.registerSubclass(Ingredient.class);
        ParseObject.registerSubclass(Recipe.class);
        ParseObject.create("Recipe");
        ParseObject.create("Ingredient");
        // TODO remember to check these keys on parse.
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            //user has local session token. launch NavigationActivity  (for now set to add recipe activity)
            Intent launchUser = new Intent(MainActivity.this, DrawerActivity.class);
            //launchUser.putExtra(MainActivity.USER_TOKEN, currentUser.toString());
            startActivity(launchUser);
            this.finish();

        } else {
            Intent launchLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(launchLogin);
            this.finish();
        }

    }


}



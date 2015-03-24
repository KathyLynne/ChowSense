package com.kathylynne.chowsense.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;


public class AddRecipeActivity extends ActionBarActivity {

    ImageButton btnAdd;
    Button btnDisplay;
    EditText title;
    EditText description;
    ArrayList<String> ingredientIds = new ArrayList<String>();
    ArrayList<String> steps = new ArrayList<String>();
    String recipeId;
    Button btnSave;
    // Recipe recipe = new Recipe();
    ParseObject recipe = new ParseObject("Recipe");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //initialize fields
        //ParseObject.registerSubclass(Recipe.class);
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");

        title = (EditText) findViewById(R.id.titleText);
        description = (EditText) findViewById(R.id.descriptionText);
        //for dynamic item add
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        btnSave = (Button) findViewById(R.id.saveRecipeButton);
        //recipeId = UUID.randomUUID().toString();

        //recipe.setObjectId(recipeId);
        recipe.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                recipeId = recipe.getObjectId();
            }
        });

        Toast rview = Toast.makeText(this, recipeId, Toast.LENGTH_SHORT);
        rview.show();
        LayoutOperation.add(this, btnAdd);
        LayoutOperation.display(this, btnDisplay, recipeId);
        //

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_recipe, menu);
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

    public void saveRecipe(View v) {
        //JSONArray newIngredients = new JSONArray();
        //newIngredients.put(ingredientIds);


        //recipe.setTitle(title.getText().toString());
        //recipe.setDescription(description.getText().toString());

        //they see me changing!
    }

    public void addIngredientToList(String ingredientId) {
        ingredientIds.add(ingredientId);
    }
}

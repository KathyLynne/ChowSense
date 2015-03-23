package com.kathylynne.chowsense.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import org.json.JSONArray;

import java.util.ArrayList;


public class AddRecipeActivity extends ActionBarActivity {

    ImageButton btnAdd;
    Button btnDisplay;
    EditText title;
    EditText description;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    ArrayList<String> steps = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //initialize fields
        title = (EditText) findViewById(R.id.titleText);
        description = (EditText) findViewById(R.id.descriptionText);
        //for dynamic item add
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        LayoutOperation.add(this, btnAdd);
        LayoutOperation.display(this, btnDisplay);

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
        JSONArray newIngredients = new JSONArray();
        newIngredients.put(ingredients);

        Recipe recipe = new Recipe();
        recipe.setTitle(title.getText().toString());
        recipe.setDescription(description.getText().toString());


    }

    public void addIngredientToList(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}

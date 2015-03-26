package com.kathylynne.chowsense.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.parse.Parse;
import com.parse.ParseObject;


public class RecipeDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ParseObject.registerSubclass(Ingredient.class);
        ParseObject.registerSubclass(Recipe.class);
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");
        String recipeID = "Lf0f1fJ5WV";
        //ParseQuery<ParseObject> query = ParseQuery.getQuery()

        final LinearLayout ingredientsLayout = (LinearLayout) this.findViewById(R.id.details_wrap_ingredients);
        final LinearLayout stepsLayout = (LinearLayout) this.findViewById(R.id.details_wrap_steps);

        for (int x = 0; x < 3; x++) {
            final LinearLayout newView = (LinearLayout) this.getLayoutInflater().inflate(R.layout.recipe_details_row, null);
            newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView ingredientView = (TextView) newView.findViewById(R.id.detail_recipe_text);

            ingredientView.setText("Ingredient #" + (x + 1));
            ingredientsLayout.addView(newView);
        }

        for (int x = 0; x < 3; x++) {
            final LinearLayout newView = (LinearLayout) this.getLayoutInflater().inflate(R.layout.recipe_details_row, null);
            newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView stepsView = (TextView) newView.findViewById(R.id.detail_recipe_text);

            stepsView.setText("This is step #" + (x + 1));
            stepsLayout.addView(newView);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
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

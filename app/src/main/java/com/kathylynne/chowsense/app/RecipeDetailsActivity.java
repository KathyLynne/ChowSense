package com.kathylynne.chowsense.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class RecipeDetailsActivity extends ActionBarActivity {


    public TextView title;
    public TextView description;



    //public TextView description = (TextView)findViewById(R.id.recipe_detail_description);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        title = (TextView) findViewById(R.id.recipe_detail_title);
        description = (TextView) findViewById(R.id.recipe_detail_description);

        final LinearLayout ingredientsLayout = (LinearLayout) this.findViewById(R.id.details_wrap_ingredients);
        final LinearLayout stepsLayout = (LinearLayout) this.findViewById(R.id.details_wrap_steps);

        String recipeID = "HUCyJ0WNVZ";

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
        query.getInBackground(recipeID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Recipe recipe = (Recipe) object;
                    title.setText(recipe.getTitle());
                    description.setText(recipe.getDescription());
                    ArrayList<String> steps = recipe.getSteps();
                    ArrayList<Ingredient> ingredients = recipe.getIngredients();


                    for (int x = 0; x < ingredients.size(); x++) {
                        final LinearLayout newView = (LinearLayout) RecipeDetailsActivity.this.getLayoutInflater().inflate(R.layout.recipe_details_row, null);
                        newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        TextView ingredientView = (TextView) newView.findViewById(R.id.detail_recipe_text);

                        ingredientView.setText(ingredients.get(x).getMeasure() + " of " + ingredients.get(x).getName());
                        ingredientsLayout.addView(newView);
                    }


                    for (int x = 0; x < steps.size(); x++) {
                        final LinearLayout newView = (LinearLayout) RecipeDetailsActivity.this.getLayoutInflater().inflate(R.layout.recipe_details_row, null);
                        newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        TextView stepsView = (TextView) newView.findViewById(R.id.detail_recipe_text);

                        stepsView.setText("Step #" + (x + 1) + "\n" + steps.get(x));
                        stepsLayout.addView(newView);
                    }


                } else {
                    //error
                }
            }
        });

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

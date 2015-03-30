package com.kathylynne.chowsense.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.parse.*;

import java.util.ArrayList;


public class AddRecipeActivity extends DrawerActivity {

    ImageButton btnAdd;
    ImageButton btnAddStep;

    EditText title;

    EditText description;
    ArrayList<String> steps = new ArrayList<String>();
    public String recipeId;
    Button btnSave;

    final ParseObject recipe = new ParseObject("Recipe");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_recipe);


        //initialize fields
        //TODO these keys need to be verified
        Parse.initialize(this, "qJwvg8qtJEb7FnzU1ygRwgdUkGp7Bgh2oV8m2yWP", "TTfQmmrAbfBFu9IGxOQb6oeSvEWLo8TliM6kgj8a");
        title = (EditText) findViewById(R.id.titleText);
        description = (EditText) findViewById(R.id.descriptionText);
        //for dynamic item add
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnAddStep = (ImageButton) findViewById(R.id.btnAddStep);

        btnSave = (Button) findViewById(R.id.saveRecipeButton);
        //Tag for logging
        final String tag = "tag";

        recipe.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    recipeId = recipe.getObjectId();
                    //show the recipeId is not null in logCat
                    Log.i(tag, "object id, " + recipeId);
                } else {
                    //didn't work
                }
            }
        });

        recipeId = recipe.getObjectId();
        //Toast.makeText(this, recipeId, Toast.LENGTH_SHORT).show();

        //call to make the dynamic ingredient and step buttons inflate
        add(this, btnAdd);
        addSteps(this, btnAddStep);


    }




    public void saveRecipe(View v) {

        //put the steps into the array
        LinearLayout stepsScrollViewLinearLayout = (LinearLayout) findViewById(R.id.stepLinearLayoutForm);
        for (int i = 0; i < stepsScrollViewLinearLayout.getChildCount(); i++) {
            LinearLayout innerLayout = (LinearLayout) stepsScrollViewLinearLayout.getChildAt(i);
            EditText stepField = (EditText) innerLayout.findViewById(R.id.stepText);
            String stepToSave = stepField.getText().toString();
            steps.add(stepToSave);

        }


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");

        // Retrieve the object by id
        query.getInBackground(recipeId, new GetCallback<ParseObject>() {
            public void done(ParseObject recipe, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    recipe.put("RecipeTitle", title.getText().toString());
                    recipe.put("RecipeDescription", description.getText().toString());
                    recipe.put("RecipeSteps", steps);
                    recipe.saveInBackground();
                }
            }
        });

        //save the ingredients that are present at the time of buttonClick
        LinearLayout scrollViewLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutForm);
        for (int i = 0; i < scrollViewLinearLayout.getChildCount(); i++) {
            ParseObject ingredient = new ParseObject("Ingredient");
            LinearLayout innerLayout = (LinearLayout) scrollViewLinearLayout.getChildAt(i);
            EditText name = (EditText) innerLayout.findViewById(R.id.editDescricao);
            EditText qty = (EditText) innerLayout.findViewById(R.id.qtyText);
            String iName = name.getText().toString();
            //add steps here
            String iQty = qty.getText().toString();
            ingredient.put("IngredientName", iName);
            //concatenate measure put to have the measure as well.
            ingredient.put("Measure", iQty);
            ingredient.put("RecipeId", recipeId);
            ingredient.saveInBackground();

        }


        Toast.makeText(this, "Recipe Saved!", Toast.LENGTH_SHORT).show();
    } // end save method


    private void add(final Activity activity, ImageButton btn) {
        //TODO find a way to turn this into ingredient fields..I believe in us~!
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.rowdetail, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);


                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                    }
                });
                linearLayoutForm.addView(newView);
            }
        });
    }

    private void addSteps(final Activity activity, ImageButton btn) {
        //TODO find a way to turn this into ingredient fields..I believe in us~!
        final LinearLayout stepsLinearLayoutForm = (LinearLayout) activity.findViewById(R.id.stepLinearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.step_rowdetail, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);


                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        stepsLinearLayoutForm.removeView(newView);
                    }
                });

                stepsLinearLayoutForm.addView(newView);
            }
        });
    }


}

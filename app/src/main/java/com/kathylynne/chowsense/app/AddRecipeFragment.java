package com.kathylynne.chowsense.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;

/**
 * Created by Kate on 2015-03-29.
 */
public class AddRecipeFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout layout;
    private LinearLayout rowLayout;
    ImageButton btnAdd;
    ImageButton btnAddStep;
    Spinner spinner;
    EditText title;
    String spinnerChoice;
    EditText description;
    ArrayList<String> steps = new ArrayList<String>();
    ArrayList<Ingredient> ingToRecipe = new ArrayList<Ingredient>();
    public String recipeId;
    Button btnSave;
    final String tag = "tag";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_add_recipe, container, false);
        title = (EditText) layout.findViewById(R.id.titleText);
        description = (EditText) layout.findViewById(R.id.descriptionText);
        //for dynamic item add
        btnAdd = (ImageButton) layout.findViewById(R.id.btnAdd);
        btnAddStep = (ImageButton) layout.findViewById(R.id.btnAddStep);

        btnSave = (Button) layout.findViewById(R.id.saveRecipeButton);
        btnSave.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initialize fields

        //Tag for logging



        //Toast.makeText(this, recipeId, Toast.LENGTH_SHORT).show();


        //call to make the dynamic ingredient and step buttons inflate
        add(getActivity(), btnAdd);
        addSteps(getActivity(), btnAddStep);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveRecipeButton:
                final Recipe recipe = new Recipe();
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

                recipeId = recipe.getRecipeId();
                //put the steps into the array
                LinearLayout stepsScrollViewLinearLayout = (LinearLayout) layout.findViewById(R.id.stepLinearLayoutForm);
                for (int i = 0; i < stepsScrollViewLinearLayout.getChildCount(); i++) {
                    LinearLayout innerLayout = (LinearLayout) stepsScrollViewLinearLayout.getChildAt(i);
                    EditText stepField = (EditText) innerLayout.findViewById(R.id.stepText);
                    String stepToSave = stepField.getText().toString();
                    steps.add(stepToSave);

                }
                recipe.setTitle(title.getText().toString());
                recipe.setDescription(description.getText().toString());
                recipe.setSteps(steps);
                recipe.setUser();


/*                ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
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
                });*/

                //save the ingredients that are present at the time of buttonClick
                LinearLayout scrollViewLinearLayout = (LinearLayout) layout.findViewById(R.id.linearLayoutForm);
                for (int i = 0; i < scrollViewLinearLayout.getChildCount(); i++) {
                    Ingredient ingredient = new Ingredient();
                    LinearLayout innerLayout = (LinearLayout) scrollViewLinearLayout.getChildAt(i);
                    EditText name = (EditText) innerLayout.findViewById(R.id.editDescricao);
                    EditText qty = (EditText) innerLayout.findViewById(R.id.qtyText);
                    String iName = name.getText().toString();
                    //add steps here
                    String iQty = qty.getText().toString();
                    ingredient.setName(iName);
                    //concatenate measure put to have the measure as well.
                    ingredient.setMeasure(iQty + " " + spinnerChoice);
                    ingredient.setRecipeID(recipeId);
                    ingToRecipe.add(ingredient);
                    ingredient.saveInBackground();
                }

                recipe.setIngredients(ingToRecipe);
                recipe.saveInBackground();
                Toast.makeText(getActivity(), "Recipe Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    private void add(final Activity activity, ImageButton btn) {
        //TODO find a way to turn this into ingredient fields..I believe in us~!
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //String[] measures = getResources().getStringArray(R.array.measurement_array);
                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.rowdetail, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                //set up the spinner
                spinner = (Spinner) newView.findViewById(R.id.spinner);
                ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.measurement_array, android.R.layout.simple_spinner_item);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int option = spinner.getSelectedItemPosition();
                        spinnerChoice = (String) parent.getItemAtPosition(option);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

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

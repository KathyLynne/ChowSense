package com.kathylynne.chowsense.app;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.*;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailsFragment extends Fragment implements View.OnClickListener {


    public static final String RECIPE_ID_PARAM = "Recipe ID for details: ";

    // private String recipeId;
    public TextView title;
    public TextView description;
    private String recipeID;
    public ParseImageView imageView;
    private RelativeLayout layout;
    private String userId;
    public TextView favoritesText;
    public ImageView favoritesImage;


    public static RecipeDetailsFragment newInstance(String param, String recipeId) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(RECIPE_ID_PARAM, recipeId);

        fragment.setArguments(args);
        return fragment;
    }

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeID = getArguments().getString(RECIPE_ID_PARAM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_recipe_details, container, false);

        title = (TextView) layout.findViewById(R.id.frag_recipe_detail_title);
        description = (TextView) layout.findViewById(R.id.frag_recipe_detail_description);
        TextView ingredientsTitle = (TextView) layout.findViewById(R.id.frag_details_ingredients_title);
        TextView stepTitle = (TextView) layout.findViewById(R.id.frag_details_steps_title);
        ingredientsTitle.setTextColor(Color.BLACK);
        stepTitle.setTextColor(Color.BLACK);
        title.setTextColor(Color.BLACK);
        favoritesImage = (ImageView) layout.findViewById(R.id.frag_detail_favorite_image);
        favoritesImage.setOnClickListener(this);
        favoritesText = (TextView) layout.findViewById(R.id.frag_textView_favorites);
        favoritesText.setOnClickListener(this);
        imageView = (ParseImageView) layout.findViewById(R.id.frag_details_image);
        userId = ParseUser.getCurrentUser().getObjectId();

        final LinearLayout ingredientsLayout = (LinearLayout) layout.findViewById(R.id.frag_details_wrap_ingredients);
        final LinearLayout stepsLayout = (LinearLayout) layout.findViewById(R.id.frag_details_wrap_steps);

        //recipeID = "1bSWA4Gsaa";

        ParseQuery<ParseObject> FavQuery = ParseQuery.getQuery("Favorites");
        FavQuery.whereEqualTo("UserId", userId);
        FavQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    ArrayList<String> favorites = (ArrayList<String>) object.get("RecipeId");
                    if (favorites != null) {
                        for (int x = 0; x < favorites.size(); x++) {
                            if (favorites.get(x).equals(recipeID)) {
                                favoritesImage.setImageResource(R.drawable.favorite_true);
                                favoritesText.setText(getText(R.string.favorites_true));
                                break;
                            }
                        }
                    }
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
        query.getInBackground(recipeID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Recipe recipe = (Recipe) object;
                    title.setText(recipe.getTitle());
                    description.setText(recipe.getDescription());
                    // image.setParseFile(recipe.getPhoto());

                    ParseFile image = recipe.getPhoto();
                    imageView.setParseFile(image);
                    imageView.loadInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                        }
                    });


                    ArrayList<String> steps = recipe.getSteps();
                    // ArrayList<Ingredient> ingredients = recipe.getIngredients();

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Ingredient");
                    query.whereEqualTo("RecipeId", recipeID);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objectList, ParseException e) {
                            if (e == null) {
                                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

                                for (ParseObject p : objectList) {
                                    ingredients.add((Ingredient) p);
                                }

                                for (int x = 0; x < ingredients.size(); x++) {
                                    final LinearLayout newView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.recipe_details_row, null);
                                    newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    TextView ingredientView = (TextView) newView.findViewById(R.id.detail_recipe_text);

                                    ingredientView.setText(ingredients.get(x).getMeasure() + " " + ingredients.get(x).getName());
                                    ingredientsLayout.addView(newView);
                                }
                            } else {
                                Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });


                    for (int x = 0; x < steps.size(); x++) {
                        final LinearLayout newView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.recipe_details_row, null);
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

        return layout;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frag_detail_favorite_image || v.getId() == R.id.frag_textView_favorites) {
            ParseQuery<ParseObject> FavQuery = ParseQuery.getQuery("Favorites");
            FavQuery.whereEqualTo("UserId", userId);
            FavQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        favoritesText.setText(getText(R.string.favorites_true));

                        ArrayList<String> favorites = (ArrayList<String>) object.get("RecipeId");
                        boolean favorite = false;
                        if (favorites != null) {
                            for (int x = 0; x < favorites.size(); x++) {
                                if (favorites.get(x).equals(recipeID)) {
                                    favorites.remove(x);
                                    favorite = true;
                                    favoritesImage.setImageResource(R.drawable.favorite_false);
                                    favoritesText.setText(getText(R.string.favorites_false));
                                    //break;
                                }
                            }

                            if (!favorite) {
                                favorites.add(recipeID);
                                favoritesImage.setImageResource(R.drawable.favorite_true);
                                favoritesText.setText(getText(R.string.favorites_true));
                            }
                        } else {
                            favorites = new ArrayList<String>();
                            favorites.add(recipeID);
                            favoritesImage.setImageResource(R.drawable.favorite_true);
                            favoritesText.setText(getText(R.string.favorites_true));
                        }
                        object.put("RecipeId", favorites);
                        object.saveInBackground();
                    }
                }
            });
        }
    }
}

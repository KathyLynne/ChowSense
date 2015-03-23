package com.kathylynne.chowsense.app;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate on 2015-03-23.
 */
@ParseClassName("Recipe")
public class Recipe extends ParseObject {
    //parse requires blank default constructor.
    public Recipe() {
    }

    //initialize lists to contain Ingredient
    public List<Ingredient> ingredients = new ArrayList<Ingredient>();
    //public HashSet<String> ingredientHash = new HashSet<String>();

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);

        //ingredientHash.add(ingredient.getObjectId());
    }

    //this should create the ingredients array in Parse.
    public void setIngredients() {
        put("recipeIngredients", ingredients);
    }

    //add ingredient to the list
    public void addIngredients(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    //pull the JSON array from the Cloud
    public JSONArray getIngredientsArray() {
        return getJSONArray("ingredientName");
    }

    public String getDescription() {
        return getString("recipeDescription");
    }

    public void setDescription(String description) {
        put("recipeDescription", description);
    }

    public String getTitle() {
        return getString("recipeTitle");
    }

    public void setTitle(String title) {
        put("recipeTitle", title);
    }

    public ParseUser getAuthor() {
        return getParseUser("userName");
    }

    public void setAuthor(ParseUser user) {
        put("UserName", user);
    }

    public String getRating() {
        return getString("recipeRating");
    }

    public void setRating(String rating) {
        put("recipeRating", rating);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
}

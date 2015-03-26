package com.kathylynne.chowsense.app;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;

@ParseClassName("Recipe")
public class Recipe extends ParseObject {

    public Recipe() {
        //default constructor
    }

    public String getTitle() {
        return getString("RecipeTitle");
    }

    public void setTitle(String title) {
        put("RecipeTitle", title);
    }

    public String getDescription() {
        return getString("RecipeTitle");
    }

    public void setDescription(String description) {
        put("RecipeDescription", description);
    }

    public ArrayList getSteps() {
        return (ArrayList<String>) get("RecipeSteps");
    }

    public void setSteps(ArrayList<String> steps) {
        put("RecipeSteps", steps);
    }

    public ParseFile getPhoto() {
        return getParseFile("picture");
    }

    public void setPhoto(ParseFile file) {
        put("photo", file);
    }

    public ArrayList<Ingredient> getIngredients() {
        return (ArrayList<Ingredient>) get("Ingredients");
    }

    public void setIngredients(ArrayList<Ingredient> ingredientList) {
        put("Ingredients", ingredientList);
    }

}
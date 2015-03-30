package com.kathylynne.chowsense.app.model;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Ingredient")
public class Ingredient extends ParseObject {

    public Ingredient() {
        //default
    }

    public String getName() {
        return getString("IngredientName");
    }

    public void setName(String name) {
        put("IngredientName", name);
    }

    public String getMeasure() {
        return getString("Measure");
    }

    public void setMeasure(String measure) {
        put("Measure", measure);
    }

    public String getRecipeID() {
        return getString("RecipeID");
    }

    public void setRecipeID(String recipeID) {
        put("RecipeId", recipeID);
    }

}

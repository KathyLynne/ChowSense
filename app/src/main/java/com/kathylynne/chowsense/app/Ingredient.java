package com.kathylynne.chowsense.app;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Kate on 2015-03-23.
 */
@ParseClassName("Ingredient")
public class Ingredient extends ParseObject {
    public Ingredient() {
    }

    public void setRecipeId(String id) {
        put("recipeId", id);
    }

    public String getRecipeId() {
        return getString("recipeId");
    }

    public void setMeasure(String measure) {
        put("measure", measure);
    }

    public String getMeasure() {
        return getString("measure");
    }

    public void setIngredientName(String name) {
        put("ingredientName", name);
    }

    public String getIngredientName() {
        return getString("ingredientName");
    }

}

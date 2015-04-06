package com.kathylynne.chowsense.app.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Kate on 2015-04-06.
 */
@ParseClassName("Favorites")
public class Favorites extends ParseObject {

    public Favorites() {
    }

    public void setUserId() {
        put("UserId", ParseUser.getCurrentUser().getObjectId().toString());
    }

    public String getUserId() {
        return getString("UserId");
    }

    public String getDescription() {
        return getString("RecipeDescription");
    }

    public void setDescription(String description) {
        put("RecipeDescription", description);
    }

    public ArrayList getFavorites() {
        return (ArrayList<String>) get("RecipeId");
    }

    public void setFavorites(ArrayList<String> faves) {
        put("RecipeId", faves);
    }


}

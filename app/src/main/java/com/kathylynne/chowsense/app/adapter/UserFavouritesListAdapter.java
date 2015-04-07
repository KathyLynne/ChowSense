package com.kathylynne.chowsense.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kathylynne.chowsense.app.R;
import com.kathylynne.chowsense.app.model.Favorites;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Kate on 2015-04-06.
 */
public class UserFavouritesListAdapter extends ParseQueryAdapter {

    public static ArrayList<ParseObject> userFavouritesArray;
    private static final String TAG = "Query Failure";


    public UserFavouritesListAdapter(final Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                String user = ParseUser.getCurrentUser().getObjectId();
                ParseQuery<ParseObject> query = new ParseQuery(Favorites.class);
                ParseQuery<ParseObject> recipeQuery = new ParseQuery(Recipe.class);
                query.whereEqualTo("UserId", user);

                try {
                    Favorites f = (Favorites) query.getFirst();
                    userFavouritesArray = f.getFavorites();
                    //Log.i(TAG, userFavouritesArray.get(0).toString());

                    recipeQuery.whereContainedIn("objectId", (Collection) userFavouritesArray);

                } catch (ParseException e) {
                    Log.i(TAG, e.toString());
                }
                return recipeQuery;
            }

        });
    }

    public static class ViewHolder {
        TextView titleTV;
        TextView descriptionTV;
        ParseImageView photoIV;
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {

        ViewHolder holder;

        if (v == null) {
            v = View.inflate(getContext(), R.layout.layout_fragment_recipe_list, null);
            super.getItemView(object, v, parent);

            holder = new ViewHolder();

            holder.titleTV = (TextView) v.findViewById(R.id.recipe_list_row_title);
            holder.descriptionTV = (TextView) v.findViewById(R.id.recipe_list_row_Description);
            holder.photoIV = (ParseImageView) v.findViewById(R.id.recipe_list_row_image);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }
        //v.setVisibility(View.VISIBLE);


        //ParseImageView recipeImage = (ParseImageView) v.findViewById(R.id.recipe_list_row_image);
        ParseFile imageFile = object.getParseFile("RecipePhoto");
        if (imageFile != null) {
            holder.photoIV.setParseFile(imageFile);
            holder.photoIV.loadInBackground();
        }

        // Add the title view
        //TextView titleTextView = (TextView) v.findViewById(R.id.recipe_list_row_title);
        holder.titleTV.setText(object.getString("RecipeTitle"));

        // Add a reminder of how long this item has been outstanding
        //TextView timestampView = (TextView) v.findViewById(R.id.recipe_list_row_Description);
        holder.descriptionTV.setText(object.getString("RecipeDescription"));

        return v;
    }


}

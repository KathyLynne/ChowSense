package com.kathylynne.chowsense.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kathylynne.chowsense.app.R;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.*;

/**
 * Created by Kate on 2015-04-09.
 */
public class BrowseQueryAdapter extends ParseQueryAdapter {

    public BrowseQueryAdapter(Context context) {
        // Use the QueryFactory to construct a PQA that will only show

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery recipeQuery = new ParseQuery(Recipe.class);
                recipeQuery.orderByDescending("createdAt");
                recipeQuery.setLimit(500);


                return recipeQuery;
            }
        });
    }


    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.layout_fragment_recipe_list, null);
        }

        super.getItemView(object, v, parent);


        //left in for reference.
        // Add and download the image
        ParseImageView recipeImage = (ParseImageView) v.findViewById(R.id.recipe_list_row_image);
        ParseFile imageFile = object.getParseFile("RecipePhoto");
        if (imageFile != null) {
            recipeImage.setParseFile(imageFile);
            recipeImage.loadInBackground();
        }

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.recipe_list_row_title);
        titleTextView.setText(object.getString("RecipeTitle"));

        // Add a reminder of how long this item has been outstanding
        TextView timestampView = (TextView) v.findViewById(R.id.recipe_list_row_Description);
        timestampView.setText(object.getString("RecipeDescription"));

        //ImageView imageView = (ImageView) v.findViewById(R.id.recipe_list_row_image);
        //imageView.

        return v;
    }
}



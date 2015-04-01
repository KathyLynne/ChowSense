package com.kathylynne.chowsense.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kathylynne.chowsense.app.R;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by Kate on 2015-03-31.
 */
public class UserListQueryAdapter extends ParseQueryAdapter {

    public UserListQueryAdapter(Context context) {
        // Use the QueryFactory to construct a PQA that will only show

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery(Recipe.class);
                query.whereEqualTo("UserId", ParseUser.getCurrentUser().getUsername().toString());
                return query;
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

        // Add and download the image
        /*ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile imageFile = object.getParseFile("image");
        if (imageFile != null) {
            todoImage.setParseFile(imageFile);
            todoImage.loadInBackground();
        }*/

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.recipe_list_row_title);
        titleTextView.setText(object.getString("RecipeTitle"));

        // Add a reminder of how long this item has been outstanding
        TextView timestampView = (TextView) v.findViewById(R.id.recipe_list_row_Description);
        timestampView.setText(object.getString("RecipeDescription"));

        return v;
    }

}

package com.kathylynne.chowsense.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.*;

import java.util.ArrayList;
import java.util.List;

//import com.kathylynne.chowsense.app.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class RecipeFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String USER_PARAM = "UserName";
    public static final String FAVOURITE_PARAM = "UserFavourites";
    public static final String SEARCH_PARAM = "Search";
    //private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String userName;
    private String mParam2;

    private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

    private OnFragmentInteractionListener mListener;


    /**
     * Instantiation settings for the fragment, handling the passing of the bundles
     */

    public static RecipeFragment newInstance(String parameter) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();

        args.putString(USER_PARAM, parameter);


        fragment.setArguments(args);
        return fragment;
    }

    //overload the newInstance method to accept the agrument for the list as well.
    public static RecipeFragment newInstance(ArrayList<Ingredient> parameter) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        //TODO
        //could this be serializable array?  is it parcelable array?
        //args.put(USER_PARAM, parameter);


        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Custom adapter
     */
    private class RecipeListAdapter extends ArrayAdapter<Recipe> {
        public RecipeListAdapter(ArrayList<Recipe> recipeList) {
            super(getActivity(), 0, recipeList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_fragment_recipe_list, null);
            }
            //Recipe r = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.recipe_list_row_title);
            TextView descTextView = (TextView) convertView.findViewById(R.id.recipe_list_row_Description);

            return convertView;
        }
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {

            userName = getArguments().getString(USER_PARAM);
            //get query from user name
            ParseQuery<Recipe> myRecipeQuery = ParseQuery.getQuery(Recipe.class);
            myRecipeQuery.whereContains("UserId", ParseUser.getCurrentUser().get("username").toString());
            myRecipeQuery.findInBackground(new FindCallback<Recipe>() {
                @Override
                public void done(List<Recipe> list, ParseException e) {
                    final String TAG = "QueryError";
                    if (e == null) {
                        for (Recipe r : list) {
                            Log.d(TAG, recipeList.get(0).toString());
                            recipeList.add(r);

                            // TODO: Change Adapter to display your content
                            //RecipeListAdapter adapter = new RecipeListAdapter(recipeList);
                            //setListAdapter(adapter);
                        }
                    } else {

                        Log.d(TAG, e.getMessage());
                    }

                }
            });
            final ParseQueryAdapter adapter = new ParseQueryAdapter(getActivity().getApplicationContext(), Recipe.class);


        }

        // TODO: Change Adapter to display your content
        //RecipeListAdapter adapter = new RecipeListAdapter(recipeList);
        //setListAdapter(adapter);
        //setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //        android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
    }


/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }


}

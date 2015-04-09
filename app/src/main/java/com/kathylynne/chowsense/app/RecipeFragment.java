package com.kathylynne.chowsense.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import com.kathylynne.chowsense.app.adapter.SearchListQueryAdapter;
import com.kathylynne.chowsense.app.adapter.UserFavouritesListAdapter;
import com.kathylynne.chowsense.app.adapter.UserListQueryAdapter;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

public class RecipeFragment extends ListFragment {


    public static final String USER_PARAM = "UserName";
    public static final String FAVOURITE_PARAM = "UserFavourites";
    public static final String SEARCH_INGREDIENT_PARAM = "Ingredients";

    private String userName;

    private String favourites;
    private ArrayList<String> ingredientSearchItems;




    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * Instantiation settings for the fragment, handling the passing of the bundles
     */

    public static RecipeFragment newInstance(String key, String parameter) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(key, parameter);

        fragment.setArguments(args);
        return fragment;
    }

    //overload the newInstance method to accept the agrument for the list as well.
    public static RecipeFragment newInstance(String key, ArrayList<String> parameter) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(key, parameter);
        fragment.setArguments(args);
        return fragment;
    }






    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().containsKey(USER_PARAM)) {
                userName = getArguments().getString(USER_PARAM);
            } else if (getArguments().containsKey(FAVOURITE_PARAM)) {
                favourites = getArguments().getString(FAVOURITE_PARAM);
            } else if (getArguments().containsKey(SEARCH_INGREDIENT_PARAM)) {
                ingredientSearchItems = new ArrayList<String>(getArguments().getStringArrayList(SEARCH_INGREDIENT_PARAM));


            }
        }
    }
    ParseQueryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipelist, container, false);
        if (userName != null) {
            adapter = new UserListQueryAdapter(getActivity());
        } else if (ingredientSearchItems != null) {
            adapter = new SearchListQueryAdapter(getActivity());
        } else if (favourites != null) {
            adapter = new UserFavouritesListAdapter(getActivity());
        }
        adapter.getCount();
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(adapter);
        // mListView.getCount();
        //mListView.removeViewAt();
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        super.onListItemClick(list, v, position, id);
        Recipe r = (Recipe) adapter.getItem(position);
        String rId = r.getRecipeId();
        Fragment fragment = RecipeDetailsFragment.newInstance(RecipeDetailsFragment.RECIPE_ID_PARAM, rId);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


}

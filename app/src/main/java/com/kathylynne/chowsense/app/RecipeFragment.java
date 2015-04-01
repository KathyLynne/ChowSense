package com.kathylynne.chowsense.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.kathylynne.chowsense.app.adapter.UserListQueryAdapter;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * TODO implement OnFragmentInteractionListener in Drawer
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class RecipeFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String USER_PARAM = "UserName";
    public static final String FAVOURITE_PARAM = "UserFavourites";
    public static final String SEARCH_PARAM = "Search";
    public static final String SEARCH_INGREDIENT_PARAM = "Ingredients";
    //private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String userName;
    private String favourites;
    private String recipeTitle;
    private ArrayList<String> ingredientSearchItems;

    //private ListAdapter mAdapter;

    //private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

    private OnFragmentInteractionListener mListener;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().containsKey(USER_PARAM)) {
                userName = getArguments().getString(USER_PARAM);
            } else if (getArguments().containsKey(FAVOURITE_PARAM)) {
                favourites = getArguments().getString(USER_PARAM);
            } else if (getArguments().containsKey(SEARCH_PARAM)) {
                recipeTitle = getArguments().getString(SEARCH_PARAM);
            } else if (getArguments().containsKey(SEARCH_INGREDIENT_PARAM))
                ingredientSearchItems = new ArrayList<String>(getArguments().getStringArrayList(SEARCH_INGREDIENT_PARAM));
        }
    }
    ParseQueryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipelist, container, false);
        if (userName != null) {
            adapter = new UserListQueryAdapter(getActivity());


        }

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(adapter);
        // Set OnItemClickListener so we can be notified on item clicks
        //mListView.setOnItemClickListener();

        return view;
    }


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
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
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

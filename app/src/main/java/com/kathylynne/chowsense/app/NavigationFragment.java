package com.kathylynne.chowsense.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.parse.ParseUser;

/**
 * Created by Kate on 2015-03-29.
 */

public class NavigationFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout layout;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userName = ParseUser.getCurrentUser().getUsername().toString();
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_navigation, container, false);
        LinearLayout searchLayout = (LinearLayout) layout.findViewById(R.id.searchImageButtonLayout);
        searchLayout.setOnClickListener(this);
        LinearLayout addLayout = (LinearLayout) layout.findViewById(R.id.addImageButtonLayout);
        addLayout.setOnClickListener(this);
        LinearLayout favLayout = (LinearLayout) layout.findViewById(R.id.favouriteImageButtonLayout);
        favLayout.setOnClickListener(this);
        LinearLayout myLayout = (LinearLayout) layout.findViewById(R.id.myImageButtonLayout);
        myLayout.setOnClickListener(this);
        ImageButton searchButton = (ImageButton) layout.findViewById(R.id.searchImageButton);
        searchButton.setOnClickListener(this);
        ImageButton addButton = (ImageButton) layout.findViewById(R.id.addImageButton);
        addButton.setOnClickListener(this);
        ImageButton favouriteButton = (ImageButton) layout.findViewById(R.id.favouriteImageButton);
        favouriteButton.setOnClickListener(this);
        ImageButton myButton = (ImageButton) layout.findViewById(R.id.myImageButton);
        myButton.setOnClickListener(this);

        return layout;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //fragment controller logic goes here. (what would go in onCreate for an Activity)
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        String stackName = null;

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        switch (v.getId()) {
            case R.id.searchImageButtonLayout:
                stackName = "Menu";
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fragment = new RecipeSearchFragment();
                getActivity().setTitle("Search");
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.addImageButtonLayout:
                stackName = "Menu";
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fragment = new AddRecipeFragment();
                getActivity().setTitle("Add Recipe");
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.favouriteImageButtonLayout:
                stackName = "Menu";
                fragment = RecipeFragment.newInstance(RecipeFragment.FAVOURITE_PARAM, userName);
                getActivity().setTitle("Favourites");
                ft.replace(R.id.frame_container, fragment);

                break;
            case R.id.myImageButtonLayout:
                stackName = "Menu";
                fragment = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);
                getActivity().setTitle("My Recipes");
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.searchImageButton:
                stackName = "Menu";
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fragment = new RecipeSearchFragment();
                getActivity().setTitle("Search");
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.addImageButton:
                stackName = "Menu";
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fragment = new AddRecipeFragment();
                getActivity().setTitle("Add Recipe");
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.favouriteImageButton:
                stackName = "Menu";
                fragment = RecipeFragment.newInstance(RecipeFragment.FAVOURITE_PARAM, userName);
                getActivity().setTitle("Favourites");
                ft.replace(R.id.frame_container, fragment);

                break;
            case R.id.myImageButton:
                stackName = "Menu";
                fragment = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);
                getActivity().setTitle("My Recipes");
                ft.replace(R.id.frame_container, fragment);
                break;
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(stackName);
        ft.commit();

    }


}

package com.kathylynne.chowsense.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.parse.ParseUser;

/**
 * Created by Kate on 2015-03-29.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout layout;
    private FragmentActivity frag;
    private String userName = ParseUser.getCurrentUser().getUsername().toString();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //frag = super.getActivity();
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_navigation, container, false);

        ImageButton searchButton = (ImageButton) layout.findViewById(R.id.searchImageButton);
        ImageButton addButton = (ImageButton) layout.findViewById(R.id.addImageButton);
        ImageButton favouritesButton = (ImageButton) layout.findViewById(R.id.favouriteImageButton);
        ImageButton myButton = (ImageButton) layout.findViewById(R.id.myImageButton);
        searchButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        favouritesButton.setOnClickListener(this);
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
            case R.id.searchImageButton:
                stackName = "Menu";
                fragment = new RecipeSearchFragment();
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.addImageButton:
                stackName = "Menu";
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fragment = new AddRecipeFragment();
                ft.replace(R.id.frame_container, fragment);
                break;
            case R.id.favouriteImageButton:
                stackName = "Menu";
                fragment = RecipeFragment.newInstance(RecipeFragment.FAVOURITE_PARAM, userName);
                ft.replace(R.id.frame_container, fragment);

                break;
            case R.id.myImageButton:
                stackName = "Menu";
                fragment = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);
                ft.replace(R.id.frame_container, fragment);
                break;
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(stackName);
        ft.commit();

    }

}

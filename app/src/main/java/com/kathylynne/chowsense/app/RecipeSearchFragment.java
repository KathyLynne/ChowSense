package com.kathylynne.chowsense.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;


public class RecipeSearchFragment extends Fragment implements View.OnClickListener {



    private RelativeLayout layout;
    private ImageButton addIngredient;
    private Button searchButton;
    public static ArrayList<String> search;


    public static RecipeSearchFragment newInstance(String key, ArrayList searchTerms) {
        RecipeSearchFragment fragment = new RecipeSearchFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(RecipeFragment.SEARCH_INGREDIENT_PARAM, search);

        fragment.setArguments(args);
        return fragment;
    }

    public RecipeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addSearchTerm(getActivity(), addIngredient);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_recipe_search, container, false);
        addIngredient = (ImageButton) layout.findViewById(R.id.addSearchTerm);

        searchButton = (Button) layout.findViewById(R.id.findRecipesButton);
        search = new ArrayList<String>();
        searchButton.setOnClickListener(this);
        return layout;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.findRecipesButton) {
            Fragment fragment = RecipeFragment.newInstance(RecipeFragment.SEARCH_INGREDIENT_PARAM, search);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            LinearLayout searchLayoutForm = (LinearLayout) layout.findViewById(R.id.searchRowPlaceHolder);

            for (int i = 0; i < searchLayoutForm.getChildCount(); i++) {
                LinearLayout innerLayout = (LinearLayout) searchLayoutForm.getChildAt(i);
                EditText stepField = (EditText) innerLayout.findViewById(R.id.searchIngredient);
                String stepToSave = stepField.getText().toString().trim();
                search.add(stepToSave);
            }

            ft.replace(R.id.frame_container, fragment);
            ft.commit();
        }
    }

    private void addSearchTerm(final Activity activity, ImageButton btn) {

        final LinearLayout searchLayoutForm = (LinearLayout) activity.findViewById(R.id.searchRowPlaceHolder);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.search_row, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.searchRowRemove);

                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        searchLayoutForm.removeView(newView);
                    }
                });

                searchLayoutForm.addView(newView);
            }
        });
    }

}

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
    private String mParam1;
    private String mParam2;
    private ImageButton addIngredient;
    private Button searchButton;
    public static ArrayList<String> search;
    public static String[] searchTerms;

    //private OnFragmentInteractionListener mListener;


    public static RecipeSearchFragment newInstance(String param1, ArrayList param2) {
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
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

   /* @Override
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
        //mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.findRecipesButton) {
            Fragment fragment = RecipeFragment.newInstance(RecipeFragment.SEARCH_INGREDIENT_PARAM, search);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //switch (v.getId()) {

            //  case R.id.findRecipesButton:


            LinearLayout searchLayoutForm = (LinearLayout) layout.findViewById(R.id.searchRowPlaceHolder);
            //searchTerms = new  String[searchLayoutForm.getChildCount() -1];
            for (int i = 0; i < searchLayoutForm.getChildCount(); i++) {
                LinearLayout innerLayout = (LinearLayout) searchLayoutForm.getChildAt(i);
                EditText stepField = (EditText) innerLayout.findViewById(R.id.searchIngredient);
                String stepToSave = stepField.getText().toString().trim();
                search.add(stepToSave);
                //searchTerms[i] = stepToSave;

            }
            // break;


            //case R.id.findRecipesButton:


            //}
            ft.replace(R.id.frame_container, fragment);
            ft.commit();
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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/
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

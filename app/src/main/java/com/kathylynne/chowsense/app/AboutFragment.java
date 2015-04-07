package com.kathylynne.chowsense.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * Created by Kate on 2015-04-07.
 */
public class AboutFragment extends Fragment {


    private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //frag = super.getActivity();
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_about, container, false);

        return layout;
    }
}

package com.kathylynne.chowsense.app;

/**
 * Created by Kate on 2015-03-23.
 */

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

import java.util.ArrayList;

public class LayoutOperation {

    public static void display(final Activity activity, Button btn, final String rID) {
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LinearLayout scrollViewLinearLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
                java.util.ArrayList<String> msg = new ArrayList<String>();

                for (int i = 0; i < scrollViewLinearLayout.getChildCount(); i++) {
                    // Ingredient flavour = new Ingredient();
                    LinearLayout innerLayout = (LinearLayout) scrollViewLinearLayout.getChildAt(i);
                    EditText name = (EditText) innerLayout.findViewById(R.id.editDescricao);
                    EditText qty = (EditText) innerLayout.findViewById(R.id.qtyText);
                    //Spinner spinner = (Spinner) innerLayout.findViewById(R.id.spinner);
                    //TODO try to isolate dynamic field IDs with the following line...
                    //msg.add(edit.getResources().getResourceName(R.id.editDescricao));
                    msg.add(name.getText() + ", " + qty.getText() + ": " + rID);


                }
                Toast.makeText(activity.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void add(final Activity activity, ImageButton btn) {
        //TODO find a way to turn this into ingredient fields..I believe in us~!
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.rowdetail, null);
                newView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);

                //remove ingredient here.
                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                    }
                });
                linearLayoutForm.addView(newView);
            }
        });
    }

    //to save the ingredients, place method in here, to be called in the saveButton method in the AddRecipeActivity.
    public static void save() {
    }
}

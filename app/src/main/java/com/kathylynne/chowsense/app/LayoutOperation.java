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

    public static void display(final Activity activity, Button btn) {
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
                java.util.ArrayList<String> msg = new ArrayList<String>();

                for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++) {
                    // Ingredient flavour = new Ingredient();
                    LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
                    EditText edit = (EditText) innerLayout.findViewById(R.id.editDescricao);
                    //EditText qty = (EditText) innerLayout.findViewById(R.id.qtyText);
                    //Spinner info as above soon
                    //TODO try to isolate dynamic field IDs with the following line...
                    // flavour.setMeasure(qty.getText().toString());
                    // flavour.setIngredientName(edit.getText().toString());
                    // tastes.add(flavour);
                    msg.add(edit.getResources().getResourceName(R.id.editDescricao));


                    //currently pops the same editDescricao id.  SO weird. So how do we get this put into the array? of ingredients to pass into JSON

                }
                Toast t = Toast.makeText(activity.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT);
                t.show();
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
}

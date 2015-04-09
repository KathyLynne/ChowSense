package com.kathylynne.chowsense.app;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout layout;
    private RelativeLayout dogeLayout;
    private AnimationDrawable dogeAnimation;
    private ImageView doge;
    private TextView dogeText1;
    private TextView dogeText2;
    private TextView dogeText3;
    private TextView dogeText4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_settings, container, false);

        doge = (ImageView) layout.findViewById(R.id.doge_settings);
        dogeLayout = (RelativeLayout) layout.findViewById(R.id.doge_layout);
        dogeText1 = (TextView) layout.findViewById(R.id.dogeText1);
        dogeText2 = (TextView) layout.findViewById(R.id.dogeText2);
        dogeText3 = (TextView) layout.findViewById(R.id.dogeText3);
        dogeText4 = (TextView) layout.findViewById(R.id.dogeText4);

        doge.setOnClickListener(this);
        dogeLayout.setOnClickListener(this);
        dogeText1.setOnClickListener(this);
        dogeText2.setOnClickListener(this);
        dogeText3.setOnClickListener(this);
        dogeText4.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doge_settings:
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1000);
                doge.startAnimation(rotate);
                break;
            case R.id.doge_layout:
                Toast.makeText(getActivity(), "Denied, woof.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dogeText1:
                dogeLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.dogeText2:
                dogeLayout.setBackgroundColor(Color.BLACK);
                break;
            case R.id.dogeText3:
                dogeLayout.setBackgroundColor(Color.YELLOW);
                break;
            case R.id.dogeText4:
                dogeLayout.setBackgroundColor(Color.BLUE);
                break;
        }
    }


}

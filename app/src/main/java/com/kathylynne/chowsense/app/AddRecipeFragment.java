package com.kathylynne.chowsense.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kate on 2015-03-29.
 */
public class AddRecipeFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout layout;
    ImageButton btnAdd;
    ImageButton btnAddStep;
    Spinner spinner;
    EditText title;
    EditText description;
    ArrayList<String> steps = new ArrayList<String>();
    ArrayList<Ingredient> ingToRecipe = new ArrayList<Ingredient>();
    public String recipeId;
    Button btnSave;

    ImageButton cameraBtn;
    public ImageView photoView;
    public Bitmap photo;
    public static final int TAKE_PICTURE = 1888;
    Recipe recipe = new Recipe();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.fragment_add_recipe, container, false);
        title = (EditText) layout.findViewById(R.id.titleText);
        description = (EditText) layout.findViewById(R.id.descriptionText);
        btnAdd = (ImageButton) layout.findViewById(R.id.btnAdd);
        btnAddStep = (ImageButton) layout.findViewById(R.id.btnAddStep);
        btnSave = (Button) layout.findViewById(R.id.saveRecipeButton);
        btnSave.setOnClickListener(this);

        cameraBtn = (ImageButton) layout.findViewById(R.id.cameraBtn);
        photoView = (ImageView) layout.findViewById(R.id.cameraImgView);

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String tag = "tag";
        //for some reason, has to stay here.
        if (recipeId == null) {
            recipe.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //worked
                    } else {
                        //didn't work
                    }
                }
            });
        }
        recipeId = recipe.getRecipeId();
        add(getActivity(), btnAdd);
        addSteps(getActivity(), btnAddStep);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File filePicture = new File(pictureFolder, "temp.jpg");
/*
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

                if (filePicture.exists()) {
                    Uri outPutFileUri = Uri.fromFile(filePicture);
                    Intent intentStartCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentStartCamera.putExtra(MediaStore.EXTRA_OUTPUT, outPutFileUri);
                    getActivity().startActivity(intentStartCamera);
                } else {
                    Toast.makeText(getActivity(), "There was a problem creating the file", Toast.LENGTH_SHORT).show();
                }

                if (filePicture.exists()) {
                    photo = decodeImage(filePicture.getPath());
                } else {
                    Toast.makeText(getActivity(), "There was a problem loading the image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Bitmap decodeImage(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeFile(filePath, options);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DrawerActivity.photo != null) {
            photoView.setImageBitmap(DrawerActivity.photo);
        }
    }

/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveRecipeButton:
                //put the steps into the array
                LinearLayout stepsScrollViewLinearLayout = (LinearLayout) layout.findViewById(R.id.stepLinearLayoutForm);
                for (int i = 0; i < stepsScrollViewLinearLayout.getChildCount(); i++) {
                    LinearLayout innerLayout = (LinearLayout) stepsScrollViewLinearLayout.getChildAt(i);
                    EditText stepField = (EditText) innerLayout.findViewById(R.id.stepText);
                    String stepToSave = stepField.getText().toString();
                    steps.add(stepToSave);

                }

                String photoName = title.getText().toString().trim().replaceAll("[\\\\-\\\\+\\\\.\\\\^:,]", "") + ".jpg";


                recipe.setTitle(title.getText().toString());
                recipe.setDescription(description.getText().toString());
                recipe.setSteps(steps);
                recipe.setUser();

                if (DrawerActivity.photo != null) {


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    DrawerActivity.photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bitMapData = stream.toByteArray();

                    ParseFile imgFile = new ParseFile(photoName, bitMapData);
                    recipe.setPhoto(imgFile);
                }

                //save the ingredients that are present at the time of buttonClick
                LinearLayout scrollViewLinearLayout = (LinearLayout) layout.findViewById(R.id.linearLayoutForm);
                for (int i = 0; i < scrollViewLinearLayout.getChildCount(); i++) {
                    final Ingredient ingredient = new Ingredient();

                    LinearLayout innerLayout = (LinearLayout) scrollViewLinearLayout.getChildAt(i);
                    EditText name = (EditText) innerLayout.findViewById(R.id.editDescricao);
                    EditText qty = (EditText) innerLayout.findViewById(R.id.qtyText);
                    Spinner measure = (Spinner) innerLayout.findViewById(R.id.spinner);

                    String choice = measure.getSelectedItem().toString();
                    String iName = name.getText().toString();
                    String iQty = qty.getText().toString();
                    ingredient.setName(iName);
                    //concatenate measure put to have the measure as well.
                    ingredient.setMeasure(iQty + " " + choice);
                    ingredient.setRecipeID(recipeId);
                    ingredient.saveInBackground();
                    ingToRecipe.add(ingredient);

                }

                recipe.setIngredients(ingToRecipe);
                recipe.saveInBackground();
                Toast.makeText(getActivity(), "Recipe Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void add(final Activity activity, ImageButton btn) {

        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.rowdetail, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                //set up the spinner
                spinner = (Spinner) newView.findViewById(R.id.spinner);
                ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.measurement_array, android.R.layout.simple_spinner_item);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);


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

    private void addSteps(final Activity activity, ImageButton btn) {
        //TODO find a way to turn this into ingredient fields..I believe in us~!
        final LinearLayout stepsLinearLayoutForm = (LinearLayout) activity.findViewById(R.id.stepLinearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.step_rowdetail, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);


                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        stepsLinearLayoutForm.removeView(newView);
                    }
                });

                stepsLinearLayoutForm.addView(newView);
            }
        });
    }
}

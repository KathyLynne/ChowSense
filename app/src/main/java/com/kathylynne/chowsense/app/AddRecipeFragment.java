package com.kathylynne.chowsense.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kathylynne.chowsense.app.model.Ingredient;
import com.kathylynne.chowsense.app.model.Recipe;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

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
    // ArrayList<Ingredient> ingToRecipe = new ArrayList<Ingredient>();
    public String recipeId;
    Button btnSave;
    public File output;
    ImageButton cameraBtn;
    public ImageView photoView;
    public Bitmap photo;
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
        //for some reason, has to stay here.

 /*       if (recipeId == null) {

            recipe.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        recipeId = recipe.getRecipeId();
                    } else {
                        //didn't work
                    }
                }
            });
        }*/
        add(getActivity(), btnAdd);
        addSteps(getActivity(), btnAddStep);
        btnAdd.performClick();
        btnAddStep.performClick();

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Chowsense");
                if (!imagesFolder.exists()) {
                    imagesFolder.mkdirs();
                }
                UUID pictureID = UUID.randomUUID();
                String fileName = "image_" + pictureID.toString() + ".jpg";
                output = new File(imagesFolder, fileName);
/*
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

                Uri outPutFileUri = Uri.fromFile(output);
                    Intent intentStartCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentStartCamera.putExtra(MediaStore.EXTRA_OUTPUT, outPutFileUri);
                    getActivity().startActivity(intentStartCamera);

            }
        });

    }

    private Bitmap formatImage(String filePath, int maxW, int maxH) {
        Bitmap image;
        try {
            image = BitmapFactory.decodeFile(filePath);

            int maxWidth = maxW;
            int maxHeight = maxH;

            if (image.getHeight() > image.getWidth()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
            }

            image = Bitmap.createScaledBitmap(image, maxWidth, maxHeight, true);

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Photo not taken!", Toast.LENGTH_SHORT).show();
            image = null;
            if (output.exists()) {
                output.delete();
            }
        }

        return image;
    }

    @Override
    public void onResume() {
        super.onResume();
        String Tag = "help with camera:";
        if (output != null) {
            photo = formatImage(output.getPath(), 1024, 600);
            Log.i(Tag, "output is not null: " + output.getPath());
            if (photo != null) {
                photoView.setImageBitmap(photo);
                Log.i(Tag, "photo is not null, should have decoded");
            }
        } else {
            Log.i(Tag, "output is null");
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


                recipe.setTitle(title.getText().toString());
                recipe.setDescription(description.getText().toString());
                recipe.setSteps(steps);
                recipe.setUser();

//<<<<<<< HEAD


//=======


//>>>>>>> 9379b63050a2bf3f13f36aa7579d38797d4e4704
                //save the ingredients that are present at the time of buttonClick

                Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_LONG).show();
                btnSave.setVisibility(View.GONE);

                final String tag = "tag";
                boolean success = false;

                //recipe.saveInBackground();

                String photoName = title.getText().toString().trim().replaceAll("[\\\\-\\\\+\\\\.\\\\^:,!?<>']", "") + ".jpg";

                if (photo != null) {


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byte[] bitMapData = stream.toByteArray();

                    ParseFile imgFile = new ParseFile(photoName, bitMapData);
                    recipe.setPhoto(imgFile);
                }

                recipe.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            recipeId = recipe.getRecipeId();
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
                                //ingToRecipe.add(ingredient);

                            }
                            Toast.makeText(getActivity(), "Recipe Saved!", Toast.LENGTH_SHORT).show();

                            String userName = ParseUser.getCurrentUser().get("username").toString();
                            Fragment fragment = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);

                            if (fragment != null) {
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, fragment).commit();
                            } else {
                                // error in creating fragment
                                Log.e("MainActivity", "Error in creating fragment");
                            }

                        } else {
                            Toast.makeText(getActivity(), "Save Failed...Try Again Later.\n\n" + e.toString(), Toast.LENGTH_LONG).show();
                            btnSave.setVisibility(View.VISIBLE);
                        }
                    }
                });

                /*LinearLayout scrollViewLinearLayout = (LinearLayout) layout.findViewById(R.id.linearLayoutForm);
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
                    //ingToRecipe.add(ingredient);

                }
*/
                /*do {
                    try {
                        recipe.save();
                        Toast.makeText(getActivity(), "Recipe Saved!", Toast.LENGTH_SHORT).show();
                        success = true;

                    } catch (com.parse.ParseException ex) {
                        success = false;
                        Log.e(tag, "EXCEPTION, TRYING AGAIN..." + ex.toString());
                    }
                }while(!success);
*/


               /* while(!verifyUpload(recipeId)){
                            recipe.saveInBackground();
                            Log.i(tag,"Looping save #" + i);
                            i++;
                }*/

/*                recipe.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                Toast.makeText(getActivity(), "Recipe Saved!", Toast.LENGTH_SHORT).show();
                    }
                });                */
 /*       String userName = ParseUser.getCurrentUser().get("username").toString();
        Fragment fragment = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();
                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }*/

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
                ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.measurement_array, R.layout.custom_spinner);
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

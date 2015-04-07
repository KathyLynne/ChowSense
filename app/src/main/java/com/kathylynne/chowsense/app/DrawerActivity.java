package com.kathylynne.chowsense.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.kathylynne.chowsense.app.adapter.NavDrawerListAdapter;
import com.kathylynne.chowsense.app.model.NavDrawerItem;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Kate on 2015-03-28.
 */
public class DrawerActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items from string array in values/strings.xml
    private String[] navMenuTitles;
    private FragmentManager fragmentManager = getFragmentManager();
    private ArrayList<NavDrawerItem> navDrawerItems;

    private NavDrawerListAdapter adapter;
    //static Bitmap photo;
    private static final String TAG = "logging errors(On Drawer)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        actionBar = getSupportActionBar();
        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_sliderMenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        //add Drawer items individually.  The matching resources found are listed in values/strings.xml but cannot be called directly form there as the
        //method is deprecated.  List left in strings for time saving in the future.
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], android.R.drawable.ic_menu_compass));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], android.R.drawable.ic_menu_search));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], android.R.drawable.ic_menu_add));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], android.R.drawable.star_off));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], android.R.drawable.ic_menu_view));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], android.R.drawable.ic_menu_info_details));


        //begin click functionality in drawer
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {

                actionBar.setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // switch/case to handle action bar actions click ONLY
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_bar_search:
                displayView(1);
                return true;

            case R.id.action_bar_logout:

                ParseUser.logOut();
                ParseUser user = ParseUser.getCurrentUser();
                if (user == null) {
                    Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    finish();
                }
                return true;
            //TODO settings
            //case R.id.action_bar_settings:
            //the fragment to launch
            //return true


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        actionBar.setTitle(mTitle);
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddRecipeFragment.CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            if(photo.getHeight() > photo.getWidth()){
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                photo = Bitmap.createBitmap(photo , 0, 0, photo .getWidth(), photo .getHeight(), matrix, true);
            }
        }
    }*/

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {

        String userName = ParseUser.getCurrentUser().get("username").toString();
        //most efficient here, only called when needed
        // update the main content by replacing fragments
        Fragment fragment = null;
        //RecipeFragment rFrag = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        switch (position) {
            case 0:
                fragment = new NavigationFragment();

                break;
            case 1:
                fragment = new RecipeSearchFragment();
                break;
            case 2:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                fragment = new AddRecipeFragment();
                break;
            case 3:
                //String userName = ParseUser.getCurrentUser().get("username").toString();
                fragment = RecipeFragment.newInstance(RecipeFragment.FAVOURITE_PARAM, userName);
                break;
            case 4:
                // fragment = new PagesFragment();
                fragment = RecipeFragment.newInstance(RecipeFragment.USER_PARAM, userName);
                break;
            case 5:
                //fragment = new WhatsHotFragment();
                break;


            default:
                fragment = new NavigationFragment();
                break;
        }


        if (fragment != null) {

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }




}
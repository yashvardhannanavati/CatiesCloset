package com.example.yashnanavati.catiescloset.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.yashnanavati.catiescloset.DonationModule.UserProfileActivity;
import com.example.yashnanavati.catiescloset.Fragments.BottomBarAdapter;
import com.example.yashnanavati.catiescloset.Fragments.DashBoardFragment;
import com.example.yashnanavati.catiescloset.Fragments.DummyFragment;
import com.example.yashnanavati.catiescloset.Fragments.NoSwipePager;
import com.example.yashnanavati.catiescloset.R;

public class HomeActivity extends AppCompatActivity {

    private final int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.cornflower_blue};

    private Toolbar toolbar;
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;

    private boolean notificationVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle("Catie's Closet");


        setupViewPager();

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setupBottomNavBehaviors();
        setupBottomNavStyle();

        //createFakeNotification();

        addBottomNavigationItems();
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (!wasSelected)
                    viewPager.setCurrentItem(position);

                // remove notification badge
                int lastItemPos = bottomNavigation.getItemsCount() - 1;
                if (notificationVisible && position == lastItemPos)
                    bottomNavigation.setNotification(new AHNotification(), lastItemPos);

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Toast.makeText(HomeActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Redirecting the user to settings if GPS is turned Off on the device
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int off = 0;
                    try {
                        off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(off==0){
                        Toast.makeText(getApplicationContext(), "Redirecting to Settings!", Toast.LENGTH_SHORT).show();
                        Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(onGPS);
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "We will require your location to assist you in Donating!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

    //Checking if Location Permissions are granted
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

    private void setupViewPager() {
        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(createFragmentHome(1));
        pagerAdapter.addFragments(createFragment(2));
        pagerAdapter.addFragments(createFragment(3));
        pagerAdapter.addFragments(createFragment(4));

        viewPager.setAdapter(pagerAdapter);
    }

    @NonNull
    private DashBoardFragment createFragmentHome(int tab) {
        DashBoardFragment dfragment = new DashBoardFragment();
        dfragment.setArguments(passFragmentArguments(tab));
        return dfragment;
    }

    @NonNull
    private DummyFragment createFragment(int tab) {
        DummyFragment fragment = new DummyFragment();
        fragment.setArguments(passFragmentArguments(tab));
        return fragment;
    }

    @NonNull
    private Bundle passFragmentArguments(int tab) {
        Bundle bundle = new Bundle();
        bundle.putInt("tab_index", tab);
        return bundle;
    }

    public void setupBottomNavBehaviors() {
        bottomNavigation.setTranslucentNavigationEnabled(false);
    }

    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.

        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.cornflower_blue_dark));
        bottomNavigation.setInactiveColor(fetchColor(R.color.colorBottomNavigationInactive));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(fetchColor(R.color.cornflower_blue_dark),
                fetchColor(R.color.colorBottomNavigationInactive));

        //  Enables Reveal effect
        //bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }


    /**
     * Adds (items) {@link AHBottomNavigationItem} to {@link AHBottomNavigation}
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_home_black_24dp, colors[2]);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_donate, R.drawable.icon_donate, colors[2]);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.title_dashboard, R.drawable.ic_dashboard_black_24dp, colors[2]);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_aboutUs, R.drawable.icon_aboutus, colors[2]);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item3);
    }


    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

}

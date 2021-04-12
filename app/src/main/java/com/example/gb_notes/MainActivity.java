package com.example.gb_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gb_notes.bussiness_logic.GetPublisher;
import com.example.gb_notes.bussiness_logic.Publisher;
import com.example.gb_notes.ui.ContentNotesFragment;
import com.example.gb_notes.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements GetPublisher {
    private boolean isLandscape;
    private Publisher publisher = new Publisher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContentNotesFragment(new ContentNotesFragment());
        initView();
    }

    private void initView() {
        Toolbar toolbar = initToolBar();
        initDrawer(toolbar);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav_drawer, R.string.close_navigation_toolbar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 int id = item.getItemId();
                 switch (id){
                     case R.id.action_settings_drawer:
                         addFragment(new SettingsFragment());
                         drawerLayout.closeDrawer(GravityCompat.START);
                         return true;
                     case R.id.action_main_drawer:
                         addFragment(new ContentNotesFragment());
                         drawerLayout.closeDrawer(GravityCompat.START);
                         return true;
                 }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search_menu);
        android.widget.SearchView searchView = (android.widget.SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
         return  true;
    };

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         int id = item.getItemId();
         switch (id){
             case R.id.settingsMenu:
                 addFragment(new SettingsFragment());
                 return  true;
             case R.id.main_menu:
                 addFragment(new ContentNotesFragment());
                 return true;
         }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Fragment settingsFragment) {
        initFragmentTransaction(settingsFragment);
    }

    private void initContentNotesFragment(Fragment contentNotesFragment) {
        initFragmentTransaction(contentNotesFragment);
    }

    private void initFragmentTransaction(Fragment fragment) {
        int viewOrientation = isLandscape ? R.id.contentListFragmentLand : R.id.contentListFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentListFragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}
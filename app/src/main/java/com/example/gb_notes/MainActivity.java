package com.example.gb_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

import java.util.List;

import static androidx.appcompat.widget.SearchView.*;

public class MainActivity extends AppCompatActivity implements GetPublisher {
    private boolean isLandscape;
    private Publisher publisher = new Publisher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initContentNotesFragment(new ContentNotesFragment());
    }

    private void initView() {
        initToolBar();
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        fragmentTransaction.replace(viewOrientation, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}
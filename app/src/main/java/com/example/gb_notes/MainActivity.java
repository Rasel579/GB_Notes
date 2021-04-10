package com.example.gb_notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gb_notes.bussiness_logic.GetPublisher;
import com.example.gb_notes.bussiness_logic.Publisher;

import static androidx.appcompat.widget.SearchView.*;

public class MainActivity extends AppCompatActivity implements GetPublisher {
    private Publisher publisher = new Publisher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initToolBar();
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

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}
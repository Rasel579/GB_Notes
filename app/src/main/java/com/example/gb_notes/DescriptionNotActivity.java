package com.example.gb_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SearchRecentSuggestionsProvider;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.gb_notes.bussiness_logic.Note;

public class DescriptionNotActivity extends AppCompatActivity {
    private Note note;
    public static  String NOTE ="Note";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_notea_activiy);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if (savedInstanceState == null){
           DescriptionNoteFragment descriptionNoteFragment = new DescriptionNoteFragment();
           descriptionNoteFragment.setArguments(getIntent().getExtras());
           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentDescriptionForLayout, descriptionNoteFragment)
                   .commit();
        }
    }
}
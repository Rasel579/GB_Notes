package com.example.gb_notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gb_notes.bussiness_logic.GetPublisher;
import com.example.gb_notes.bussiness_logic.Publisher;

public class MainActivity extends AppCompatActivity implements GetPublisher {
    private Publisher publisher = new Publisher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentNotesFragment contentNotesFragment = new ContentNotesFragment();
        publisher.subscribe(contentNotesFragment);



    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}
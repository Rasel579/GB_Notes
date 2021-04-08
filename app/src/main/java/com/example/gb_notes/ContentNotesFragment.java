package com.example.gb_notes;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gb_notes.bussiness_logic.Note;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentNotesFragment#} factory method to
 * create an instance of this fragment.
 */
public class ContentNotesFragment extends Fragment {

    private static final String ARG_NOTE = "Note";
    public static Note currentNote;
    private boolean isLandscape;

    public ContentNotesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);


    }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.noteName);
        String[] data = getResources().getStringArray(R.array.dateCreated);
        for (int i = 0; i < notes.length ; i++) {
            TextView textView = new TextView(getContext());
            TextView dataText = new TextView(getContext());
            textView.setText(notes[i]);
            textView.setTextSize(20);
            dataText.setText(data[i]);
            dataText.setTextSize(20);
            linearLayout.addView(dataText);
            linearLayout.addView(textView);
            int  index = i;
            textView.setOnClickListener(view1 -> {
                currentNote = new Note(getResources().getStringArray(R.array.noteName)[index], getResources().getStringArray(R.array.noteDescription)[index], index);
                showDescriptionNote(currentNote);
        });
      }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(ARG_NOTE, currentNote);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null){
            currentNote = savedInstanceState.getParcelable(ARG_NOTE);
        } else {
            currentNote = new Note(getResources().getStringArray(R.array.noteName)[0], getResources().getStringArray(R.array.noteDescription)[0], 0);
        }

        if (isLandscape){
            System.out.println("LAAAANf");
            showLandDescription(currentNote);
        }

    }

    private void showDescriptionNote(Note currentNote) {
        if (isLandscape){
            showLandDescription(currentNote);

        } else{
            showPortDescription(currentNote);
        }

    }

    private void showLandDescription(Note currentNote) {
        DescriptionNoteFragment descriptionNoteFragment = DescriptionNoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager =  requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.descriptionNoteFrg, descriptionNoteFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    private void showPortDescription(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(requireActivity(), DescriptionNotActivity.class);
        intent.putExtra(ARG_NOTE, currentNote);
        startActivity(intent);

    }

}
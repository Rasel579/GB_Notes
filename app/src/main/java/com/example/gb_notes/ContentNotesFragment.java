package com.example.gb_notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gb_notes.bussiness_logic.Note;
import com.example.gb_notes.bussiness_logic.NotesData;
import com.example.gb_notes.bussiness_logic.Observer;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentNotesFragment#} factory method to
 * create an instance of this fragment.
 */
public class ContentNotesFragment extends Fragment implements Observer {
    private TextView dataTextView;
    private TextView nameTextView;
    private static final String NOTES = "CurrentNote";
    private static NotesData notesData = new NotesData();
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContentNotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ContentNotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  static ContentNotesFragment newInstance(){
        ContentNotesFragment contentNotesFragment = new ContentNotesFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTES, notesData);
        contentNotesFragment.setArguments(args);
        return contentNotesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(getArguments() != null){
            notesData = getArguments().getParcelable(NOTES);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        index = notesData.getNotesData().size() - 1;
        View view = inflater.inflate(R.layout.fragment_content_notes, container, false);
        if (notesData.getNotesData().size() != 0){

                Date date = notesData.getNotesData().get(index).getDate();
                String name = notesData.getNotesData().get(index).getName();
                TextView dataView = new TextView(getContext());
                TextView nameView = new TextView(getContext());
                dataView.setText(String.valueOf(date));
                nameView.setText(name);
                dataView.setTextSize(20);
                nameView.setTextSize(20);
                ((LinearLayout) view).addView(dataView);
                ((LinearLayout) view).addView(nameView);

        }

//        if (savedInstanceState.getParcelable(CURRENT_NOTE) != null){
//            dataTextView.setText(String.valueOf(note.getDate()));
//            nameTextView.setText(note.getName());
//        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void updateListOfFragments(Note note) {
        notesData.getNotesData().add(note);
    }
}
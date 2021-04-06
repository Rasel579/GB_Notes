package com.example.gb_notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gb_notes.bussiness_logic.Note;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionNoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOTE = "note";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int index;
    private Note note;

    public DescriptionNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment DescriptionNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DescriptionNoteFragment newInstance(Note note) {
        DescriptionNoteFragment fragment = new DescriptionNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description_note, container, false);
        if (savedInstanceState != null) {
            note = savedInstanceState.getParcelable(ARG_NOTE);
            TextView nameDescription = view.findViewById(R.id.nameOfNoteForDescription);
            TextView descriptionNote = view.findViewById(R.id.descriptionNote);
            nameDescription.setText(note.getName());
            descriptionNote.setText(note.getDescription());
        }
        return view;
    }
}
package com.example.gb_notes;

import android.os.Bundle;

import androidx.annotation.Nullable;
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
    private static final String ARG_NOTE = "Note";
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
        System.out.println(note + " newInstance");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
            System.out.println(note + "1111");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description_note, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if(getArguments() == null){
            note = new Note(getResources().getStringArray(R.array.noteName)[0], getResources().getStringArray(R.array.noteDescription)[0], 0);
        }
        System.out.println(note);
        TextView nameTextView = view.findViewById(R.id.nameOfNoteForDescription);
        TextView textDescriptionView = view.findViewById(R.id.textDescription);
        nameTextView.setText(note.getName());
        textDescriptionView.setText(note.getDescription());


    }
}
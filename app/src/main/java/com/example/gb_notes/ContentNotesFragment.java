package com.example.gb_notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
    public  static ContentNotesFragment newInstance(Note note){
        notesData.getNotesData().add(note);
        ContentNotesFragment contentNotesFragment = new ContentNotesFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTES, notesData);
        contentNotesFragment.setArguments(args);
        return contentNotesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            notesData = getArguments().getParcelable(NOTES);
            System.out.println(notesData.getNotesData().get(0).toString());

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        index = notesData.getNotesData().size() - 1;
        View view = inflater.inflate(R.layout.fragment_content_notes, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (notesData.getNotesData().size() != 0){
            for (int i = 0; i <notesData.getNotesData().size() ; i++) {
                Date date = notesData.getNotesData().get(i).getDate();
                String name = notesData.getNotesData().get(i).getName();
                TextView dataView = new TextView(getContext());
                TextView nameView = new TextView(getContext());
                dataView.setText(String.valueOf(date));
                nameView.setText(name);
                dataView.setTextSize(20);
                nameView.setTextSize(20);
                ((LinearLayout) view).addView(dataView);
                ((LinearLayout) view).addView(nameView);
            }

        }

//        if (savedInstanceState.getParcelable(CURRENT_NOTE) != null){
//            dataTextView.setText(String.valueOf(note.getDate()));
//            nameTextView.setText(note.getName());
//        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NOTES, notesData);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        View view = new LinearLayout(getContext());
        for (int i = 0; i <notesData.getNotesData().size() ; i++) {
            Date date = notesData.getNotesData().get(i).getDate();
            String name = notesData.getNotesData().get(i).getName();
            TextView dataView = new TextView(getContext());
            TextView nameView = new TextView(getContext());
            dataView.setText(String.valueOf(date));
            nameView.setText(name);
            dataView.setTextSize(20);
            nameView.setTextSize(20);
            ((LinearLayout) view).addView(dataView);
            ((LinearLayout) view).addView(nameView);
            index = i;
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DescriptionNoteFragment detail = DescriptionNoteFragment.newInstance(notesData.getNotesData().get(index));

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentDescription, detail);  // замена фрагмента
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
            });

        }
    }

    @Override
    public void updateListOfFragments(Note note) {
        notesData.getNotesData().add(note);
    }
}
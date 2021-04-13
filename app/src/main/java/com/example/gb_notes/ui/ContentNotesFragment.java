package com.example.gb_notes.ui;


import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.gb_notes.R;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.data.NoteSource;
import com.example.gb_notes.data.NoteSourceImpl;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentNotesFragment#} factory method to
 * create an instance of this fragment.
 */
public class ContentNotesFragment extends Fragment {

    private static final String ARG_NOTE = "Note";
    private final int SIZE = 3;
    public static Note currentNote;
    private boolean isLandscape;

    public ContentNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_content_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_layout);
        NoteSource noteSource = new NoteSourceImpl(getResources()).init();
        //    recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final ContentNotesAdapter contentNotesAdapter = new ContentNotesAdapter(noteSource);
        recyclerView.setAdapter(contentNotesAdapter);
        contentNotesAdapter.setItemClickListener(new ContentNotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int resources) {
                if (resources == R.id.noteDate) {
                    initPopup(view, noteSource.getNote(position), position);
                } else {
                    currentNote = noteSource.getNote(position);
                    showDescriptionNote(currentNote);
                }
            }
        });
    }

    private void initPopup(View view1, Note note, int position) {
        Activity activity = requireActivity();
        PopupMenu popupMenu = new PopupMenu(activity, view1);
        activity.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            switch (id) {
                case R.id.changePopup:
                    showUpdateFragment(note, position);
                    return true;
                case R.id.donePopup:
                    Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.deletePopup:
                    Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return true;
        });
        popupMenu.show();

    }

    private void showUpdateFragment(Note note, int position) {
        initFragmentTransaction(note, position);
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
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(ARG_NOTE);
        } else {
            currentNote = new Note(getResources().getStringArray(R.array.noteName)[0], getResources().getStringArray(R.array.noteDescription)[0], 0);
        }

        if (isLandscape) {
            showLandDescription(currentNote);
        }

    }

    private void showDescriptionNote(Note currentNote) {
        if (isLandscape) {
            showLandDescription(currentNote);

        } else {
            showPortDescription(currentNote);
        }

    }

    private void showLandDescription(Note currentNote) {
        initFragmentTransaction(currentNote, isLandscape);
    }

    private void initFragmentTransaction(Note currentNote, boolean isLandscape) {
        DescriptionNoteFragment descriptionNoteFragment = DescriptionNoteFragment.newInstance(currentNote);
        int viewId = isLandscape ? R.id.descriptionNoteFrgLand : R.id.contentListFragment;
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(viewId, descriptionNoteFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void initFragmentTransaction(Note currentNote, int position) {
        UpdateFragment updateFragment = UpdateFragment.newInstance(currentNote, position);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentListFragment, updateFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void showPortDescription(Note currentNote) {
        initFragmentTransaction(currentNote, isLandscape);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.content_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
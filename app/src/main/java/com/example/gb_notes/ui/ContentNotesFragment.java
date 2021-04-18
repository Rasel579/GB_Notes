package com.example.gb_notes.ui;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.gb_notes.MainActivity;
import com.example.gb_notes.R;
import com.example.gb_notes.bussiness_logic.Observer;
import com.example.gb_notes.bussiness_logic.Publisher;
import com.example.gb_notes.data.Navigation;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.data.NoteSource;
import com.example.gb_notes.data.NoteSourceImpl;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentNotesFragment#} factory method to
 * create an instance of this fragment.
 */
public class ContentNotesFragment extends Fragment {

    private static final String ARG_NOTE = "Note";
    public static Note currentNote;
    private boolean isLandscape;
    private NoteSource noteSource;
    private Navigation navigation;
    private Publisher publisher;
    private ContentNotesAdapter contentNotesAdapter;
    private static final String ARG_POSITION = "position";
    private boolean moveToLastPosition;

    public ContentNotesFragment() {
        // Required empty public constructor
    }

    public static ContentNotesFragment newInstance(){
        ContentNotesFragment fragment = new ContentNotesFragment();
        return fragment;
    }

    public  static ContentNotesFragment newInstance(Note currentNote, int position){
        ContentNotesFragment fragment = new ContentNotesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, currentNote);
        args.putInt(ARG_POSITION, position);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteSource = new NoteSourceImpl(getResources()).init();
        if(getArguments() != null){
            currentNote = getArguments().getParcelable(ARG_NOTE);
            int position = getArguments().getInt(ARG_POSITION);
            noteSource.addNote(currentNote);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_content_notes, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_layout);
        //    recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
         contentNotesAdapter = new ContentNotesAdapter(noteSource, this);
        recyclerView.setAdapter(contentNotesAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.nav_bar_side, null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);
        if(moveToLastPosition){
            recyclerView.smoothScrollToPosition(noteSource.size() - 1);
            moveToLastPosition = false;
        }


        contentNotesAdapter.setItemClickListener((view1, position, resources) -> {
            if (resources == R.id.noteDate) {
                initPopup(view1, noteSource.getNote(position), position);
            } else {
                currentNote = noteSource.getNote(position);
                showDescriptionNote(currentNote);
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
        navigation.addFragment(UpdateFragment.newInstance(note, position), true);
        publisher.subscribe(new Observer() {
            @Override
            public void updateNoteSource(Note note) {
                noteSource.updateNote(note, position);
                contentNotesAdapter.notifyItemChanged(position);
                moveToLastPosition  = true;
            }
        });
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
            currentNote = new Note(getResources().getStringArray(R.array.noteName)[0], getResources().getStringArray(R.array.noteDescription)[0], 0, new Date());
        }

//        if (isLandscape) {
//            navigation.addFragment(DescriptionNoteFragment.newInstance(currentNote), true, isLandscape);
//        }

    }

    private void showDescriptionNote(Note currentNote) {
        navigation.addFragment(DescriptionNoteFragment.newInstance(currentNote), true, isLandscape);
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
                addFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void  addFragment(){
        navigation.addFragment(UpdateFragment.newInstance(), true);
        publisher.subscribe(new Observer() {
            @Override
            public void updateNoteSource(Note note) {
                noteSource.addNote(note);
                contentNotesAdapter.notifyItemChanged(noteSource.size() - 1);
                moveToLastPosition= true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = contentNotesAdapter.getMenuPosition();
        switch (item.getItemId()){
            case R.id.action_add_context:
                addFragment();
                Toast.makeText(getContext(), "ADD", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_update_context:
                showUpdateFragment(currentNote, position);
                Toast.makeText(getContext(), "Update", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
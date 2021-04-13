package com.example.gb_notes.data;

import android.content.res.Resources;

import com.example.gb_notes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private List<Note> noteList;
    private Resources resources;

    public NoteSourceImpl(Resources resources) {
        this.resources = resources;
        noteList = new ArrayList<Note>();
    }

    public NoteSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.noteName);
        String[] descriptions = resources.getStringArray(R.array.noteDescription);
        for (int i = 0; i < titles.length; i++) {
            noteList.add(new Note(titles[i], descriptions[i], i));
        }
        return this;
    }

    @Override
    public Note getNote(int position) {
        return noteList.get(position);
    }

    @Override
    public int size() {
        return noteList.size();
    }

    @Override
    public void changeNote(Note note, int position) {
        noteList.set(position, note);
    }
}

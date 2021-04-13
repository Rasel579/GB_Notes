package com.example.gb_notes.data;

public interface NoteSource {
    Note getNote(int position);
    int size ();
    void changeNote(Note note, int position);
}

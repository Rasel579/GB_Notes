package com.example.gb_notes.data;

public interface NoteSource {
    Note getNote(int position);

    int size();

    void updateNote(Note note, int position);
    void addNote(Note note);
    void removeNote(int position);

}

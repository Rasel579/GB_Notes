package com.example.gb_notes.bussiness_logic;

import com.example.gb_notes.data.Note;

public interface Observer {
    void updateNoteSource(Note note);
}

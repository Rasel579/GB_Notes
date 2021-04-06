package com.example.gb_notes.bussiness_logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NotesData implements Parcelable {
    ArrayList<Note> notesData = new ArrayList<Note>();

    public NotesData(Parcel in) {
        notesData = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NotesData> CREATOR = new Creator<NotesData>() {
        @Override
        public NotesData createFromParcel(Parcel in) {
            return new NotesData(in);
        }

        @Override
        public NotesData[] newArray(int size) {
            return new NotesData[size];
        }
    };

    public NotesData() {
        this.notesData = new ArrayList<Note>();
    }

    public ArrayList<Note> getNotesData() {
        return notesData;
    }

    public void setNotesData(ArrayList<Note> notesData) {
        this.notesData = notesData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(notesData);
    }
}

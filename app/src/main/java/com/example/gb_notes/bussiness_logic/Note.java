package com.example.gb_notes.bussiness_logic;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Note implements Parcelable {
    private Date date;
    private String name;
    private String description;

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        byte tmpIsDone = in.readByte();
        isDone = tmpIsDone == 0 ? null : tmpIsDone == 1;
        index = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Boolean isDone;
    private int index;


    public Note(String name, String description, int index ){
        this.date = new Date();
        this.index = index;
        this.name = name;
        this.description = description;
        this.isDone = false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeByte((byte) (isDone == null ? 0 : isDone ? 1 : 2));
        parcel.writeInt(index);
    }
}

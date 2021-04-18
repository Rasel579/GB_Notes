package com.example.gb_notes.ui;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.gb_notes.R;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.data.NoteSource;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ContentNotesAdapter extends RecyclerView.Adapter<ContentNotesAdapter.ViewHolder> {
    private static String TAG = "ContentNotesAdapter";
    private NoteSource noteSource;
    private Fragment fragment;
    private OnItemClickListener itemClickListener;
    private int menuPosition;

    public ContentNotesAdapter(NoteSource noteSource , Fragment fragment) {
        this.noteSource = noteSource;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(noteSource.getNote(position));
        Log.d(TAG, "onBindViewHolder");

    }


    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }


    public int getMenuPosition() {
        return menuPosition;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int resources);
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameNote;
        private TextView data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameNote = itemView.findViewById(R.id.noteName);
            data = itemView.findViewById(R.id.noteDate);
            registerContextMenu(itemView);
            data.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(50,10);
                    return true;
                }
            });
            data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(view, getAdapterPosition(), R.id.noteDate);
                    }
                }
            });
            nameNote.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(view, getAdapterPosition(), R.id.noteName);
                }
            });
        }

        private void registerContextMenu(View itemView) {
            if(fragment != null){
                itemView.setOnLongClickListener(view -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }

        }

        public void setData(Note note) {
            nameNote.setText(note.getName());
            data.setText(String.valueOf(note.getDate()));
        }
    }
}

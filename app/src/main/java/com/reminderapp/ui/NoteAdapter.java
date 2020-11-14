package com.reminderapp.ui;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.reminderapp.database.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> mNotes;

    /**
     * Sets the list according to current mode
     *
     * @param mode  The id of current mode
     *              mode 0 is ongoing
     *              mode 1 favorite
     *              mode 2 finished
     * @param notes - The list of Note
     */
    public void setNotesList(List<Note> notes, int mode) {
        ArrayList<Note> list = new ArrayList<>();
        for (Note note : notes) {
            if (mode == 0) {
                if (!note.finished)
                    list.add(note);
            } else if (mode == 1) {
                if (note.favorite)
                    list.add(note);
            } else {
                if (note.finished)
                    list.add(note);
            }
        }
        mNotes = list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(mNotes.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

}
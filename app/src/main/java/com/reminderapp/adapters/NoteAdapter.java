package com.reminderapp.adapters;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.reminderapp.database.Note;
import com.reminderapp.holders.NoteViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    // The list of notes
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

    // View to show when list is empty
    private final int EMPTY_LIST_VIEW = 100;

    @Override
    public int getItemViewType(int position) {
        if (mNotes == null || mNotes.size() == 0)
            return EMPTY_LIST_VIEW;
        return super.getItemViewType(position);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_LIST_VIEW)
            return NoteViewHolder.createEmptyView(parent);

        return NoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (holder.getItemViewType() != EMPTY_LIST_VIEW)
            holder.bind(mNotes.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mNotes == null || mNotes.size() == 0 ? 1 : mNotes.size();
    }

}
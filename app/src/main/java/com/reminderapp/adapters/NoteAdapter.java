package com.reminderapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.reminderapp.NoteActivity;
import com.reminderapp.R;
import com.reminderapp.database.Note;
import com.reminderapp.database.NoteViewModel;
import com.reminderapp.holders.NoteViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    // The list of notes
    private List<Note> mNotes;

    private final Fragment noteFragment;

    public NoteAdapter(Fragment fragment) {
        noteFragment = fragment;
    }

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

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        NoteViewHolder holder = new NoteViewHolder(view);
        view.setOnClickListener((View v) -> {
            Intent myIntent = new Intent(v.getContext(), NoteActivity.class);
            myIntent.putExtra("id", mNotes.get(holder.getAdapterPosition()).id);
            v.getContext().startActivity(myIntent);
        });
        view.setOnLongClickListener((View v) -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.delete_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                NoteViewModel noteViewModel = new ViewModelProvider(noteFragment).get(NoteViewModel.class);
                noteViewModel.delete(mNotes.get(holder.getAdapterPosition()));
                return true;
            });
            popup.show();

            return true;
        });
        return holder;
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
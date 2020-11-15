package com.reminderapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.reminderapp.NoteActivity;
import com.reminderapp.R;
import com.reminderapp.database.Note;
import com.reminderapp.holders.NoteViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    // The list of all notes
    private List<Note> mNotes;

    // The list of filtered notes
    private List<Note> filteredNotes;

    /**
     * Sets the list
     */
    public void setNotesList(List<Note> notes) {
        mNotes = notes;
    }

    public void filter(String s) {
        if (s.isEmpty()) {
            filteredNotes = new ArrayList<>();
        }
        ArrayList<Note> list = new ArrayList<>();
        if (mNotes != null && mNotes.size() > 0) {
            for (Note note : mNotes) {
                if (note.title.toLowerCase().contains(s.toLowerCase())) {
                    list.add(note);
                }
            }
        }
        filteredNotes = list;
    }

    // View to show when list is empty
    private final int EMPTY_LIST_VIEW = 100;

    @Override
    public int getItemViewType(int position) {
        if (filteredNotes == null || filteredNotes.size() == 0)
            return EMPTY_LIST_VIEW;
        return super.getItemViewType(position);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_LIST_VIEW) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_empty, parent, false);
            TextView textView = view.findViewById(R.id.empty_message);
            textView.setText(R.string.start_typ);
            return new NoteViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        NoteViewHolder holder = new NoteViewHolder(view);
        view.setOnClickListener((View v) -> {
            Intent myIntent = new Intent(v.getContext(), NoteActivity.class);
            myIntent.putExtra("id", filteredNotes.get(holder.getAdapterPosition()).id);
            v.getContext().startActivity(myIntent);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (holder.getItemViewType() != EMPTY_LIST_VIEW)
            holder.bind(filteredNotes.get(position).title);
    }

    @Override
    public int getItemCount() {
        return filteredNotes == null || filteredNotes.size() == 0 ? 1 : filteredNotes.size();
    }

}
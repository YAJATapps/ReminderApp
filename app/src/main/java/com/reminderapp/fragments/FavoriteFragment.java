package com.reminderapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reminderapp.R;
import com.reminderapp.database.NoteViewModel;
import com.reminderapp.ui.NoteAdapter;

public class FavoriteFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private NoteAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NoteAdapter();
        recyclerView.setAdapter(mAdapter);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.setNotesList(notes, 1);
            mAdapter.notifyDataSetChanged();
        });

        return root;
    }
}
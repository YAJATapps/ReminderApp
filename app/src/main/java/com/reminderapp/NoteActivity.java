package com.reminderapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reminderapp.database.Note;
import com.reminderapp.database.NoteViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NoteActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private Note note;

    // If title and content were set
    private boolean init = false;

    private int id;

    // TextView s
    private EditText title;
    private EditText content;

    // Menus
    private MenuItem favorite;
    private MenuItem finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("id", -1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.note_title);
        content = findViewById(R.id.note_content);

        // Init the view model for notes.
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> {
            note = NoteViewModel.getNote(id, notes);
            if (!init && note != null && !note.title.isEmpty()) {
                if (note.finished && finished != null)
                    finished.setIcon(R.drawable.ic_unarchive).setTitle(R.string.unfinished);

                if (note.favorite && favorite != null)
                    favorite.setIcon(R.drawable.ic_unfavorite).setTitle(R.string.unfavorite);

                title.setText(note.title);
                content.setText(note.noteContent);
                init = true;
            }
        });

        // Add note to db
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (id == -1) {
                if (title.getText().toString().length() > 0) {
                    Note note = new Note(title.getText().toString(),
                            content.getText().toString(), false, false
                    );
                    noteViewModel.insert(note);
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.title_empty), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (title.getText().toString().length() > 0) {
                    note.title = title.getText().toString();
                    note.noteContent = content.getText().toString();
                    noteViewModel.update(note);
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.title_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Favorite
        if (item.getItemId() == 1) {
            if (id != -1 && note != null) {
                if (note.favorite) {
                    note.favorite = false;
                    noteViewModel.update(note);
                    Toast.makeText(this, "Unmarked as favorite", Toast.LENGTH_SHORT).show();
                } else {
                    note.favorite = true;
                    noteViewModel.update(note);
                    Toast.makeText(this, "Marked as favorite", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                if (title.getText().toString().length() > 0) {
                    Note note = new Note(title.getText().toString(),
                            content.getText().toString(), true, false
                    );
                    noteViewModel.insert(note);
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.title_empty), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }

        // Finished
        if (item.getItemId() == 2) {
            if (id != -1 && note != null) {
                if (note.finished) {
                    note.finished = false;
                    noteViewModel.update(note);
                    Toast.makeText(this, "Moved to ongoing", Toast.LENGTH_SHORT).show();
                } else {
                    note.finished = true;
                    noteViewModel.update(note);
                    Toast.makeText(this, "Moved to finished", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                if (title.getText().toString().length() > 0) {
                    Note note = new Note(title.getText().toString(),
                            content.getText().toString(), false, true
                    );
                    noteViewModel.insert(note);
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.title_empty), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the favorite icon; this adds items to the action bar if it is present.
        favorite = menu.add(0, 1, 0, R.string.menu_favorite);
        favorite.setIcon(R.drawable.ic_menu_favorite).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        // Inflate the finished icon; this adds items to the action bar if it is present.
        finished = menu.add(0, 2, 0, R.string.menu_finished);
        finished.setIcon(R.drawable.ic_archive).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        if (note != null) {
            if (note.finished && finished != null)
                finished.setIcon(R.drawable.ic_unarchive).setTitle(R.string.unfinished);

            if (note.favorite && favorite != null)
                favorite.setIcon(R.drawable.ic_unfavorite).setTitle(R.string.unfavorite);
        }
        return true;
    }
}
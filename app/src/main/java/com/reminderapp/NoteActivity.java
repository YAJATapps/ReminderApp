package com.reminderapp;

import android.graphics.Color;
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

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("id", -1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init the view model for notes.
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // Add note to db
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if (id == -1) {
                EditText title = findViewById(R.id.note_title);
                EditText content = findViewById(R.id.note_content);
                if (title.getText().toString().length() > 0) {
                    Note note = new Note(title.getText().toString(),
                            content.getText().toString(),
                            Color.BLACK,
                            1f, false, false
                    );
                    noteViewModel.insert(note);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.title_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            finish();
        });

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1 || item.getItemId() == 2) {
            Toast.makeText(this, "Replace with your actions", Toast.LENGTH_SHORT).show();
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
        // Inflate the color icon; this adds items to the action bar if it is present.
        menu.add(0, 1, 0, R.string.color)
                .setIcon(R.drawable.ic_color_lens)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        // Inflate the font size icon; this adds items to the action bar if it is present.
        menu.add(0, 2, 0, R.string.size)
                .setIcon(R.drawable.ic_text_fields)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
}
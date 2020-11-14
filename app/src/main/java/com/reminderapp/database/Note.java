package com.reminderapp.database;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Color;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 * <p>
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity(tableName = "note_table")
public class Note {

    // Unique id of the document
    @PrimaryKey(autoGenerate = true)
    public int id;

    // The title of note
    @NonNull
    @ColumnInfo(name = "note")
    public String title;

    // The content of note
    @ColumnInfo(name = "noteContent")
    public String noteContent;

    // The color of note
    @NonNull
    @ColumnInfo(name = "noteColor")
    public int noteColor;

    // The factor of note
    @NonNull
    @ColumnInfo(name = "noteSizeFactor")
    public float noteSizeFactor;

    // Whether the note is favorite
    public boolean favorite;

    // Whether the note is finished
    public boolean finished;

    // The main constructor
    public Note(@NonNull String note, String content, int color, float factor, boolean favorite, boolean finished) {
        this.title = note;
        this.noteContent = content;
        this.noteColor = color;
        this.noteSizeFactor = factor;
        this.favorite = favorite;
        this.finished = finished;
    }

    // Dummy constructor
    public Note() {
        this("Empty", "", Color.BLACK, 1f, false, false);
    }
}

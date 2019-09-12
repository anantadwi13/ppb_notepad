package com.anantadwi13.notepad;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "note_db";
    private static volatile NoteDatabase INSTANCE;

    public abstract NoteDao noteDao();

    static NoteDatabase getInstance(final Context context){
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}

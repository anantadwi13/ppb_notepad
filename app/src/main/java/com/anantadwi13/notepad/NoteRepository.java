package com.anantadwi13.notepad;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private static NoteRepository repository;

    private NoteDao noteDao;
    private LiveData<List<Note>> notes;

    public static NoteRepository getInstance(Application application){
        if (repository == null)
            repository = new NoteRepository(application);
        return repository;
    }

    private NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
        notes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insert(Note note){
        new InsertTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteTask(noteDao).execute(note);
    }

    private static class InsertTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        public InsertTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            if (notes.length > 0 && notes[0] != null)
                noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        public DeleteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            if (notes.length > 0 && notes[0] != null)
                noteDao.delete(notes[0]);
            return null;
        }
    }
}

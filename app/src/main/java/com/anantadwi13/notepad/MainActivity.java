package com.anantadwi13.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel viewModel;

    private RecyclerView recyclerView;
    private NoteListAdapter adapter;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(nextIntent);
            }
        });
        adapter = new NoteListAdapter(new NoteListAdapter.Listener() {
            @Override
            public void onClick(int pos) {
                Intent nextIntent = new Intent(MainActivity.this, EditActivity.class);
                nextIntent.putExtra(EditActivity.EXTRA_NOTE, adapter.getList().get(pos));
                startActivity(nextIntent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setList(notes);
            }
        });
    }
}

package com.anantadwi13.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE = "extra_note";

    private Note note;

    private EditText etTitle, etContent;
    private Button btnSave;

    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);

        note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note == null) note = new Note();
        else btnSave.setText("Update");

        etTitle.setText(note.getTitle());
        etContent.setText(note.getContent());

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(EditActivity.this, "Semua isian harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                note.setTitle(title);
                note.setContent(content);
                viewModel.insert(note);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (note.getId() != null)
            inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnDelete:
                if (note.getId() != null) {
                    viewModel.delete(note);
                    Toast.makeText(this, "Note berhasil dihapus!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Note belum disimpan!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.example.simplenotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Note> notesList = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(this, notesList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddNoteDialog());
    }

    private void showAddNoteDialog() {
        // Inflate the layout for the dialog
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_add_note, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editTextTitle = promptView.findViewById(R.id.new_note_title);
        final EditText editTextContent = promptView.findViewById(R.id.new_note_content);

        // Setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", (dialog, id) -> {
                    String title = editTextTitle.getText().toString();
                    String content = editTextContent.getText().toString();
                    notesList.add(new Note(title, content));
                    adapter.notifyItemInserted(notesList.size() - 1);
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}

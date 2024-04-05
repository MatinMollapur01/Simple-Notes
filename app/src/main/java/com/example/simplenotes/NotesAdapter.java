package com.example.simplenotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<Note> notesList;
    private final LayoutInflater inflater;
    private final Context context;

    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Note note = notesList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());

        holder.itemView.setOnLongClickListener(view -> {
            notesList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            return true;
        });

        holder.itemView.setOnClickListener(view -> showEditNoteDialog(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteContent;

        ViewHolder(View view) {
            super(view);
            noteTitle = view.findViewById(R.id.note_title);
            noteContent = view.findViewById(R.id.note_content);
        }
    }

    private void showEditNoteDialog(final int position) {
        // Inflate the layout for the dialog
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_edit_note, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText editTextTitle = promptView.findViewById(R.id.edit_note_title);
        final EditText editTextContent = promptView.findViewById(R.id.edit_note_content);

        // Pre-fill the dialog with current note content
        editTextTitle.setText(notesList.get(position).getTitle());
        editTextContent.setText(notesList.get(position).getContent());

        // Setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", (dialog, id) -> {
                    String title = editTextTitle.getText().toString();
                    String content = editTextContent.getText().toString();
                    Note note = notesList.get(position);
                    note.setTitle(title);
                    note.setContent(content);
                    notifyItemChanged(position);
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}

package com.shamim.note_to_self;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogShowNote extends DialogFragment {

    private Note mNote;

    public void sendNoteSelected(Note noteSelected) {
        mNote = noteSelected;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_show_note, null);

        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        TextView txtDescription = dialogView.findViewById(R.id.txtDescription);
        TextView txtImportant = dialogView.findViewById(R.id.textViewImportant);
        TextView txtTodo = dialogView.findViewById(R.id.textViewTodo);
        TextView txtIdea = dialogView.findViewById(R.id.textViewIdea);

        txtTitle.setText(mNote.getTitle());
        txtDescription.setText(mNote.getDescription());

        if (!mNote.isImportant()) txtImportant.setVisibility(View.GONE);
        if (!mNote.isTodo()) txtTodo.setVisibility(View.GONE);
        if (!mNote.isIdea()) txtIdea.setVisibility(View.GONE);

        Button btnOK = dialogView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(dialogView).setMessage("Your Note");
        return builder.create();
    }
}

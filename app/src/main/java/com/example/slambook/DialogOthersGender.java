package com.example.slambook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.slambook.R;

public class DialogOthersGender extends AppCompatDialogFragment {

    private EditText editTextOtherGender;
    private DialogOthersGenderListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_for_gender_others, null);
        builder.setView(view)
                .setTitle("Others")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String otherGender = editTextOtherGender.getText().toString().trim();
                        char capThefirstLetter = otherGender.toUpperCase().charAt(0);
                        listener.applyTexts(capThefirstLetter + otherGender.substring(1));
                    }
                });
        editTextOtherGender = view.findViewById(R.id.edit_other_gender);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogOthersGenderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogOthersGenderListener");
        }
    }

    public interface DialogOthersGenderListener {
        void applyTexts(String otherGender);
    }
}

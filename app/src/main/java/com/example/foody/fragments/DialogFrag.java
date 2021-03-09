package com.example.foody.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogFrag extends DialogFragment {

    private static final String ARG_TITLE = "TITLE";
    private static final String ARG_MESSAGE = "MESSAGE";

    private String contactDialogTitle;
    private String contactDialogMessage;

    public DialogFrag() {
    }

    public static DialogFrag newInstance(String title, String message) {
        DialogFrag fragment = new DialogFrag();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactDialogTitle = getArguments().getString(ARG_TITLE);
            contactDialogMessage = getArguments().getString(ARG_MESSAGE);
        }
    }

    //-----------------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (contactDialogTitle != null) {
            builder.setTitle(contactDialogTitle);
        }
        if (contactDialogMessage != null){
            builder.setMessage(contactDialogMessage);
        }
        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
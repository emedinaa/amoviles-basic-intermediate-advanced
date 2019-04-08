package com.emedinaa.core.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/18/18
 */
public class SimpleDialog extends DialogFragment {

    private SimpleDialogListener listener;
    private String title="";
    private String message="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     if (context instanceof SimpleDialogListener) {
         listener = (SimpleDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SimpleDialogListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            title=getArguments().getString("TITLE", "");
            message=getArguments().getString("MESSAGE", "");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(listener!=null){
                            listener.onAction();
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if(listener!=null){
                            listener.onAction();
                        }
                    }
                });
                /*.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface SimpleDialogListener{
        void onAction();
    }
}

package com.emedinaa.myrestaurant.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.ui.listeners.NumberPickerListener;

/**
 * @author : Eduardo Medina
 * @see : https://github.com/emedinaa/android_custom_dialog_fragment
 * @since : 8/11/18
 */
public class NumberPickerDialog extends DialogFragment {

    private View buttonOk,buttonPlus,buttonMinus;
    private TextView textViewNumber;

    private NumberPickerListener numberPickerListener;
    private int amount=0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View customView= inflater.inflate(R.layout.layout_number_picker,null);
        buttonOk= customView.findViewById(R.id.buttonOk);
        buttonPlus=  customView.findViewById(R.id.buttonPlus);
        buttonMinus=  customView.findViewById(R.id.buttonMinus);
        textViewNumber=  customView.findViewById(R.id.textViewNumber);

        builder.setView(customView);
        //events
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberPickerListener!=null){
                    numberPickerListener.onDialogOk(amount);
                    dismiss();
                }
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount++;
                textViewNumber.setText(String.valueOf(amount));
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount--;
                if(amount<0){
                    amount=0;
                }
                textViewNumber.setText(String.valueOf(amount));
            }
        });

        return  builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    private void updateTextView(){
        textViewNumber.setText(String.valueOf(amount));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NumberPickerListener) {
            numberPickerListener = (NumberPickerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NumberPickerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        numberPickerListener= null;
    }
}

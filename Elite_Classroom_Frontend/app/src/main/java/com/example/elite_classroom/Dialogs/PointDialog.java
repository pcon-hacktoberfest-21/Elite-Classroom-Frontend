package com.example.elite_classroom.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.elite_classroom.R;

public class PointDialog extends AppCompatDialogFragment {
    RadioButton r,r1;
    EditText e;
    PointDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pointdialog, null);
        builder.setView(view)
                .setTitle("Change point value")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String points = "";
                       if(r.isChecked()){
                           points = e.getText().toString();
                           Log.i("point",points);
                       }
                       else if(r1.isChecked()){
                           points ="Ungraded";
                       }
                        listener.applyTexts(points);
                    }
                });
      r=view.findViewById(R.id.pointbutton);
      r1=view.findViewById(R.id.unpointbutton);
      e=view.findViewById(R.id.points);
      return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PointDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement PointDialogListener");
        }
    }
    public interface PointDialogListener {
        void applyTexts(String points);
    }
}

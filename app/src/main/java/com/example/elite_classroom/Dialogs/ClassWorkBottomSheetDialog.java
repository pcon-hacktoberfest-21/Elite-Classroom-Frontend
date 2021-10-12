package com.example.elite_classroom.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.elite_classroom.Activities.ClassWorkActivity;
import com.example.elite_classroom.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ClassWorkBottomSheetDialog extends BottomSheetDialogFragment {


    String class_code="", owner_code,class_name,owner_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classworkbottom_sheet, container, false);


        class_code= getArguments().getString("class_code");
        owner_code= getArguments().getString("owner_id");
        class_name = getArguments().getString("class_name");
        owner_name = getArguments().getString("owner_name");



        TextView assignment = view.findViewById(R.id.assignment);
        TextView material  = view.findViewById(R.id.material);
        TextView announcement  = view.findViewById(R.id.announcement);

        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassWorkActivity.class);
                i.putExtra("u",0);
                i.putExtra("class_code",class_code);
                i.putExtra("owner_id",owner_code);
                i.putExtra("class_name",class_name);
                i.putExtra("owner_name",owner_name);
                startActivity(i);
                dismiss();
            }
        });

        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClassWorkActivity.class);
                i.putExtra("u",1);
                i.putExtra("class_code",class_code);
                i.putExtra("owner_id",owner_code);
                i.putExtra("class_name",class_name);
                i.putExtra("owner_name",owner_name);
                startActivity(i);
                dismiss();
            }
        });

        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ClassWorkActivity.class);
                i.putExtra("u",2);
                i.putExtra("class_code",class_code);
                i.putExtra("owner_id",owner_code);
                i.putExtra("class_name",class_name);
                i.putExtra("owner_name",owner_name);
                startActivity(i);
                dismiss();
            }
        });
        return view;
    }
}

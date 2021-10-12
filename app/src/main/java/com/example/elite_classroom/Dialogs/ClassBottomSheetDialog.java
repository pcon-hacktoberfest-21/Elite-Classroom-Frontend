package com.example.elite_classroom.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.elite_classroom.Fragments.CreateClassFragment;
import com.example.elite_classroom.Fragments.JoinClassFragment;
import com.example.elite_classroom.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ClassBottomSheetDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classbottom_sheet, container, false);
        TextView create = view.findViewById(R.id.create);
        TextView join  = view.findViewById(R.id.join);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new CreateClassFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                dismiss();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new JoinClassFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                dismiss();
            }
        });
        return view;
    }
}
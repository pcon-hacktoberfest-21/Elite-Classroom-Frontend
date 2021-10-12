package com.example.elite_classroom.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.elite_classroom.Fragments.Teacher_Instructions_Fragment;
import com.example.elite_classroom.Fragments.Teacher_Student_Work_Fragment;

public class ViewPagerAdapter_Second extends FragmentPagerAdapter {
    int mNumOfTabs;
    Bundle b;

    public ViewPagerAdapter_Second(FragmentManager fm, int NoofTabs , Bundle b){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NoofTabs;
        this.b= b;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){




        switch (position){
            case 0:
                Teacher_Instructions_Fragment Submitted= new Teacher_Instructions_Fragment();
                Submitted.setArguments(b);
                return Submitted;
            case 1:
                Teacher_Student_Work_Fragment Pending = new Teacher_Student_Work_Fragment();
                Pending.setArguments(b);
                return Pending;
            default:
                return null;
        }
    }
}

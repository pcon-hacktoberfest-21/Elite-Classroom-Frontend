package com.example.elite_classroom.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.elite_classroom.Fragments.ToDo.MissedFragment;
import com.example.elite_classroom.Fragments.ToDo.PendingFragment;
import com.example.elite_classroom.Fragments.ToDo.SubmittedFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public ViewPagerAdapter(FragmentManager fm, int NoofTabs){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                SubmittedFragment Submitted= new SubmittedFragment();
                return Submitted;
            case 1:
                PendingFragment Pending = new PendingFragment();
                return Pending;
            case 2:
                MissedFragment Missing = new MissedFragment();
                return Missing;
            default:
                return null;
        }
    }
}

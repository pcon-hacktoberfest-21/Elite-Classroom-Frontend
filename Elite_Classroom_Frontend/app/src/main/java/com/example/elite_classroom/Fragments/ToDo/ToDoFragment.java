package com.example.elite_classroom.Fragments.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.elite_classroom.Activities.LoginActivity;
import com.example.elite_classroom.Activities.MainActivity;
import com.example.elite_classroom.Adapter.ViewPagerAdapter;
import com.example.elite_classroom.R;
import com.google.android.material.tabs.TabLayout;


public class ToDoFragment extends Fragment {
    private TabLayout tabLayout;
    String sharedPrefFile = "Login_Credentials";
    private ViewPager viewpager;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);


        MainActivity.line_divider_main.setVisibility(View.GONE);
        preferences = getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);


        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_id);
        tabLayout.addTab(tabLayout.newTab().setText("Submitted"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Missed"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
         viewpager =(ViewPager)view.findViewById(R.id.viewpager_id);
        ViewPagerAdapter tabsAdapter = new ViewPagerAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewpager.setAdapter(tabsAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.getAdapter().notifyDataSetChanged();
        return view;
    }
}
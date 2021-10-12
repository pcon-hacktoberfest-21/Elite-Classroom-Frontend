package com.example.elite_classroom.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Fragments.Calender_Fragment;
import com.example.elite_classroom.Fragments.Owner_Create_Class;
import com.example.elite_classroom.Fragments.Schedule_Class_Fragment;
import com.example.elite_classroom.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalenderActivity extends AppCompatActivity {


    RecyclerView current_next_week;
    SharedPreferences preferences;
    String sharedPrefFile = "Login_Credentials";

    FloatingActionButton add_class_fixture_btn;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);



        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new Calender_Fragment(),"Calender_FRAGMENT").commit();



    }


    @Override
    public void onBackPressed() {


        Calender_Fragment myFragment = (Calender_Fragment) getSupportFragmentManager().findFragmentByTag("Calender_FRAGMENT");
        if (myFragment != null && myFragment.isVisible()) {
            super.onBackPressed();
        }
        Schedule_Class_Fragment schedule_fragment =(Schedule_Class_Fragment) getSupportFragmentManager().findFragmentByTag("Schedule_FRAGMENT");
        if(schedule_fragment !=null && schedule_fragment.isVisible())
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new Owner_Create_Class(),"Owner_FRAGMENT").commit();



        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new Calender_Fragment(),"Calender_FRAGMENT").commit();
        }



    }
}
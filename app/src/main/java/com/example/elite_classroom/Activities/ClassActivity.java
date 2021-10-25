package com.example.elite_classroom.Activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.elite_classroom.Fragments.AboutFragment;
import com.example.elite_classroom.Fragments.ClassWorkFragment;
import com.example.elite_classroom.Fragments.FeedbackFragment;
import com.example.elite_classroom.Fragments.PeopleFragment;
import com.example.elite_classroom.Fragments.StreamFragment;
import com.example.elite_classroom.Fragments.ToDo.ToDoFragment;
import com.example.elite_classroom.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    TextView name_second;
    GoogleSignInClient mGoogleSignInClient;
    String sharedPrefFile = "Login_Credentials";
    public static SharedPreferences preferences;
    TextView settings;
    NavigationView navigationView;
    Bundle bundle;
    public static String classCode, owner_id, class_name, owner_name;
    public static TextView name,top_menu,top_menu_second;

    public ArrayList<Fragment>  fragments = new ArrayList<>();
    StreamFragment streamFragment;
    ClassWorkFragment classWorkFragment;
    PeopleFragment peopleFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        preferences =ClassActivity.this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        settings = findViewById(R.id.settings);
        top_menu= findViewById(R.id.top_menu);
        top_menu_second= findViewById(R.id.top_menu_second);



        name_second = findViewById(R.id.name_second);
        BottomNavigationView btview = findViewById(R.id.bottom_navigation);


        Intent intent = getIntent();
        classCode = intent.getStringExtra("class_code");
        owner_id = intent.getStringExtra("owner_id");
        class_name = intent.getStringExtra("class_name");
        owner_name = intent.getStringExtra("owner_name");

        bundle = new Bundle();
        bundle.putString("class_code", classCode);
        bundle.putString("class_name", class_name);
        bundle.putString("owner_name", owner_name);
        bundle.putString("owner_id", owner_id);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        streamFragment = new StreamFragment();
        streamFragment.setArguments(bundle);

        classWorkFragment = new ClassWorkFragment();
        classWorkFragment.setArguments(bundle);

         peopleFragment = new PeopleFragment();
        peopleFragment.setArguments(bundle);


//        BottomNavigationView.OnNavigationItemSelectedListener navListener =
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.nav_stream:
//                                name_second.setText("");
//
//                                StreamFragment streamFragment = new StreamFragment();
//                                streamFragment.setArguments(bundle);
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
//                                        streamFragment).commit();
//
//
//                                break;
//                            case R.id.nav_classwork:
//                                if (class_name.length() > 9) {
//                                    name_second.setText(class_name.substring(0, 9) + "...");
//
//                                } else {
//                                    name_second.setText(class_name);
//                                }
//
//                                ClassWorkFragment classWorkFragment = new ClassWorkFragment();
//                                classWorkFragment.setArguments(bundle);
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
//                                        classWorkFragment).commit();
//                                break;
//                            case R.id.nav_people:
//
//                                if (class_name.length() > 9) {
//                                    name_second.setText(class_name.substring(0, 9) + "...");
//
//                                } else {
//                                    name_second.setText(class_name);
//                                    name_second.setTextColor(ContextCompat.getColor(ClassActivity.this,R.color.blue_colour));
//                                }
//                                PeopleFragment peopleFragment = new PeopleFragment();
//                                peopleFragment.setArguments(bundle);
//                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
//                                        peopleFragment).commit();
//                                break;
//                        }
//                        return true;
//                    }
//                };
         show_Stream_Fragment();
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_stream:
                                name_second.setText("");


                                 show_Stream_Fragment();

                                break;
                            case R.id.nav_classwork:
                                if (class_name.length() > 9) {
                                    name_second.setText(class_name.substring(0, 9) + "...");

                                } else {
                                    name_second.setText(class_name);
                                }

                                   show_ClassWork_Fragment();
                                break;
                            case R.id.nav_people:

                                if (class_name.length() > 9) {
                                    name_second.setText(class_name.substring(0, 9) + "...");

                                } else {
                                    name_second.setText(class_name);
                                    name_second.setTextColor(ContextCompat.getColor(ClassActivity.this,R.color.blue_colour));
                                }
                                show_people_Fragment();
                                break;
                        }
                        return true;
                    }
                };

        if (getIntent().getBooleanExtra("from_Classwork", false)) {

            if (class_name.length() > 9) {
                name_second.setText(class_name.substring(0, 9) + "...");

            } else {
                name_second.setText(class_name);
            }
//            ClassWorkFragment classWorkFragment = new ClassWorkFragment();
//            classWorkFragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
//                    classWorkFragment).commit();
//            btview.setSelectedItemId(R.id.nav_classwork);

        } else {

//            StreamFragment streamFragment = new StreamFragment();
//            streamFragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
//                    streamFragment).commit();

        }


        btview.setOnNavigationItemSelectedListener(navListener);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

         navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_class);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_class:

                navigationView.setCheckedItem(R.id.nav_class);

                Intent i = new Intent(ClassActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.nav_calender:
                startActivity(new Intent(ClassActivity.this,CalenderActivity.class));
                navigationView.setCheckedItem(R.id.nav_calender);

                break;
            case R.id.nav_todo:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
                        new ToDoFragment(),"TODO_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_todo);

                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
                    new AboutFragment(),"ABOUT_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_about);

                break;
            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,
                        new FeedbackFragment(),"FEEDBACK_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_feedback);

                break;
            case R.id.nav_signout:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                mGoogleSignInClient = GoogleSignIn.getClient(ClassActivity.this, gso);
                mGoogleSignInClient.signOut();

                SharedPreferences.Editor editor =  preferences.edit();
                editor.putString("name", null);
                editor.putString("email",null);
                editor.putString("jwt_token", null);
                editor.putString("google_token", null);
                editor.apply();
                editor.commit();
                Intent intent = new Intent(ClassActivity.this, LoginActivity.class);
                Toast.makeText(ClassActivity.this,"Signed_Out",Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
//            Intent a = new Intent(Intent.ACTION_MAIN);
//            a.addCategory(Intent.CATEGORY_HOME);
//            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(a);
        }

    }

    public void show_Stream_Fragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(streamFragment.isAdded())
        {
            fragmentTransaction.show(streamFragment);
        }
        else
        {
            fragmentTransaction.add(R.id.frame_container1,streamFragment,"STREAM_FRAGMENT");
        }

        if(classWorkFragment.isAdded()) fragmentTransaction.hide(classWorkFragment);
        if(peopleFragment.isAdded())  fragmentTransaction.hide(peopleFragment);

        fragmentTransaction.commit();
    }

    public void show_ClassWork_Fragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(classWorkFragment.isAdded())
        {
            fragmentTransaction.show(classWorkFragment);
        }
        else
        {
            fragmentTransaction.add(R.id.frame_container1,classWorkFragment,"CLASSWORK_FRAGMENT");
        }

        if(streamFragment.isAdded()) fragmentTransaction.hide(streamFragment);
        if(peopleFragment.isAdded())  fragmentTransaction.hide(peopleFragment);

        fragmentTransaction.commit();
    }

    public void show_people_Fragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(peopleFragment.isAdded())
        {
            fragmentTransaction.show(peopleFragment);
        }
        else
        {
            fragmentTransaction.add(R.id.frame_container1,peopleFragment,"PEOPLE_FRAGMENT");
        }

        if(streamFragment.isAdded()) fragmentTransaction.hide(streamFragment);
        if(classWorkFragment.isAdded())  fragmentTransaction.hide(classWorkFragment);

        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        navigationView.setCheckedItem(R.id.nav_class);

        super.onResume();
    }
}
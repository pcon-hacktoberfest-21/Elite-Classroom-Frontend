package com.example.elite_classroom.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.elite_classroom.Fragments.AboutFragment;
import com.example.elite_classroom.Fragments.ClassFragment;
import com.example.elite_classroom.Fragments.FeedbackFragment;
import com.example.elite_classroom.Fragments.ToDo.ToDoFragment;
import com.example.elite_classroom.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    Button sign_out_button ;
    GoogleSignInClient mGoogleSignInClient;
    String sharedPrefFile = "Login_Credentials";
    public static TextView textView, name;
    TextView test;
   public static View line_divider_main ;
    NavigationView navigationView;
    SharedPreferences preferences;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.white));
        setContentView(R.layout.activity_main);


        line_divider_main = findViewById(R.id.line_divider_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        test = findViewById(R.id.test);





        name= findViewById(R.id.name);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new ClassFragment(), "HOME_FRAGMENT").commit();


        textView = findViewById(R.id.name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar action = getSupportActionBar();

        action.setDisplayShowTitleEnabled(false);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.nav_class);
        drawer = findViewById(R.id.drawer_layout);
        preferences =MainActivity.this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_class:


                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new ClassFragment(),"HOME_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_class);
                break;
            case R.id.nav_calender:

                startActivity(new Intent(MainActivity.this,CalenderActivity.class));
                navigationView.setCheckedItem(R.id.nav_calender);
                break;
            case R.id.nav_todo:
                name.setText("To-Do");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new ToDoFragment(),"TODO_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_todo);

                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new AboutFragment(),"ABOUT_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_about);

                break;
            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new FeedbackFragment(),"FEEDBACK_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_feedback);

                break;
            case R.id.nav_signout:
            {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                mGoogleSignInClient.signOut();

                SharedPreferences.Editor editor =  preferences.edit();
                editor.putString("name", null);
                editor.putString("email",null);
                editor.putString("jwt_token", null);
                editor.putString("google_token", null);
                editor.apply();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this,"Signed_Out",Toast.LENGTH_LONG).show();
                finish();
                startActivity(intent);
                break;
            }


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);}
        else{

            ClassFragment myFragment = (ClassFragment) getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
            if (myFragment != null && myFragment.isVisible()) {
                finishAffinity();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_out,R.anim.slide_in).replace(R.id.frame_container,
                        new ClassFragment(),"HOME_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_class);
            }

        }

    }
    @Override
    protected void onResume() {
        navigationView.setCheckedItem(R.id.nav_class);

        super.onResume();
    }
}
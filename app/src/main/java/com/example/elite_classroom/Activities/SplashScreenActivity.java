package com.example.elite_classroom.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.elite_classroom.R;

import in.codeshuffle.typewriterview.TypeWriterView;

public class SplashScreenActivity extends AppCompatActivity {

    private TypeWriterView myText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myText=findViewById(R.id.typeWriterView);
        myText.setDelay(1);
        myText.setWithMusic(true);
        myText.animateText("Elite Classroom");



        // add hander to call postDelayed
        Handler handler = new Handler();
        handler.postDelayed(this::loadLogin, 3000);




    }

    // function to call the login activity
    public void loadLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
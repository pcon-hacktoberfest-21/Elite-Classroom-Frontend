package com.example.elite_classroom.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.elite_classroom.Models.Retrofit_Models.Auth_Responses;
import com.example.elite_classroom.Models.Retrofit_Models.Google_Logins;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    String sharedPrefFile = "Login_Credentials";
    SignInButton googleBTN ;
    TextView realGoogle  ;
    RelativeLayout progress_layout;
    ProgressBar progress_bar;
    Integer RC_SIGN_IN =0;
    Window window;
    public static SharedPreferences preferences;
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        window= LoginActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preferences =LoginActivity.this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        progress_bar= findViewById(R.id.progress_bar);
        progress_layout = findViewById(R.id.progress_layout);

        progress_bar.setVisibility(View.GONE);
        progress_layout.setVisibility(View.GONE);





        color_runnable.run();
googleBTN= findViewById(R.id.googleBTN);
        realGoogle = findViewById(R.id.realGoogle);
        realGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    public  void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }



    private void handleSignInResult( Task<GoogleSignInAccount>  completedTask) {


        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account!=null) {

                String name =  account.getDisplayName();
                String email=   account.getEmail();


                progress_bar.setVisibility(View.VISIBLE);
                progress_layout.setVisibility(View.VISIBLE);

                      if(name!=null && email!=null)
                      {
                          DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
                          Call<Auth_Responses> request = service.login_Google_User(new Google_Logins(name,email,account.getId()));


                          request.enqueue(new Callback<Auth_Responses>() {
                    @Override
                    public void onResponse(@NotNull Call<Auth_Responses> call, @NotNull Response<Auth_Responses> response) {


                        if(response.body()!=null)
                        {

                            SharedPreferences.Editor editor =  preferences.edit();
                            editor.putString("name", name);
                            editor.putString("email",email);
                            editor.putString("jwt_token", response.body().getToken());
                            editor.putString("google_token", account.getId());
                            editor.apply();
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            progress_bar.setVisibility(View.GONE);
                            progress_layout.setVisibility(View.GONE);
                                }
                                else
                                {
                                    Log.d("####","Response body null hai ");
                                   Toast.makeText(LoginActivity.this,"Body is null",Toast.LENGTH_LONG).show();
                                }




                    }

                    @Override
                    public void onFailure(Call<Auth_Responses> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Something Went wrong",Toast.LENGTH_LONG).show();
                    }
                });

                      }
                      else{
                          Log.d("####","Name ya phir Email null hai");
                      }

            }

        } catch (ApiException e) {

            Log.d("#### ","API Exception ho gayi "+e.toString());

        }



    }



    @Override
    protected void onStart() {

        if(preferences.getString("google_token",null)!=null)
        {
            Log.d("token",preferences.getString("google_token",null));
            startActivity(new Intent(LoginActivity.this,MainActivity.class));

        }

        super.onStart();
    }

    private Runnable color_runnable = new Runnable() {
        @Override
        public void run() {

            final int random = new Random().nextInt(5) + 1; // [0, 60] + 20 => [20, 80]

            switch (random)
            {
                case 1 :
                {
                    progress_bar.getIndeterminateDrawable()
                            .setColorFilter(ContextCompat.getColor(LoginActivity.this,R.color.blue ), PorterDuff.Mode.SRC_IN );

                    break;
                }

                case 2 :
                {
                    progress_bar.getIndeterminateDrawable()
                            .setColorFilter(ContextCompat.getColor(LoginActivity.this,R.color.orange ), PorterDuff.Mode.SRC_IN );

                    break;
                }
                case 3 :
                {
                    progress_bar.getIndeterminateDrawable()
                            .setColorFilter(ContextCompat.getColor(LoginActivity.this,R.color.green ), PorterDuff.Mode.SRC_IN );

                    break;
                }
                case 4 :
                {
                    progress_bar.getIndeterminateDrawable()
                            .setColorFilter(ContextCompat.getColor(LoginActivity.this,R.color.red), PorterDuff.Mode.SRC_IN );

                    break;

                }
                case 5 :
                {
                    progress_bar.getIndeterminateDrawable()
                            .setColorFilter(ContextCompat.getColor(LoginActivity.this,R.color.purple ), PorterDuff.Mode.SRC_IN );

                    break;
                }

            }

               handler.postDelayed(this,1550);
        }
    };

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
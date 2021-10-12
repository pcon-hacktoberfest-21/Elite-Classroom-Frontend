    package com.example.elite_classroom.Activities;

    import android.content.Intent;
    import android.os.Build;
    import android.os.Bundle;
    import android.view.View;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.ImageView;

    import androidx.annotation.RequiresApi;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.content.ContextCompat;

    import com.example.elite_classroom.Dialogs.PointDialog;
    import com.example.elite_classroom.Fragments.Teacher.NewAnnouncementFragment;
    import com.example.elite_classroom.Fragments.Teacher.NewAssignmentFragment;
    import com.example.elite_classroom.Fragments.Teacher.NewMaterialFragment;
    import com.example.elite_classroom.R;

    import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

    public class ClassWorkActivity extends AppCompatActivity implements PointDialog.PointDialogListener {

   public static ImageView cross,send_btn_image;
    String class_code="", owner_code,class_name,owner_name;


    public static ImageView attachment;
     Window window;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_work);


        window= ClassWorkActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(ClassWorkActivity.this,R.color.dark_blue_colour));

        attachment =findViewById(R.id.attachment);

        send_btn_image= findViewById(R.id.send_btn_image);

        Intent i = getIntent();
        int u = i.getIntExtra("u",0);




        class_code= i.getStringExtra("class_code");
        owner_code= i.getStringExtra("owner_id");
        class_name = i.getStringExtra("class_name");
        owner_name = i.getStringExtra("owner_name");



        Bundle b = new Bundle();
        b.putString("class_code",class_code);
        b.putString("owner_id",owner_code);
        b.putString("class_name",class_name);
        b.putString("owner_name",owner_name);







        if(u==0){

            NewAssignmentFragment newAssignmentFragment = new NewAssignmentFragment();
            newAssignmentFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2,
                    newAssignmentFragment).commit();
        }
        else if(u==1){
            NewMaterialFragment newMaterialFragment = new NewMaterialFragment();
            newMaterialFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2,
                    newMaterialFragment).commit();
        }
        else if(u==2){
            NewAnnouncementFragment newAnnouncementFragment = new NewAnnouncementFragment();
            newAnnouncementFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2,
                   newAnnouncementFragment ).commit();
        }


        cross = findViewById(R.id.cross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(ClassWorkActivity.this,ClassActivity.class);
//                i.putExtra("class_code",class_code);
//                i.putExtra("owner_id",owner_code);
//                i.putExtra("class_name",class_name);
//                i.putExtra("owner_name",owner_name);
//                i.putExtra("from_Classwork",true);
//                startActivity(i);
               ClassWorkActivity.super.onBackPressed();
                finish();
            }
        });
        attachment = findViewById(R.id.attachment);
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @Override
    public void applyTexts(String points) {

    }



    @Override
    public void onBackPressed() {
//        Intent i = new Intent(ClassWorkActivity.this,ClassActivity.class);
//        i.putExtra("class_code",class_code);
//        i.putExtra("owner_id",owner_code);
//        i.putExtra("class_name",class_name);
//        i.putExtra("owner_name",owner_name);
//        i.putExtra("from_Classwork",true);
//        startActivity(i);
        finish();
        super.onBackPressed();
    }


}
package com.example.elite_classroom.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elite_classroom.Fragments.Announcement_Fragment;
import com.example.elite_classroom.Fragments.Calender_Fragment;
import com.example.elite_classroom.Fragments.Owner_Create_Class;
import com.example.elite_classroom.Fragments.Schedule_Class_Fragment;
import com.example.elite_classroom.Fragments.Stream_Details_Fragment;
import com.example.elite_classroom.Fragments.Student_Assignment_Fragment;
import com.example.elite_classroom.Fragments.Teacher_Assignment_Fragment;
import com.example.elite_classroom.R;

public class Assignment_Submission_Activity extends AppCompatActivity {


    RelativeLayout frame_layout;
    String title, description, work_id, due_data, attachment_link, class_code,user_status;

    String  notes_id,attachment_id,posted_on,owner_token;
   public  static  TextView  back_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment__submission_);

        frame_layout= findViewById(R.id.frame_container_3);
        back_button= findViewById(R.id.back_button);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Assignment_Submission_Activity.super.onBackPressed();
            }
        });


        if( getIntent().getStringExtra("user_status")!=null)
        {


      title=            getIntent().getStringExtra("title");
      description=      getIntent().getStringExtra("description");
      work_id=          getIntent().getStringExtra("work_id");
      due_data=          getIntent().getStringExtra("due_data");
      attachment_link=  getIntent().getStringExtra("attachment_link");
      class_code=        getIntent().getStringExtra("class_code");
      user_status=      getIntent().getStringExtra("user_status");


      Bundle b = new Bundle();
      b.putString("title" , title);
      b.putString("description" , description);
      b.putString("work_id" , work_id);
      b.putString("due_data" , due_data);
      b.putString("attachment_link" , attachment_link);
      b.putString("class_code" , class_code);






      if(user_status.equals("assignment_student"))
      {
           Student_Assignment_Fragment student_assignment_fragment = new Student_Assignment_Fragment();
           student_assignment_fragment.setArguments(b);
          getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_3,
                  student_assignment_fragment ,"Student_Assignment_Fragment").commit();
      }
      else if(user_status.equals("assignment_owner"))
      {
          Teacher_Assignment_Fragment teacher_assignment_fragment = new Teacher_Assignment_Fragment();
          teacher_assignment_fragment.setArguments(b);
          getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_3,
                  teacher_assignment_fragment,"Teacher_Assignment_Fragment").commit();
      }
      else if(user_status.equals("announcement"))
      {

          Announcement_Fragment announcement_fragment = new Announcement_Fragment();
          announcement_fragment.setArguments(b);
          getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_3,
                  announcement_fragment,"Announcement_Fragment").commit();
      }


        }

        if( getIntent().getStringExtra("stream_type")!=null)
        {

          notes_id=  getIntent().getStringExtra("notes_id");
         class_code=   getIntent().getStringExtra("class_code");
         attachment_id=   getIntent().getStringExtra("attachment_id");
         posted_on=   getIntent().getStringExtra("posted_on");
         title=   getIntent().getStringExtra("title");
         description=   getIntent().getStringExtra("description");
        owner_token=    getIntent().getStringExtra("owner_token");


            Bundle b = new Bundle();
            b.putString("notes_id" ,notes_id);
            b.putString("class_code" ,class_code);
            b.putString("attachment_id" ,attachment_id);
            b.putString("posted_on" ,posted_on);
            b.putString("title" ,title);
            b.putString("description" ,description);
            b.putString("owner_token" ,owner_token);


            Stream_Details_Fragment stream_details_fragment = new  Stream_Details_Fragment();
            stream_details_fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_3,
                    stream_details_fragment,"Stream_Details_Fragment").commit();
        }

    }





}
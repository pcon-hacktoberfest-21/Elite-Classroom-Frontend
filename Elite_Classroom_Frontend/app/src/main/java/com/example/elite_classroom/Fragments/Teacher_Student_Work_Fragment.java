package com.example.elite_classroom.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Adapter.Student_Teacher_Work_Fragment_Adapter;
import com.example.elite_classroom.Models.Retrofit_Models.Student_Submission_Response;
import com.example.elite_classroom.Models.Retrofit_Models.Student_Submissions_Record;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Teacher_Student_Work_Fragment extends Fragment {


    String title, description, work_id, due_data, attachment_link, class_code,user_status;
    RecyclerView student_work_recycler_view;
    Student_Teacher_Work_Fragment_Adapter adapter ;

    ArrayList<Student_Submissions_Record> submissions_records = new ArrayList<Student_Submissions_Record>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_student_work_fragment, container, false);


        title=           getArguments().getString("title");
        description=      getArguments().getString("description");
        work_id=          getArguments().getString("work_id");
        due_data=           getArguments().getString("due_data");
        attachment_link=   getArguments().getString("attachment_link");
        class_code=        getArguments().getString("class_code");


        student_work_recycler_view= view.findViewById(R.id.student_work_recycler_view);


        getSubmissions();



        return view;
    }

    private void getSubmissions() {


        DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
//        Call<ArrayList<Student_Submissions_Record>> request = service.get_student_submissions(Integer.parseInt(work_id));

        Call<ArrayList<Student_Submissions_Record>> request = service.get_student_submissions(Integer.parseInt(work_id));


        request.enqueue(new Callback<ArrayList<Student_Submissions_Record>>() {
            @Override
            public void onResponse(Call<ArrayList<Student_Submissions_Record>> call, Response<ArrayList<Student_Submissions_Record>> response) {

                if(response.body()!=null)
                {

                    for(Student_Submissions_Record i : response.body())
                    {
                        Student_Submissions_Record submission = new Student_Submissions_Record(i.getSubmission_id(),
                                 i.getUser_id(),i.getWork_id(),i.getWork(),i.getAttachment(),i.getSubmitted_on(),i.getName());

                         submissions_records.add(submission);
                    }


                    Log.d("Submissions", submissions_records.toString());
                    adapter = new Student_Teacher_Work_Fragment_Adapter(getContext(),submissions_records);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    student_work_recycler_view.setAdapter(adapter);
                    student_work_recycler_view.setLayoutManager(layoutManager);


                }

            }

            @Override
            public void onFailure(Call<ArrayList<Student_Submissions_Record>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }
}

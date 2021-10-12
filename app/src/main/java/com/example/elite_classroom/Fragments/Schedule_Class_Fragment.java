package com.example.elite_classroom.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.elite_classroom.Models.Retrofit_Models.Auth_Responses;
import com.example.elite_classroom.Models.Retrofit_Models.Google_Logins;
import com.example.elite_classroom.Models.Retrofit_Models.Schedule_Class_Request;
import com.example.elite_classroom.Models.Retrofit_Models.Schedule_Class_Response;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Schedule_Class_Fragment extends Fragment {


    AppCompatEditText class_link;
    AppCompatEditText class_description;
    TextView select_date;
    Calendar myCalendar;
    TextView select_time;
    Button schedule_button,reschedule_button;
    String classcode="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule__class_, container, false);




        classcode = getArguments().getString("class_code");

        class_link = view.findViewById(R.id.class_link);
        class_description = view.findViewById(R.id.class_description);
        select_date = view.findViewById(R.id.select_date);
        select_time = view.findViewById(R.id.select_time);
        schedule_button = view.findViewById(R.id.schedule_button);
        myCalendar = Calendar.getInstance();


        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
                Schedule_Class_Request schedule_class = new Schedule_Class_Request(classcode,select_date.getText().toString(),select_time.getText().toString(),class_description.getText().toString(),class_link.getText().toString());
                Call<Schedule_Class_Response> request = service.schedule_Class(schedule_class);
                request.enqueue(new Callback<Schedule_Class_Response>() {
                    @Override
                    public void onResponse(Call<Schedule_Class_Response> call, Response<Schedule_Class_Response> response) {

                        if(response.body().getMessage().equals("Class scheduled!"));
                        {
                            Toast.makeText(getContext(), "Class Scheduled", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                                    new Calender_Fragment(), "Calender_FRAGMENT").commit();


                        }
                    }

                    @Override
                    public void onFailure(Call<Schedule_Class_Response> call, Throwable t) {

                    }
                });

        }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        myCalendar.set(Calendar.HOUR_OF_DAY,i);
                        myCalendar.set(Calendar.MINUTE, i1);

                        String status = "AM";

                        if(i > 11)
                        {

                            status = "PM";
                        }


                        int hour_of_12_hour_format;

                        if(i > 11){


                            hour_of_12_hour_format = i - 12;
                        }
                        else {
                            hour_of_12_hour_format = i;
                        }
                        select_time.setText(hour_of_12_hour_format + ":" + i1 + " " + status);
                    }
                };
                 new TimePickerDialog(getActivity(),time_listener, myCalendar.HOUR_OF_DAY, myCalendar.MINUTE,false).show();
            }
        });


        return view;
    }


    private void updateLabel() {
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy");

        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String time1 = date.format(myCalendar.getTime());
        select_date.setText(time1);
    }
}
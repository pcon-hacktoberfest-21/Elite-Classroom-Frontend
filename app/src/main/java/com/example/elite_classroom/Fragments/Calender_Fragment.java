package com.example.elite_classroom.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Adapter.Calender_Adapter;
import com.example.elite_classroom.Models.Recycler_Models.Class_Fixtures;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calender_Fragment extends Fragment {



   RecyclerView current_next_week,current_next_week_second;
    SharedPreferences preferences;
    String sharedPrefFile = "Login_Credentials";

    FloatingActionButton add_class_fixture_btn;
    TextView name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender_, container, false);


        preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        current_next_week = view.findViewById(R.id.current_next_week);
        current_next_week_second = view.findViewById(R.id.current_next_week_second);

        add_class_fixture_btn = view.findViewById(R.id.add_class_fixture_btn);

        name= view.findViewById(R.id.name);

        add_class_fixture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new Owner_Create_Class(),"Owner_FRAGMENT").commit();

            }
        });



        //Fetches the current week Calender
                 current_week_Calender();

        // Fetches the next week Calender
                next_week_Calender();


        return view;
    }

    private void next_week_Calender() {

        DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
        Call<ArrayList<Class_Fixtures>> request = service.next_calender(preferences.getString("google_token",null));

        request.enqueue(new Callback<ArrayList<Class_Fixtures>>() {
            @Override
            public void onResponse(Call<ArrayList<Class_Fixtures>> call, Response<ArrayList<Class_Fixtures>> response) {

                ArrayList<Class_Fixtures> fixtures = response.body();
                ArrayList<Class_Fixtures> arranged_fixtures = new ArrayList<>();

                boolean is_First_Mon=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getMon().isEmpty()))
                    {
                        if(is_First_Mon)
                        {
                            is_First_Mon=false;
                            arrange.setMon(arrange.getMon()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Tue=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getTue().isEmpty()))
                    {
                        if(is_First_Tue)
                        {
                            is_First_Tue=false;
                            arrange.setTue(arrange.getTue()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Wed=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getWed().isEmpty()))
                    {
                        if(is_First_Wed)
                        {
                            is_First_Wed=false;
                            arrange.setWed(arrange.getWed()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }
                }

                boolean is_First_Thu=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getThu().isEmpty()))
                    {
                        if(is_First_Thu)
                        {
                            is_First_Thu=false;
                            arrange.setThu(arrange.getThu()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Fri=true;
                for(Class_Fixtures arrange : fixtures)
                {
                    if(!(arrange.getFri().isEmpty()))
                    {
                        if(is_First_Fri)
                        {
                            is_First_Fri=false;
                            arrange.setFri(arrange.getFri()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Sat=true;
                for(Class_Fixtures arrange : fixtures)
                {
                    if(!(arrange.getSat().isEmpty()))
                    {
                        if(is_First_Sat)
                        {
                            is_First_Sat=false;
                            arrange.setSat(arrange.getSat()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }
                }

                boolean is_First_sun=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getSun().isEmpty()))
                    {
                        if(is_First_sun)
                        {
                            is_First_sun=false;
                            arrange.setSun(arrange.getSun()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }



                Calender_Adapter adapter = new Calender_Adapter(getContext(),arranged_fixtures,false,preferences.getString("google_token",null));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                current_next_week_second.setAdapter(adapter);
                current_next_week_second.setLayoutManager(layoutManager);



            }

            @Override
            public void onFailure(Call<ArrayList<Class_Fixtures>> call, Throwable t) {

            }
        });

    }

    private void current_week_Calender() {

        DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
        Call<ArrayList<Class_Fixtures>> request = service.current_calender(preferences.getString("google_token",null));

        request.enqueue(new Callback<ArrayList<Class_Fixtures>>() {
            @Override
            public void onResponse(Call<ArrayList<Class_Fixtures>> call, Response<ArrayList<Class_Fixtures>> response) {

                ArrayList<Class_Fixtures> fixtures = response.body();
                ArrayList<Class_Fixtures> arranged_fixtures = new ArrayList<>();

                boolean is_First_Mon=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getMon().isEmpty()))
                    {
                        if(is_First_Mon)
                        {
                            is_First_Mon=false;
                            arrange.setMon(arrange.getMon()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Tue=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getTue().isEmpty()))
                    {
                        if(is_First_Tue)
                        {
                            is_First_Tue=false;
                            arrange.setTue(arrange.getTue()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Wed=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getWed().isEmpty()))
                    {
                        if(is_First_Wed)
                        {
                            is_First_Wed=false;
                            arrange.setWed(arrange.getWed()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }
                }

                boolean is_First_Thu=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getThu().isEmpty()))
                    {
                        if(is_First_Thu)
                        {
                            is_First_Thu=false;
                            arrange.setThu(arrange.getThu()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Fri=true;
                for(Class_Fixtures arrange : fixtures)
                {
                    if(!(arrange.getFri().isEmpty()))
                    {
                        if(is_First_Fri)
                        {
                            is_First_Fri=false;
                            arrange.setFri(arrange.getFri()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }

                boolean is_First_Sat=true;
                for(Class_Fixtures arrange : fixtures)
                {
                    if(!(arrange.getSat().isEmpty()))
                    {
                        if(is_First_Sat)
                        {
                            is_First_Sat=false;
                            arrange.setSat(arrange.getSat()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }
                }

                boolean is_First_sun=true;
                for(Class_Fixtures arrange : fixtures)
                {

                    if(!(arrange.getSun().isEmpty()))
                    {
                        if(is_First_sun)
                        {
                            is_First_sun=false;
                            arrange.setSun(arrange.getSun()+"  /first");
                            arranged_fixtures.add(arrange);

                        }
                        else
                        {
                            arranged_fixtures.add(arrange);
                        }

                    }

                }



                Calender_Adapter adapter = new Calender_Adapter(getContext(),arranged_fixtures ,true,preferences.getString("google_token",null));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                current_next_week.setAdapter(adapter);
                current_next_week.setLayoutManager(layoutManager);

//                DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
//                Call<ArrayList<Class_Fixtures>> request = service.next_calender(preferences.getString("google_token",null));
//                request.enqueue(new Callback<ArrayList<Class_Fixtures>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<Class_Fixtures>> call, Response<ArrayList<Class_Fixtures>> response) {
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<Class_Fixtures>> call, Throwable t) {
//
//                    }
//                });



            }

            @Override
            public void onFailure(Call<ArrayList<Class_Fixtures>> call, Throwable t) {
                Log.d("error", t.toString());

            }
        });

    }
}
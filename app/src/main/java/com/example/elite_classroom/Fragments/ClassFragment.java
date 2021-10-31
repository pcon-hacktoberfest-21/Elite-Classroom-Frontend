package com.example.elite_classroom.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Activities.MainActivity;
import com.example.elite_classroom.Adapter.Get_Classes_Adapter;
import com.example.elite_classroom.Dialogs.ClassBottomSheetDialog;
import com.example.elite_classroom.Models.Recycler_Models.Get_Classes_List;
import com.example.elite_classroom.Models.Retrofit_Models.Get_Classes_Response;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {
    @Nullable


    FloatingActionButton buttonAddNote;
    RecyclerView recycler_view;
    String sharedPrefFile = "Login_Credentials";

    ArrayList<Get_Classes_List> classes = new ArrayList();
    Get_Classes_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        buttonAddNote = view.findViewById(R.id.class_bottom);
        recycler_view  = view.findViewById(R.id.recycler_view);

        getClasses();


        MainActivity.textView.setText("Elite Classroom");
        MainActivity.line_divider_main.setVisibility(View.VISIBLE);


        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassBottomSheetDialog bottomSheet = new ClassBottomSheetDialog();
                bottomSheet.show(getFragmentManager(), "ClassBottomSheet");
            }
        });
        return view;
    }

    private void getClasses() {

        DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
        SharedPreferences  preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        String google_token = preferences.getString("google_token",null);

        Call<Get_Classes_Response> request = service.get_Classes(google_token);

        if(!classes.isEmpty())
        {
            classes.clear();
        }

        request.enqueue(new Callback<Get_Classes_Response>() {
            @Override
            public void onResponse(Call<Get_Classes_Response> call, Response<Get_Classes_Response> response) {



               for(int i=0 ;i< response.body().getData().size() ; i++)
               {
                   Get_Classes_List single_class = response.body().getData().get(i);

                   classes.add(new Get_Classes_List(
                           single_class.getClass_code(),
                           single_class.getClass_name(),
                           single_class.getOwner_id(),
                           single_class.getNumber_of_participants(),
                           single_class.getOwner_name()
                           ));
               }


                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                adapter = new Get_Classes_Adapter(getActivity(),classes);
                recycler_view.setLayoutManager(manager);
                recycler_view.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<Get_Classes_Response> call, Throwable t) {
                Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        });

    }


}

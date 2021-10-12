package com.example.elite_classroom.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elite_classroom.Activities.MainActivity;
import com.example.elite_classroom.R;

import org.json.JSONObject;

import java.util.Objects;

public class CreateClassFragment extends Fragment {
    EditText et;
    String class_name,token;
    Button b;
    String sharedPrefFile = "Login_Credentials";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createclass, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        token = preferences.getString("google_token",null);

        MainActivity.textView.setText("Create Classroom");

        et = view.findViewById(R.id.class_name);

        b = view.findViewById(R.id.btn_create);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().toString().isEmpty()){
                    et.setError("Please Give Class Name");
                    et.requestFocus();
                }
                else{
                    class_name = et.getText().toString();
                    createClass(class_name,token);
                }
            }
        });
        return view;
    }
    public void createClass(String class_name,String token){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url = "https://elite-classroom-server.herokuapp.com/api/classrooms/newClassroom";
        JSONObject o = new JSONObject();
        try{
        o.put("className",class_name);
        o.put("google_token",token);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Toast.makeText(getActivity(),"Class created Successfully",Toast.LENGTH_SHORT).show();
            Fragment someFragment = new ClassFragment();
            assert getFragmentManager() != null;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, someFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    requestQueue.add(request);
    }
}

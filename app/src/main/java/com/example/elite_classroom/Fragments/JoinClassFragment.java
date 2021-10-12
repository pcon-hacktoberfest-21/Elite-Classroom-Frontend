package com.example.elite_classroom.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class JoinClassFragment extends Fragment {
    TextView name,email;
    Button join;
    ImageView profile;
    EditText code;
    String class_code,token,name_,email_;
    String sharedPrefFile = "Login_Credentials";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joinclass, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        token = preferences.getString("google_token",null);
        name_= preferences.getString("name",null);
        email_= preferences.getString("email", null);

        MainActivity.textView.setText("Join Class");
        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);

        name.setText(name_);
        email.setText(email_);
        profile = view.findViewById(R.id.profile_photo);
        join = view.findViewById(R.id.join_btn);
        code = view.findViewById(R.id.class_code);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class_code = code.getText().toString();
                if(class_code.isEmpty() || class_code.length()<6){
                    code.setError("Please enter Class Code");
                    code.requestFocus();
                }
                else{
                    String url="https://elite-classroom-server.herokuapp.com/api/classrooms/joinClassroom";
                    RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
                    JSONObject o = new JSONObject();
                    try {
                        o.put("classCode",class_code);
                        o.put("google_token",token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity(),"Class joined Successfully",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(),"Please Check the Class Code",Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(request);
                }
            }
        });

        return view;
    }
}

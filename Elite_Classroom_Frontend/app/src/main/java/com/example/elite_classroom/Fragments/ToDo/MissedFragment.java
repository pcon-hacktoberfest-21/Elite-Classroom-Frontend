package com.example.elite_classroom.Fragments.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elite_classroom.Adapter.MissedAdapter;
import com.example.elite_classroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class MissedFragment extends Fragment {


    String sharedPrefFile = "Login_Credentials";
    SharedPreferences preferences;
    View view;
    RecyclerView rvMissed;
    ProgressBar progressBar;
    TextView tvLoading;
    private String URL = "https://elite-classroom-server.herokuapp.com/api/todos/missing/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_missed, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tvLoading = view.findViewById(R.id.tvLoading);
        progressBar.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.VISIBLE);
        rvMissed = view.findViewById(R.id.rv_missed);
        rvMissed.setHasFixedSize(true);
        rvMissed.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        preferences = getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);


        URL = URL + preferences.getString("google_token",null);   // later to be replaced by current user token

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray missedAssignments= new JSONArray(response.get("data").toString());
                    rvMissed.setAdapter(new MissedAdapter(getContext(), missedAssignments));
                    progressBar.setVisibility(View.GONE);
                    tvLoading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                tvLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
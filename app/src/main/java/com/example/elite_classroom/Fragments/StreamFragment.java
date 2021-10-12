package com.example.elite_classroom.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.elite_classroom.Activities.ChatActivity;
import com.example.elite_classroom.Activities.ClassActivity;
import com.example.elite_classroom.Adapter.StreamAdapter;
import com.example.elite_classroom.Dialogs.ClassBottomSheetDialog;
import com.example.elite_classroom.Dialogs.ClassWorkBottomSheetDialog;
import com.example.elite_classroom.Models.Recycler_Models.Stream;
import com.example.elite_classroom.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.elite_classroom.Activities.ClassActivity.classCode;
import static com.example.elite_classroom.Activities.ClassActivity.preferences;

public class StreamFragment extends Fragment {
   ArrayList<Stream> list;
   public  static  StreamAdapter adapter;
   RecyclerView recyclerView;
    TextView class_name, owner_name;
    Context ctx;
   public static PopupMenu popupMenu;
    String token;
    String sharedPrefFile = "Login_Credentials";
    FloatingActionButton chat;
     public  static  SwipeRefreshLayout swipe_refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream, container, false);

        String class_names =getArguments().getString("class_name");
        String owner_names = getArguments().getString("owner_name");



        class_name= view.findViewById(R.id.class_name);
        owner_name= view.findViewById(R.id.owner_name);

        swipe_refresh = view.findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeColors(Color.BLACK);


        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStreams();

            }
        });
        class_name.setText(class_names);
        owner_name.setText(owner_names);
        ctx=getActivity();

        chat = view.findViewById(R.id.class_bottom);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("class_code",classCode);
                intent.putExtra("google_token",preferences.getString("google_token",null));
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        token = preferences.getString("google_token",null);

        list=new ArrayList<>();

        recyclerView=view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ClassActivity.top_menu.setVisibility(View.VISIBLE);
        ClassActivity.top_menu_second.setVisibility(View.GONE);

        ClassActivity.top_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               popupMenu = new PopupMenu(getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.refresh_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId()==R.id.refresh_option)
                        {
                            StreamFragment.swipe_refresh.setRefreshing(true);



                            getStreams();
                        }



                        return true;
                    }
                });
                popupMenu.show();

            }
        });







        getStreams();

        return view;
    }



    public  void getStreams() {



        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        String url = "https://elite-classroom-server.herokuapp.com/api/notes/getNotesCode/"+ classCode;

        if(!(list.isEmpty()))
        {
            list.clear();
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject o = response.getJSONObject(i);
                        if(o.getString("class_code").equals(classCode)){
                            Stream l = new Stream(
                                    o.getString("notes_id"),
                                    o.getString("class_code"),
                                    o.getString("attachment_id"),
                                    o.getString("posted_on"),
                                    o.getString("title"),
                                    o.getString("description"),
                                    o.getString("owner_token")
                            );
                            list.add(l);
                        }
                    }
                    adapter = new StreamAdapter(list,ctx,token);
                    recyclerView.setAdapter(adapter);
                    if(swipe_refresh.isRefreshing())
                    {
                        swipe_refresh.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

}

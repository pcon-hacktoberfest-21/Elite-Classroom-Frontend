package com.example.elite_classroom.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elite_classroom.Adapter.Chat_Adapter;
import com.example.elite_classroom.Models.Recycler_Models.Message_Format;
import com.example.elite_classroom.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {


    public Socket socket;
    EditText text;
    ImageView send_btn;
    RecyclerView chat_recycler_view;
    String name, message;
    ArrayList<Message_Format>  messages = new ArrayList<Message_Format>();
   Chat_Adapter adapter;
   LinearLayoutManager linearLayoutManager;
    SharedPreferences preferences;
    String sharedPrefFile = "Login_Credentials";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        text = findViewById(R.id.text);
        send_btn = findViewById(R.id.send_btn);

        chat_recycler_view = findViewById(R.id.chat_recycler_view);
        preferences =  ChatActivity.this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        String class_code = getIntent().getStringExtra("class_code");
        String user_token = getIntent().getStringExtra("google_token");


        Log.d("credentials","class_code"+": "+class_code +"  "+"user_token"+": "+user_token);


        try {
            IO.Options options = new IO.Options();
            options.path = "/socket.io";
            options.forceNew = true;
            options.reconnectionAttempts=3;
            options.timeout = 2000;

            if(socket==null)
            {
                try {
                    socket = IO.socket("https://elite-classroom-server.herokuapp.com/",options);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
            socket.connect();


        } catch (Exception e)
        {
            Toast.makeText(ChatActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("room" , class_code);
            jsonObject.put("user_id" , user_token);
            socket.emit("connectRoom", jsonObject);
        } catch (JSONException e) {
            Toast.makeText(ChatActivity.this, e.toString(),Toast.LENGTH_LONG).show();

        }


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String message = text.getText().toString();
               JSONObject message_body = new JSONObject();
                try {
                    message_body.put("sender",name);
                    message_body.put("msg",message);
                    message_body.put("class_id",class_code);

                    socket.emit("sendMsg",message_body);



                } catch (JSONException e) {
                    Toast.makeText(ChatActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });








//Socket Listeners
        socket.on("user_data",   new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.d("Responses" , args[0].toString());

                try {

                    JSONObject obj = new JSONObject(args[0].toString());
                     name = obj.getString("name");
                      message = obj.getString("message");
                } catch (Throwable t) {
                }
            }
        });

        socket.on("allMsg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Messages", "There are messages");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JSONArray message_list = (JSONArray) args[0];

                        for (int i =0;i<message_list.length();i++) {
                            try {
                                JSONObject json = message_list.getJSONObject(i);
                                try {
                                    Message_Format message = new Message_Format(json.getString("sender"), json.getString("msg"));
                                       messages.add(message);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                           adapter = new Chat_Adapter(ChatActivity.this,messages,preferences.getString("name",null));
                          linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
                          chat_recycler_view.setLayoutManager(linearLayoutManager);
                          chat_recycler_view.setAdapter(adapter);

                        Log.d("Messages", args[0].toString());


                    }
                });
            }
        }).on("newMsg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject json = (JSONObject)args[0];
                        try {
                            Message_Format message = new Message_Format(json.getString("sender"), json.getString("msg"));
                             messages.add(message);
                             adapter.notifyDataSetChanged();
                            text.setText("");
                             chat_recycler_view.scrollToPosition(adapter.getItemCount()-1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("New_Message",args[0].toString());
                    }
                });

            }
        });


    }



}
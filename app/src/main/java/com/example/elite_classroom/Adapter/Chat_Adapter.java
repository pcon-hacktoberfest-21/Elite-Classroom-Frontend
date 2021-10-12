package com.example.elite_classroom.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Models.Recycler_Models.Class_Fixtures;
import com.example.elite_classroom.Models.Recycler_Models.Message_Format;
import com.example.elite_classroom.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ViewHolder> {

    List<Message_Format> list1;
    Context context;
    String user_name ;

    public Chat_Adapter(Context context, ArrayList<Message_Format> list, String user_name) {
        this.list1=list;
        this.context=context;
        this.user_name = user_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.single_row_chat_recycler_view, viewGroup, false);
        return new Chat_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {



        Message_Format message= list1.get(i);
        Log.d("check_user",  message.getName()+"  "+ user_name);
        if(message.getName().equals(user_name))
        {

            viewHolder.relative_other_layout.setVisibility(View.GONE);
            viewHolder.my_message.setText(message.getMessage());
        }
        else
        {
            Toast.makeText(context,"myLayout",Toast.LENGTH_LONG).show();
            viewHolder.relative_my_layout.setVisibility(View.GONE);
            viewHolder.sender_name.setText(message.getName());
            viewHolder.sender_message.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list1.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       RelativeLayout relative_other_layout;
       TextView sender_name;
       TextView sender_message;

       RelativeLayout relative_my_layout;
       TextView my_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            relative_my_layout = itemView.findViewById(R.id.relative_my_layout);
            relative_other_layout = itemView.findViewById(R.id.relative_other_layout);
            sender_name = itemView.findViewById(R.id.sender_name);
            sender_message = itemView.findViewById(R.id.sender_message);
            my_message = itemView.findViewById(R.id.my_message);
        }
    }


}

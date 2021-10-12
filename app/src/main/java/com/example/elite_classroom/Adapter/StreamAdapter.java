package com.example.elite_classroom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elite_classroom.Activities.Assignment_Submission_Activity;
import com.example.elite_classroom.Models.Recycler_Models.Stream;
import com.example.elite_classroom.R;

import org.json.JSONObject;

import java.util.List;


public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.ViewHolder> {
    List<Stream> list;
    Context ctx;
    String token;


    public StreamAdapter(List<Stream> list, Context ctx,String token) {
        this.list = list;
        this.ctx = ctx;
        this.token = token;
    }

    @NonNull
    @Override
    public StreamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_stream,parent,false);
        return new StreamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamAdapter.ViewHolder holder, int position) {
        Stream l = list.get(position);
        holder.t.setText(l.getTitle());
        holder.t1.setText(getDateFormat(l.getPosted_on()));

        holder.parent_layout_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ctx, Assignment_Submission_Activity.class);
                intent.putExtra("notes_id", l.getNotes_id());
                intent.putExtra("class_code",l.getClass_code());
                intent.putExtra("attachment_id" , l.getAttachment_id());
                intent.putExtra("posted_on" , l.getPosted_on());
                intent.putExtra("title" , l.getTitle());
                intent.putExtra("description", l.getDescription());
                intent.putExtra("owner_token", l.getOwner_token());
                intent.putExtra("stream_type", "stream_type");
                ctx.startActivity(intent);


            }
        });

        if(token.equals(l.getOwner_token())){
            holder.b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(ctx, view);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            // TODO Auto-generated method stub
                            switch (item.getItemId()) {
                                case R.id.pop_edit:

                                    return true;
                                case R.id.pop_delete:
                                    String url = "https://elite-classroom-server.herokuapp.com/api/notes/deleteNote/"+ l.getNotes_id();
                                    RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            list.remove(position);
                                            notifyItemRemoved(position);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                                    requestQueue.add(request);
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.inflate(R.menu.pop_menu);
                    popupMenu.show();
                }
            });
        }
        else
            {

            holder.b.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView t,t1;

        ImageView b;
        RelativeLayout parent_layout_second;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout_second= itemView.findViewById(R.id.parent_layout_second);
            iv = itemView.findViewById(R.id.classwork_image);
            t = itemView.findViewById(R.id.classwork_title);
            t1 = itemView.findViewById(R.id.classwork_description);
            b = itemView.findViewById(R.id.pop_button);

        }
    }

    String getDateFormat(String date)
    {
        String identified_date;

        identified_date="Posted ";

        switch(date.substring(5,7))
        {
            case "01":
            {
                identified_date=identified_date+"Jan";
                break;
            }

            case "02":
            {
                identified_date=identified_date+"Feb";
                break;
            }
            case "03":
            {
                identified_date=identified_date+"Mar";
                break;
            }
            case "04":
            {
                identified_date=identified_date+"Apr";
                break;
            }
            case "05":
            {
                identified_date=identified_date+"May";
                break;
            }
            case "06":
            {
                identified_date=identified_date+"Jun";
                break;
            }
            case "07":
            {
                identified_date=identified_date+"July";
                break;
            }
            case "08":
            {
                identified_date=identified_date+"Aug";
                break;
            }
            case "09":
            {
                identified_date=identified_date+"Sep";
                break;
            }
            case "10":
            {
                identified_date=identified_date+"Oct";
                break;
            }
            case "11":
            {
                identified_date=identified_date+"Nov";
                break;
            }
            case "12":
            {
                identified_date=identified_date+"Dec";
                break;
            }


        }

        identified_date=identified_date+" "+date.substring(8,10);

        return identified_date;


    }
}

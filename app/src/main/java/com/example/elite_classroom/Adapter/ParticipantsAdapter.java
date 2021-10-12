package com.example.elite_classroom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantViewHolder>
{
    Context context;
    JSONArray participants;

    public ParticipantsAdapter(Context context, JSONArray participants)
    {
        this.context = context;
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rvparticipants_item, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        try {
            JSONObject jsonObject = (JSONObject) participants.get(position);
            holder.tvParticipantName.setText(jsonObject.optString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return participants.length();
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder{

        TextView tvParticipantName;
        ImageView ivDP;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);

            tvParticipantName = itemView.findViewById(R.id.tvParticipantName);
            ivDP = itemView.findViewById(R.id.ivDP);
        }
    }
}

package com.example.elite_classroom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MissedAdapter extends RecyclerView.Adapter<MissedAdapter.MissedViewHolder>
{
    Context context;
    JSONArray data;

    public MissedAdapter(Context context, JSONArray data)
    {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MissedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rvmissed_item, parent, false);
        return new MissedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissedViewHolder holder, int position) {
        try {
            JSONObject jsonObject = (JSONObject) data.get(position);
            holder.tvTitle.setText(jsonObject.optString("title"));
            holder.tvDescription.setText(jsonObject.optString("description"));
            holder.tvOwnerName.setText(jsonObject.optString("owner_name"));
            holder.tvCreationDate.setText(jsonObject.optString("created_date").substring(0,10));
            holder.tvDueDate.setText(jsonObject.optString("due_date").substring(0,10));
            holder.tvClassName.setText(jsonObject.optString("class_name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MissedViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvDescription, tvClassName, tvOwnerName, tvCreationDate, tvDueDate;

        public MissedViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);
            tvCreationDate = itemView.findViewById(R.id.tvCreationDate);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
        }
    }
}

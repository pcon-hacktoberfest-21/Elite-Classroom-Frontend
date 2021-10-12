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

public class SubmittedAdapter extends RecyclerView.Adapter<SubmittedAdapter.submittedViewHolder>
{
    private Context context;
    private JSONArray data;

    public SubmittedAdapter(Context context, JSONArray data)
    {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public submittedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rvsubmitted_item, parent, false);
        return new submittedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull submittedViewHolder holder, int position) {

        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) data.get(position);
            holder.tvWork.setText(jsonObject.optString("work"));
            holder.tvAttachment.setText(jsonObject.optString("attachment"));
            holder.tvSubmissionDate.setText(jsonObject.optString("submitted_on").substring(0, 10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    public class submittedViewHolder extends RecyclerView.ViewHolder{

        TextView tvWork, tvAttachment, tvSubmissionDate;

        public submittedViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWork = itemView.findViewById(R.id.tvWork);
            tvAttachment = itemView.findViewById(R.id.tvAttachment);
            tvSubmissionDate = itemView.findViewById(R.id.tvSubmissionDate);
        }
    }
}

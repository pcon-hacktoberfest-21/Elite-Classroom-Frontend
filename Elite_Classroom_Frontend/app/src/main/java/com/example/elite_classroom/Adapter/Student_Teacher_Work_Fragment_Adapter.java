package com.example.elite_classroom.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Models.Retrofit_Models.Student_Submissions_Record;
import com.example.elite_classroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Student_Teacher_Work_Fragment_Adapter extends RecyclerView.Adapter<Student_Teacher_Work_Fragment_Adapter.ParticipantViewHolder>
{
    Context context;
    ArrayList<Student_Submissions_Record>  submissions_records;
    String append = "https://elite-classroom-server.herokuapp.com/api/storage/download?url=";


    public Student_Teacher_Work_Fragment_Adapter(Context context, ArrayList<Student_Submissions_Record>  submissions_records)
    {
        this.context = context;
        this.submissions_records = submissions_records;


    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row_student_work, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {

        holder.student_name.setText(submissions_records.get(position).getName());
        holder.file_symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDownloading(submissions_records.get(position).getAttachment(),null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return submissions_records.size();
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder{

        TextView student_name;

        ImageView file_symbol;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);

            student_name = itemView.findViewById(R.id.student_name);
            file_symbol = itemView.findViewById(R.id.file_symbol);
        }
    }

    private void startDownloading(String url, Uri uri) {

        if(uri==null)
        {
            Log.d("download_url", url.toString());
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(append+url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

            request.setTitle("Download");
            request.setDescription("Downloading file.....");
            request.allowScanningByMediaScanner();
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            DownloadManager manager  = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            Toast.makeText(context,"Downloading file.....",Toast.LENGTH_LONG).show();
        }
    }
}

package com.example.elite_classroom.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elite_classroom.R;

import java.util.Objects;


public class Announcement_Fragment extends Fragment {


    String title, description, work_id, due_data, attachment_link, class_code,user_status;

    TextView due_date, title_field,description_field;
    ImageView file_symbol;
    String append = "https://elite-classroom-server.herokuapp.com/api/storage/download?url=";
    RelativeLayout attachement_layout;
    TextView file_name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement_, container, false);

        title=           getArguments().getString("title");
        description=      getArguments().getString("description");
        work_id=          getArguments().getString("work_id");
        due_data=           getArguments().getString(" due_data");
        attachment_link=   getArguments().getString("attachment_link");
        class_code=        getArguments().getString("class_code");



        due_date = view.findViewById(R.id.due_date);
        title_field    = view.findViewById(R.id.title);
        description_field = view.findViewById(R.id.description);
        file_symbol = view.findViewById(R.id.file_symbol);
        file_name= view.findViewById(R.id.file_name);


        title_field.setText(title);
        due_date.setText(due_data);
        description_field.setText(description);

        attachement_layout = view.findViewById(R.id.attachement_layout);
        attachement_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(attachment_link.isEmpty()))
                {
                    if(ContextCompat.checkSelfPermission(Objects.requireNonNull((Activity)getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||  ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        // Log.e(TAG, "setxml: peremission prob");
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},114);



                    } else {
                        startDownloading(attachment_link,null);
                    }
                }
            }
        });
        String mineType="";
        if(!attachment_link.isEmpty())
        {
            file_name.setText(attachment_link.substring(attachment_link.lastIndexOf('/')+1));
            mineType=attachment_link.substring(attachment_link.lastIndexOf('.')) ;
        }


        switch (mineType)
        {
            case ".pdf":
            {
                file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.pdf_placeholder));
                break;
            }

            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            {

                file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ms_word_placeholder));
                break;
            }

            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
            {
                file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.powerpoint_placeholder));
                break;
            }

            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
            {
                file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.exce_placeholder));
                break;
            }
            default:{

                if(mineType.contains("image"))
                {
                    file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.image_placeholder));
                }
                else if(mineType.contains("video"))
                {
                    file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.video_placeholder));
                }
                else
                {
                    file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_attachment));
                }
            }

        }

        return view;
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

            DownloadManager manager  = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            Toast.makeText(getContext(),"Downloading file.....",Toast.LENGTH_LONG).show();
        }
    }

    String getDateFormat(String date)
    {
        String identified_date;

        identified_date="Due  ";

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
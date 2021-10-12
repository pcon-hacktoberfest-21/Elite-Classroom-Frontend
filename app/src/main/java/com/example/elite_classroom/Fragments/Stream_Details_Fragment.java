package com.example.elite_classroom.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class Stream_Details_Fragment extends Fragment {


    String notes_id,class_code,attachment_id="",posted_on,title,description,owner_token;


    TextView due_date, title_field,description_field;
    String append = "https://elite-classroom-server.herokuapp.com/api/storage/download?url=";

    ImageView file_symbol;
    TextView file_name;

    RelativeLayout attachement_layout;
    TextView attachments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_stream__details_, container, false);


      notes_id=  getArguments().getString("notes_id");
      class_code=  getArguments().getString("class_code");
      attachment_id=  getArguments().getString("attachment_id");
      posted_on=  getArguments().getString("posted_on");
     title=   getArguments().getString("title");
     description=   getArguments().getString("description");
     owner_token=   getArguments().getString("owner_token");


        due_date = view.findViewById(R.id.due_date);
        title_field    = view.findViewById(R.id.title);
        description_field = view.findViewById(R.id.description);
        file_symbol = view.findViewById(R.id.file_symbol);
        file_name= view.findViewById(R.id.file_name);
        attachments = view.findViewById(R.id.attachments);
        attachement_layout = view.findViewById(R.id.attachement_layout);


        if(!(attachment_id.trim().isEmpty()))
        {
            attachement_layout.setVisibility(View.VISIBLE);
            attachments.setVisibility(View.VISIBLE);
        }

        attachement_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(attachment_id.trim().isEmpty()))
                {
                    if(ContextCompat.checkSelfPermission(Objects.requireNonNull((Activity)getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||  ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        // Log.e(TAG, "setxml: peremission prob");
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},114);



                    } else {
                        startDownloading(attachment_id,null);
                    }
                }
            }
        });




        due_date.setText(getDateFormat(posted_on));
        title_field.setText(title);
        description_field.setText(description);


        String mineType="";
        if(!attachment_id.trim().isEmpty())
        {
            file_name.setText(attachment_id.substring(attachment_id.lastIndexOf('/')+1));
            mineType=attachment_id.substring(attachment_id.lastIndexOf('.')) ;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


      if(requestCode==114)
        {

            startDownloading(attachment_id, null);

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
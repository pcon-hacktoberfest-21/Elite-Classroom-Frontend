package com.example.elite_classroom.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.elite_classroom.Activities.Assignment_Submission_Activity;
import com.example.elite_classroom.Activities.LoginActivity;
import com.example.elite_classroom.FeedExtraUtilsKotlin;
import com.example.elite_classroom.FileUtils;
import com.example.elite_classroom.Models.Retrofit_Models.Auth_Responses;
import com.example.elite_classroom.Models.Retrofit_Models.Delete_Submission_Response;
import com.example.elite_classroom.Models.Retrofit_Models.Google_Logins;
import com.example.elite_classroom.Models.Retrofit_Models.Student_Submission_Response;
import com.example.elite_classroom.Models.Retrofit_Models.Submission_Response;
import com.example.elite_classroom.Models.Retrofit_Models.Submit_Assignment;
import com.example.elite_classroom.Models.Retrofit_Models.Upload_Response;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;
import com.example.elite_classroom.Upload_Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class Student_Assignment_Fragment extends Fragment {


    String title, description, work_id, due_data, attachment_link, class_code,user_status;
    TextView due_date, title_field,description_field;
    ImageView file_symbol;
    TextView file_name;
    Integer  submitted_Assignment_=-20;
    String  attachment_link_second="";
    ParcelFileDescriptor parcelFileDesciptor;
    File file,file_second;
    Uri file_uri ,file_uri_second;
    RelativeLayout attachement_layout_second;
    RelativeLayout attachement_layout;
    ImageView file_symbol_second;
    TextView file_name_second,resubmit_button;
    TextView attachemnt_button, submit_button;
String append = "https://elite-classroom-server.herokuapp.com/api/storage/download?url=";
    String sharedPrefFile = "Login_Credentials";
    SharedPreferences preferences;
    TextView attachments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_fragment_assignment, container, false);

        title=           getArguments().getString("title");
        description=      getArguments().getString("description");
        work_id=          getArguments().getString("work_id");
        due_data=           getArguments().getString("due_data");
        attachment_link=   getArguments().getString("attachment_link");
        class_code=        getArguments().getString("class_code");


        attachement_layout_second = view.findViewById(R.id.attachement_layout_second);
        file_symbol_second= view.findViewById(R.id.file_symbol_second);
        file_name_second= view.findViewById(R.id.file_name_second);
        attachemnt_button= view.findViewById(R.id.attachemnt_button);
        attachemnt_button.setVisibility(View.GONE);
        submit_button = view.findViewById(R.id.submit_button);
        resubmit_button= view.findViewById(R.id.resubmit_button);
        attachement_layout= view.findViewById(R.id.attachement_layout);
          attachments = view.findViewById(R.id.attachments);
        if(!(attachment_link.trim().isEmpty()))
        {
            attachement_layout.setVisibility(View.VISIBLE);
            attachments.setVisibility(View.VISIBLE);
        }

        attachement_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(!(attachment_link.trim().isEmpty()))
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
        attachement_layout_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        if(!(attachment_link_second.isEmpty())) {
                            Log.d("attachment_link_second",attachment_link_second);
                            startDownloading(attachment_link_second, null);
                        }
                        else if(file_uri!=null)
                        {

                        }


            }
        });



        preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        Log.d("work_id", work_id+"  is your work id");
        get_Previous_Submissions(work_id);

        due_date = view.findViewById(R.id.due_date);
        title_field    = view.findViewById(R.id.title);
        description_field = view.findViewById(R.id.description);
        file_symbol = view.findViewById(R.id.file_symbol);
        file_name= view.findViewById(R.id.file_name);



        title_field.setText(title);
        due_date.setText(getDateFormat(due_data));
        description_field.setText(description);




        attachemnt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_img_dialog(0 );
            }
        });


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    create_Assignment_above_versions(file_uri_second);
                }
                else
                {
                    DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
                    RequestBody requestFile = RequestBody.create(MediaType.parse(getMimeType(file_uri)), file);
                    Log.d("MIME_type",getMimeType(file_uri).toString());

                    MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);
                    Call<Upload_Response> responseBodyCall = service.uploadFile( multipartBody);

                    responseBodyCall.enqueue(new Callback<Upload_Response>() {
                        @Override
                        public void onResponse(Call<Upload_Response> call, Response<Upload_Response> response) {

                            attachment_link_second = response.body().getLocation();

                            if(attachment_link_second!=null)
                            {
                                DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
//                           "2000-09-12 10:10:00
                                String date =  Calendar.YEAR +"-"+Calendar.MONTH+"-"+Calendar.DATE+" "+Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE+":"+Calendar.SECOND;
                                Submit_Assignment submit_assignment = new Submit_Assignment(preferences.getString("google_token",null),Integer.parseInt(work_id), "Assignment_Submission",attachment_link_second, date);
                                Call<Submission_Response> request = service.submit_assignment(submit_assignment);
                                request.enqueue(new Callback<Submission_Response>() {
                                    @Override
                                    public void onResponse(Call<Submission_Response> call, Response<Submission_Response> response) {
                                        if(response.body().getProtocol41())
                                        {
                                            Toast.makeText(getContext(),"Assignment Submitted Successfully",Toast.LENGTH_LONG).show();
                                            submitted_Assignment_ =  response.body().getInsertId();
                                            attachemnt_button.setVisibility(View.GONE);
                                            submit_button.setVisibility(View.GONE);
                                            resubmit_button.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Submission_Response> call, Throwable t) {

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(getContext(),"Assignment Could not be submitted",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Upload_Response> call, Throwable t) {

                        }
                    });
                }


            }
        });



        resubmit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //delete the Assignment fetched
                if(submitted_Assignment_!=-20)
                {
                    DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
                    Call<Delete_Submission_Response>  request = service.delete_submission(submitted_Assignment_);

                    request.enqueue(new Callback<Delete_Submission_Response>() {
                        @Override
                        public void onResponse(Call<Delete_Submission_Response> call, Response<Delete_Submission_Response> response) {

                            if(response.body()!=null)
                            {


                            if(response.body().getProtocol41())
                            {
                                Toast.makeText(getContext(),"Deleted Successfully",Toast.LENGTH_LONG).show();
                                resubmit_button.setVisibility(View.GONE);
                                attachemnt_button.setVisibility(View.VISIBLE);
                                attachement_layout_second.setVisibility(View.GONE);
                                submit_button.setVisibility(View.VISIBLE);
                            }
                            }
                            else
                            {
                                Toast.makeText(getContext(),"Unable to delete your submission",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Delete_Submission_Response> call, Throwable t) {

                            Toast.makeText(getContext(),"Failed To delete Submission",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        String mineType="";
        if(!attachment_link.trim().isEmpty())
        {
            file_name.setText(attachment_link.substring(attachment_link.lastIndexOf('/')+1));
            mineType=attachment_link.substring(attachment_link.lastIndexOf('.')) ;
//            submit_button.setVisibility(View.GONE);
//            resubmit_button.setVisibility(View.VISIBLE);
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

    private void get_Previous_Submissions(String work_id) {

        DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
       Call<ArrayList<Student_Submission_Response>> request = service.get_student_submission(Integer.parseInt(work_id),preferences.getString("google_token",null));
                  request.enqueue(new Callback<ArrayList<Student_Submission_Response>>() {
                      @Override
                      public void onResponse(Call<ArrayList<Student_Submission_Response>> call, Response<ArrayList<Student_Submission_Response>> response) {
                          if(response.body().size()==0)
                          {
                              attachemnt_button.setVisibility(View.VISIBLE);
                              resubmit_button.setVisibility(View.GONE);
                              submit_button.setVisibility(View.VISIBLE);
                          }
                          else
                          {

                              resubmit_button.setVisibility(View.VISIBLE);
                              attachement_layout_second.setVisibility(View.VISIBLE);
                              file_symbol_second.setVisibility(View.VISIBLE);
                               attachment_link_second = response.body().get(response.body().size()-1).getAttachment();
                              submitted_Assignment_ = response.body().get(response.body().size()-1).getSubmission_id();
                              String mineType="";
                              if(!(attachment_link_second.isEmpty()))
                              {
                                  file_name_second.setText(attachment_link_second.substring(attachment_link_second.lastIndexOf('/')+1));
                                  mineType=attachment_link_second.substring(attachment_link_second.lastIndexOf('.')) ;
                              }


                              switch (mineType)
                              {
                                  case ".pdf":
                                  {
                                      file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.pdf_placeholder));
                                      break;
                                  }

                                  case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                                  {

                                      file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ms_word_placeholder));
                                      break;
                                  }

                                  case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                                  {
                                      file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.powerpoint_placeholder));
                                      break;
                                  }

                                  case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                                  {
                                      file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.exce_placeholder));
                                      break;
                                  }
                                  default:{

                                      if(mineType.contains("image"))
                                      {
                                          file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.image_placeholder));
                                      }
                                      else if(mineType.contains("video"))
                                      {
                                          file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.video_placeholder));
                                      }
                                      else
                                      {
                                          file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_attachment));
                                      }
                                  }

                              }


                          }
                      }

                      @Override
                      public void onFailure(Call<ArrayList<Student_Submission_Response>> call, Throwable t) {

                      }
                  });
    }


    private void show_img_dialog(int type) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {

            openFile();

        }
        else
        {
            if(ContextCompat.checkSelfPermission(Objects.requireNonNull((Activity)getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||  ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                // Log.e(TAG, "setxml: peremission prob");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},112);



            } else {
                open_Intent(type);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==112)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                open_Intent(0);

            }
            else
            {
                Toast.makeText(getContext(),"Access Denied",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==114)
        {
            startDownloading(attachment_link,null);
        }
    }

    private void open_Intent(int type) {

        browseDocuments();

    }

    private void browseDocuments(){

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip",
                        "video/*", "image/*"
                };

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 0);



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==111)
        {
            if(data!=null)
            {



                try {
                    parcelFileDesciptor = getContext().getContentResolver().openFileDescriptor(data.getData(),"r",null);
                    if(parcelFileDesciptor==null)
                    {
                        Toast.makeText(getContext(),"File Desciptor is null",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        file_uri_second = data.getData();

                        if(file_uri_second!=null)
                        {


                            attachement_layout_second.setVisibility(View.VISIBLE);

                            file_name_second.setText(new Upload_Request().getFileName(getContext().getContentResolver(),file_uri_second));

                            attachemnt_button.setVisibility(View.GONE);


                            String mineType = getMimeType(file_uri_second);

                            switch (mineType)
                            {
                                case "application/pdf":
                                {
                                    file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.pdf_placeholder));
                                    break;
                                }

                                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                                {

                                    file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ms_word_placeholder));
                                    break;
                                }

                                case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                                {
                                    file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.powerpoint_placeholder));
                                    break;
                                }

                                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                                {
                                    file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.exce_placeholder));
                                    break;
                                }
                                default:{

                                    if(mineType.contains("image"))
                                    {
                                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.image_placeholder));
                                    }
                                    else if(mineType.contains("video"))
                                    {
                                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.video_placeholder));
                                    }
                                    else
                                    {
                                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_attachment));
                                    }
                                }

                            }
                        }
                        else
                        {
                            attachemnt_button.setVisibility(View.VISIBLE);
                        }



                    }

                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
            else
            {

            }
        }


        else
        {
            String filePath = "";

            if(getPathFromUri(getContext(),data.getData())!=null)
            {

                filePath = getPathFromUri(getContext(),data.getData());
            }
            else if(FileUtils.getPath(getContext(),data.getData())!=null)
            {
                filePath = FileUtils.getPath(getContext(),data.getData());
            }

            file_uri= data.getData();
            file = new File(filePath);


            if(file!=null)
            {


                attachement_layout_second.setVisibility(View.VISIBLE);

                file_name_second.setText(file.getName());

                attachemnt_button.setVisibility(View.GONE);


                String mineType = getMimeType(data.getData());

                switch (mineType)
                {
                    case "application/pdf":
                    {
                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.pdf_placeholder));
                        break;
                    }

                    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    {

                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ms_word_placeholder));
                        break;
                    }

                    case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                    {
                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.powerpoint_placeholder));
                        break;
                    }

                    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                    {
                        file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.exce_placeholder));
                        break;
                    }
                    default:{

                        if(mineType.contains("image"))
                        {
                            file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.image_placeholder));
                        }
                        else if(mineType.contains("video"))
                        {
                            file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.video_placeholder));
                        }
                        else
                        {
                            file_symbol_second.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_attachment));
                        }
                    }

                }
            }
            else
            {
                attachemnt_button.setVisibility(View.VISIBLE);
            }

        }




    }

    public String getPathFromUri(Context context, Uri fileUri) {
        // SDK >= 11 && SDK < 19
        if (Build.VERSION.SDK_INT < 19) {
            return FeedExtraUtilsKotlin.INSTANCE.getRealPathFromURIAPI11to18(context, fileUri);
        } else {
            return FeedExtraUtilsKotlin.INSTANCE.getRealPathFromURIAPI19(context, fileUri);
        }// SDK > 19 (Android 4.4) and up
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = getContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private void startDownloading(String url,Uri uri) {

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openFile() {


        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip",
                        "video/*", "image/*"
                };
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 111);



    }


    private void create_Assignment_above_versions(Uri file_uri_second) {


        InputStream inputStream = new FileInputStream(parcelFileDesciptor.getFileDescriptor());
        file_second = new File(getContext().getCacheDir(), new Upload_Request().getFileName(getContext().getContentResolver(),file_uri_second));
        copyInputStreamToFile(inputStream,file_second);


        DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getMimeType(file_uri_second)), file_second);
        Log.d("MIME_type",getMimeType(file_uri_second).toString());

        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",new Upload_Request().getFileName(getContext().getContentResolver(),file_uri_second),requestFile);
        Call<Upload_Response> responseBodyCall = service.uploadFile( multipartBody);

        responseBodyCall.enqueue(new Callback<Upload_Response>() {
            @Override
            public void onResponse(Call<Upload_Response> call, retrofit2.Response<Upload_Response> response) {

                attachment_link_second= response.body().getLocation();

                if(attachment_link_second!=null)
                {
                    DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
//                           "2000-09-12 10:10:00
                    String date =  Calendar.YEAR +"-"+Calendar.MONTH+"-"+Calendar.DATE+" "+Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE+":"+Calendar.SECOND;
                    Submit_Assignment submit_assignment = new Submit_Assignment(preferences.getString("google_token",null),Integer.parseInt(work_id), "Assignment_Submission",attachment_link_second, date);
                    Call<Submission_Response> request = service.submit_assignment(submit_assignment);
                    request.enqueue(new Callback<Submission_Response>() {
                        @Override
                        public void onResponse(Call<Submission_Response> call, Response<Submission_Response> response) {
                            if(response.body()!=null)
                            {
                                Toast.makeText(getContext(),"Assignment Submitted Successfully",Toast.LENGTH_LONG).show();
                                submitted_Assignment_ =  response.body().getInsertId();
                                attachemnt_button.setVisibility(View.GONE);
                                submit_button.setVisibility(View.GONE);
                                resubmit_button.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Toast.makeText(getContext(),"Could not submit_Assignment",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Submission_Response> call, Throwable t) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(getContext(),"Assignment Could not be submitted",Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<Upload_Response> call, Throwable t) {
                Log.d("Access_Error", t.toString());
            }
        });


    }

    private void copyInputStreamToFile( InputStream in, File file ) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
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

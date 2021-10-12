package com.example.elite_classroom.Fragments.Teacher;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.util.Output;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elite_classroom.Activities.ClassActivity;
import com.example.elite_classroom.Activities.ClassWorkActivity;
import com.example.elite_classroom.Dialogs.PointDialog;
import com.example.elite_classroom.FeedExtraUtilsKotlin;
import com.example.elite_classroom.FileUtils;
import com.example.elite_classroom.Models.Retrofit_Models.Upload_Response;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;
import com.example.elite_classroom.UploadRequestBody;
import com.example.elite_classroom.Upload_Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NewAssignmentFragment extends Fragment implements PointDialog.PointDialogListener {

    EditText assignment_title,assignment_description,point;
    TextView due_date;
    Button crete_assignment;
    Calendar myCalendar;
    String attachment_link = "";
    ParcelFileDescriptor parcelFileDesciptor;
    String attachment_path_before_upload = "";
    File file,file_second;
    Uri file_uri,file_uri_second;
    String class_code="", owner_code,class_name,owner_name;
    private static Integer MY_PERMISSIONS_REQUEST_READ_STORAGE = 111;
    private static Integer PICK_IMAGE = 101;

    TextView attachement_text;
     View  attachment_divider;
     RelativeLayout attachement_layout;
     ImageView file_symbol;
     TextView file_name;

    String path;
    Boolean is_video = false, normal_file_bool = false;
    String title,description="",points="100",due="No due date",token;
    String sharedPrefFile = "Login_Credentials";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newassignment, container, false);


         attachement_text= view.findViewById(R.id.attachement_text);
         attachment_divider= view.findViewById(R.id.attachment_divider);
         attachement_layout= view.findViewById(R.id.attachement_layout);
         file_symbol= view.findViewById(R.id.file_symbol);
         file_name= view.findViewById(R.id.file_name);



        class_code= getArguments().getString("class_code");
        owner_code= getArguments().getString("owner_id");
        class_name = getArguments().getString("class_name");
        owner_name = getArguments().getString("owner_name");


        ClassWorkActivity.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           show_img_dialog(0 );
            }
        });



        SharedPreferences preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        token= preferences.getString("google_token",null);
        //DatePicker
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        assignment_title = view.findViewById(R.id.assignment_title);
        assignment_description = view.findViewById(R.id.assignment_description);
        point = view.findViewById(R.id.point);
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        due_date = view.findViewById(R.id.due_date);
        due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DatePickerDialog datePickerDialog =  new  DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
             datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(),R.color.dark_blue_colour));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(),R.color.dark_blue_colour));

            }
        });
        crete_assignment = view.findViewById(R.id.crete_assignment);
        ClassWorkActivity.send_btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    create_Assignment_above_versions(file_uri_second);

                }
                else
                {

                    if(file_uri!=null)
                    {


                    DestinationService service = ServiceBuilder.INSTANCE.buildService(DestinationService.class);
                    RequestBody requestFile = RequestBody.create(MediaType.parse(getMimeType(file_uri)), file);
                    Log.d("MIME_type",getMimeType(file_uri).toString());

                    MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);
                    Call<Upload_Response> responseBodyCall = service.uploadFile( multipartBody);

                    responseBodyCall.enqueue(new Callback<Upload_Response>() {
                        @Override
                        public void onResponse(Call<Upload_Response> call, retrofit2.Response<Upload_Response> response) {

                            attachment_link = response.body().getLocation();



                            title = assignment_title.getText().toString();
                            description = assignment_description.getText().toString();
                            points = point.getText().toString();
                            due = due_date.getText().toString();
                            if(title.isEmpty()){
                                assignment_title.setError("Please enter Title");
                                assignment_title.requestFocus();
                            }
                            else{


                                createAssignment(title,description,points,due,attachment_link);
                            }

                            Log.d("File_Upload",response.body().getLocation());
                        }

                        @Override
                        public void onFailure(Call<Upload_Response> call, Throwable t) {
                            Log.d("Access_Error", t.toString());
                        }
                    });
                    }
                    else
                    {
                        title = assignment_title.getText().toString();
                        description = assignment_description.getText().toString();
                        points = point.getText().toString();
                        due = due_date.getText().toString();
                        if(title.isEmpty()){
                            assignment_title.setError("Please enter Title");
                            assignment_title.requestFocus();
                        }
                        else{


                            createAssignment(title,description,points,due," ");
                        }
                    }
                }

            }
        });
        return view;
    }

    private void create_Assignment_above_versions(Uri file_uri_second) {


            if(parcelFileDesciptor!=null)
            {
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

                attachment_link = response.body().getLocation();



                title = assignment_title.getText().toString();
                description = assignment_description.getText().toString();
                points = point.getText().toString();
                due = due_date.getText().toString();
                if(title.isEmpty()){
                    assignment_title.setError("Please enter Title");
                    assignment_title.requestFocus();
                }
                else{


                    createAssignment(title,description,points,due,attachment_link);
                }

                Log.d("File_Upload",response.body().getLocation());
            }

            @Override
            public void onFailure(Call<Upload_Response> call, Throwable t) {
                Toast.makeText(getContext()," Access Error "+t.toString(),Toast.LENGTH_SHORT).show();            }
        });
            }
            else
            {
                title = assignment_title.getText().toString();
                description = assignment_description.getText().toString();
                points = point.getText().toString();
                due = due_date.getText().toString();
                if(title.isEmpty()){
                    assignment_title.setError("Please enter Title");
                    assignment_title.requestFocus();
                }
                else{


                    createAssignment(title,description,points,due," ");
                }

            }


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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                Toast.makeText(getContext(),"Access Denied ",Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode==120)
        {


            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openFile();
            }
            else
            {
                Toast.makeText(getContext(),"Access Denied",Toast.LENGTH_LONG).show();
            }


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
                             attachement_text.setVisibility(View.VISIBLE);
                             attachment_divider.setVisibility(View.VISIBLE);
                             attachement_layout.setVisibility(View.VISIBLE);

                             file_name.setText(new Upload_Request().getFileName(getContext().getContentResolver(),file_uri_second));


                             String mineType = getMimeType(file_uri_second);

                             switch (mineType)
                             {
                                 case "application/pdf":
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
            if (data != null) {
                if (getPathFromUri(getContext(), data.getData()) != null) {

                    filePath = getPathFromUri(getContext(), data.getData());
                } else if (FileUtils.getPath(getContext(), data.getData()) != null) {
                    filePath = FileUtils.getPath(getContext(), data.getData());
                }

                file_uri = data.getData();
                file = new File(filePath);
            }


            if (file != null) {
                attachement_text.setVisibility(View.VISIBLE);
                attachment_divider.setVisibility(View.VISIBLE);
                attachement_layout.setVisibility(View.VISIBLE);

                file_name.setText(file.getName());


                String mineType = getMimeType(data.getData());

                switch (mineType) {
                    case "application/pdf": {
                        file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.pdf_placeholder));
                        break;
                    }

                    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": {

                        file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ms_word_placeholder));
                        break;
                    }

                    case "application/vnd.openxmlformats-officedocument.presentationml.presentation": {
                        file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.powerpoint_placeholder));
                        break;
                    }

                    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": {
                        file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.exce_placeholder));
                        break;
                    }
                    default: {

                        if (mineType.contains("image")) {
                            file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.image_placeholder));
                        } else if (mineType.contains("video")) {
                            file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.video_placeholder));
                        } else {
                            file_symbol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_attachment));
                        }
                    }

                }
            }

        }


//


    }

    private void updateLabel() {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String time1 = date.format(myCalendar.getTime());
        due_date.setText(time1);
    }
    public void openDialog() {
        PointDialog pointDialog = new PointDialog();
        assert getFragmentManager() != null;
        pointDialog.show(getFragmentManager(), "point dialog");
    }

    public void createAssignment(String title,String description,String points,String due_date,String attachment_link){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url = "https://elite-classroom-server.herokuapp.com/api/classworks/createClasswork";
        JSONObject o = new JSONObject();
        try {

            o.put("class_code", class_code);
            o.put("title", title);
            o.put("description", description);
            o.put("type",0);
            o.put("attachment",attachment_link);
            o.put("due_date", due_date);
            o.put("google_token",token);
            o.put("points", points);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                ClassWorkActivity.cross.performClick();
                ClassActivity.top_menu_second.performClick();
//                Intent i = new Intent(getActivity(),ClassActivity.class);
//                i.putExtra("class_code",class_code);
//                i.putExtra("owner_id",owner_code);
//                i.putExtra("class_name",class_name);
//                i.putExtra("owner_name",owner_name);
//                i.putExtra("from_Classwork",true);
//                startActivity(i);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void applyTexts(String points) {
        Log.i("point",points);
       point.setText(points);
    }

    public String getPathFromUri(Context context, Uri fileUri) {
        // SDK >= 11 && SDK < 19
        if (Build.VERSION.SDK_INT < 19) {
            return FeedExtraUtilsKotlin.INSTANCE.getRealPathFromURIAPI11to18(context, fileUri);
        } else {
            return FeedExtraUtilsKotlin.INSTANCE.getRealPathFromURIAPI19(context, fileUri);
        }// SDK > 19 (Android 4.4) and up
    }


    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
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



}

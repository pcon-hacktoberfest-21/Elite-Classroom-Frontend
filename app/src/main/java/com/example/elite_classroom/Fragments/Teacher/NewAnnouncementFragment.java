package com.example.elite_classroom.Fragments.Teacher;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.elite_classroom.FeedExtraUtilsKotlin;
import com.example.elite_classroom.FileUtils;
import com.example.elite_classroom.Models.Retrofit_Models.Upload_Response;
import com.example.elite_classroom.R;
import com.example.elite_classroom.Retrofit.DestinationService;
import com.example.elite_classroom.Retrofit.ServiceBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NewAnnouncementFragment extends Fragment {
    EditText announcement_title,announcement_description;
    Button create_announcement;
    String class_code="", owner_code,class_name,owner_name;
    String token;
    String attachment_link = "";
    File file;
    Uri file_uri;
    String sharedPrefFile = "Login_Credentials";



    TextView attachement_text;
    View  attachment_divider;
    RelativeLayout attachement_layout;
    ImageView file_symbol;
    TextView file_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newannouncement, container, false);



        class_code= getArguments().getString("class_code");
        owner_code= getArguments().getString("owner_id");
        class_name = getArguments().getString("class_name");
        owner_name = getArguments().getString("owner_name");


        attachement_text= view.findViewById(R.id.attachement_text);
        attachment_divider= view.findViewById(R.id.attachment_divider);
        attachement_layout= view.findViewById(R.id.attachement_layout);
        file_symbol= view.findViewById(R.id.file_symbol);
        file_name= view.findViewById(R.id.file_name);

        SharedPreferences preferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        token = preferences.getString("google_token",null);

        ClassWorkActivity.attachment.setClickable(false);
        ClassWorkActivity.attachment.setVisibility(View.GONE);
//        ClassWorkActivity.attachment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                show_img_dialog(0 );
//            }
//        });

        announcement_title = view.findViewById(R.id.announcement_title);
        announcement_description = view.findViewById(R.id.announcement_description);
        create_announcement = view.findViewById(R.id.create_announcement);

        ClassWorkActivity.send_btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = announcement_title.getText().toString();
                        String description = announcement_description.getText().toString();
                        if(title.isEmpty()){
                            announcement_title.setError("Please enter Title");
                            announcement_title.requestFocus();
                        }
                        else if(description.isEmpty()){
                            announcement_description.setError("Please enter Description");
                            announcement_description.requestFocus();
                        }
                        else{
                            announcement(title,description,"");
                        }
                    }
        });
        return view;
    }



    private void show_img_dialog(int type) {
        if(ContextCompat.checkSelfPermission(Objects.requireNonNull((Activity)getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||  ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            // Log.e(TAG, "setxml: peremission prob");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},112);



        } else {
            open_Intent(type);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            attachement_text.setVisibility(View.VISIBLE);
            attachment_divider.setVisibility(View.VISIBLE);
            attachement_layout.setVisibility(View.VISIBLE);

            file_name.setText(file.getName());


            String mineType = getMimeType(data.getData());

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




    public void announcement(String title,String description,String attachment_link){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        String url = "https://elite-classroom-server.herokuapp.com/api/classworks/createClasswork";
        JSONObject o = new JSONObject();
        try {

            o.put("class_code", class_code);
            o.put("title", title);
            o.put("description", description);
            o.put("type",1);
            o.put("attachment",attachment_link);
            o.put("due_date", "2021-02-22");
            o.put("google_token",token);
            o.put("points", "100");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ClassWorkActivity.cross.performClick();

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



                }
        });
        requestQueue.add(request);
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

    public String getPathFromUri(Context context, Uri fileUri) {
        // SDK >= 11 && SDK < 19
        if (Build.VERSION.SDK_INT < 19) {
            return FeedExtraUtilsKotlin.INSTANCE.getRealPathFromURIAPI11to18(context, fileUri);
        } else {
            return FeedExtraUtilsKotlin.INSTANCE.getRealPathFromURIAPI19(context, fileUri);
        }// SDK > 19 (Android 4.4) and up
    }


}

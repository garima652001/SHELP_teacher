package com.users.shelp_teacher.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.FileUtils;
import com.users.shelp_teacher.R;
import com.users.shelp_teacher.Response.CourseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Createfragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST=100;
    View view;
    TextView path;
    EditText et_name,et_title,et_description, et_req,user_id;
    Button btn,button1,button2,button3,button4,button5,button6,upload;
    Context mcontext;
    String id;
    String category ;
    ProgressDialog progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createfragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        path=view.findViewById(R.id.filepath);
        btn=view.findViewById(R.id.btn_choose);
        et_name=view.findViewById(R.id.creatorname);
        et_title=view.findViewById(R.id.course_title);
        et_description=view.findViewById(R.id.course_des);
        et_req=view.findViewById(R.id.course_req);
        upload=view.findViewById(R.id.btn_create);
        SharedPreferences preferences=getContext().getSharedPreferences("Id",0);
        user_id=view.findViewById(R.id.user_idpref);
        user_id.setText(preferences.getString("Id",null));
        id = user_id.getText().toString();
        button1=view.findViewById(R.id.btn_photography);
        button2=view.findViewById(R.id.btn_dev);
        button3=view.findViewById(R.id.btn_design);
        button4=view.findViewById(R.id.btn_ml);
        button5=view.findViewById(R.id.btn_react);
        button6=view.findViewById(R.id.btn_node);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST);
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Photography";
                button1.setBackgroundResource(R.drawable.bg);
                button2.setBackgroundResource(R.drawable.background);
                button3.setBackgroundResource(R.drawable.background);
                button4.setBackgroundResource(R.drawable.background);
                button5.setBackgroundResource(R.drawable.background);
                button6.setBackgroundResource(R.drawable.background);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Development";
                button2.setBackgroundResource(R.drawable.bg);
                button1.setBackgroundResource(R.drawable.background);
                button3.setBackgroundResource(R.drawable.background);
                button4.setBackgroundResource(R.drawable.background);
                button5.setBackgroundResource(R.drawable.background);
                button6.setBackgroundResource(R.drawable.background);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Designing";
                button3.setBackgroundResource(R.drawable.bg);
                button1.setBackgroundResource(R.drawable.background);
                button2.setBackgroundResource(R.drawable.background);
                button4.setBackgroundResource(R.drawable.background);
                button5.setBackgroundResource(R.drawable.background);
                button6.setBackgroundResource(R.drawable.background);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Machine Learning";
                button4.setBackgroundResource(R.drawable.bg);
                button1.setBackgroundResource(R.drawable.background);
                button2.setBackgroundResource(R.drawable.background);
                button3.setBackgroundResource(R.drawable.background);
                button5.setBackgroundResource(R.drawable.background);
                button6.setBackgroundResource(R.drawable.background);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="React";
                button5.setBackgroundResource(R.drawable.bg);
                button1.setBackgroundResource(R.drawable.background);
                button2.setBackgroundResource(R.drawable.background);
                button3.setBackgroundResource(R.drawable.background);
                button4.setBackgroundResource(R.drawable.background);
                button6.setBackgroundResource(R.drawable.background);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="Node JS";
                button6.setBackgroundResource(R.drawable.bg);
                button1.setBackgroundResource(R.drawable.background);
                button2.setBackgroundResource(R.drawable.background);
                button3.setBackgroundResource(R.drawable.background);
                button4.setBackgroundResource(R.drawable.background);
                button5.setBackgroundResource(R.drawable.background);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),id,Toast.LENGTH_LONG).show();
                Intent myfileintent = new Intent();
                myfileintent.setAction(Intent.ACTION_GET_CONTENT);
                myfileintent.setType("*/*");
               // myfileintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(myfileintent, "image"), 10);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case MY_PERMISSIONS_REQUEST:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Uri uri;
        if(resultCode==RESULT_OK&&requestCode==10&&data!=null) {
            {
                uri =data.getData();
            }
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress= new ProgressDialog(getActivity());
                    progress.setTitle("Uploading");
                    progress.setMessage("Please Wait...");
                    progress.show();
                    uploadfile(uri);
                }
            });
        }
    }

    private RequestBody createStringPart(String des_string)
    {
        return RequestBody.create(MultipartBody.FORM,des_string);
    }

    private MultipartBody.Part preparefilePart(String partname, Uri uri){
        File originalfile= FileUtils.getFile(getContext(),uri);
        RequestBody filepart= RequestBody.create(
                MediaType.parse(getActivity().getContentResolver().getType(uri)),
                originalfile);
        return MultipartBody.Part.createFormData(partname,originalfile.getName(),filepart);
    }

    private void uploadfile(Uri uri) {
        Call<CourseResponse> call= Retroclient.getInstance().getapi().uploadPhoto(
                createStringPart(et_name.getText().toString()),
                createStringPart(et_title.getText().toString()),
                createStringPart(et_description.getText().toString()),
                createStringPart(category),
                createStringPart(id),
                createStringPart(et_req.getText().toString()),
                preparefilePart("image",uri));

        call.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if(response.isSuccessful()){
                    CourseResponse res= response.body();
                    Toast.makeText(getContext(),res.getMessage(),Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        String string ;
                        string = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(string);
                        String wrong ;
                        wrong = jsonObject.getString("message");
                        Toast.makeText(getContext(), wrong, Toast.LENGTH_LONG).show();
                    }
                    catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(),Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });
    }

  /*@Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_dev:
                category="Development";
                v.findViewById(R.id.btn_dev).setBackgroundResource(R.drawable.bg);
                break;
            case R.id.btn_design:
                category="Designing";
                v.findViewById(R.id.btn_design).setBackgroundResource(R.drawable.bg);
                break;

            case R.id.btn_ml:
                category="Machine Learning";
                v.findViewById(R.id.btn_ml).setBackgroundResource(R.drawable.bg);
                break;
            case R.id.btn_react:
                category="React";
                v.findViewById(R.id.btn_react).setBackgroundResource(R.drawable.bg);
                break;
            case R.id.btn_node:
                category="Node JS";
                v.findViewById(R.id.btn_node).setBackgroundResource(R.drawable.bg);
                break;
        }
    }*/
}
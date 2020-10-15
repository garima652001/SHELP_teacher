package com.users.shelp_teacher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;

import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Response.CourseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Videoactivity extends AppCompatActivity {
    Button selectvid, uploadvid;
    VideoView video;
    String courseid;
    ProgressDialog progress;
   // VideoView videoview;
    //MediaController mcontroller;
    TextView videopath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoactivity);

        selectvid = findViewById(R.id.btn_selv);
        uploadvid = findViewById(R.id.btn_uploadv);
        courseid = getIntent().getStringExtra("cid");
        //videoview=findViewById(R.id.videoView);
         videopath= findViewById(R.id.vidpath);

      //  Toast.makeText(this, "on create "+courseid, Toast.LENGTH_SHORT).show();
         selectvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vidintent = new Intent();
                vidintent.setAction(Intent.ACTION_GET_CONTENT);
                vidintent.setType("video/*");
                startActivityForResult(vidintent, 10);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Uri uri;
        if (resultCode == RESULT_OK && requestCode == 10 && data != null) {
            {
                uri = data.getData();
                //videopath.setVisibility(View.VISIBLE);
                //videopath.setText(data.getData().getPath());
            }
            uploadvid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress = new ProgressDialog(Videoactivity.this);
                    progress.setTitle("Uploading");
                    progress.setMessage("Please Wait...");
                    progress.show();
                    uploadfile(uri);
                }
            });
        }
    }

    private MultipartBody.Part preparefilePart(String partname, Uri uri) {
        File originalfile = FileUtils.getFile(getApplicationContext(), uri);
        RequestBody filepart = RequestBody.create(
                MediaType.parse(this.getContentResolver().getType(uri)),
                originalfile);
        return MultipartBody.Part.createFormData(partname, originalfile.getName(), filepart);
    }

    private void uploadfile(Uri uri) {
        //Toast.makeText(this, courseid, Toast.LENGTH_SHORT).show();
        Call<ResponseBody> call = Retroclient
                .getInstance()
                .getapi()
                .uploadVideo(courseid, preparefilePart("video", uri));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                        //String res = response.body().toString();
                        //JSONObject obj = new JSONObject(res);
                       // String res1= obj.getString("message");
                        Toast.makeText(Videoactivity.this,"Video successfully added",Toast.LENGTH_LONG).show();
                    progress.dismiss();

                }
                else
                {
                    progress.dismiss();
                    Toast.makeText(Videoactivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progress.dismiss();

                Toast.makeText(Videoactivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
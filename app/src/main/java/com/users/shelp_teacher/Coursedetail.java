package com.users.shelp_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Request.Mycourses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Coursedetail extends AppCompatActivity {

    TextView title1, teacher1, rate1, requirement1, description1,longdes1,outcome1;
    RatingBar ratebar1;
    ImageView cimg1;
    String id,header,name;
    int pos;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetail);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Id", 0);
        id = preferences.getString("Id", null);

        SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("Name", 0);
        name = preferences1.getString("Name", null);

        SharedPreferences preference = getApplicationContext().getSharedPreferences("Token", 0);
        header = "Bearer " + preference.getString("Token", null);

        progressBar= findViewById(R.id.progbar2);
        title1 = findViewById(R.id.tv_ctitle);
        teacher1 = findViewById(R.id.creator);
        requirement1 = findViewById(R.id.requiremnets);
        description1 = findViewById(R.id.description);
        longdes1 = findViewById(R.id.longdesc);
        outcome1 = findViewById(R.id.willlearn);
        rate1 = findViewById(R.id.rating);
        cimg1 = findViewById(R.id.courseimg);
        ratebar1 = findViewById(R.id.ratingBar);

        pos = getIntent().getIntExtra("position", 0);
        displaydetails();

    }


    private void displaydetails() {
            Mycourses mycourses = new Mycourses(id);
            Call<ResponseBody> call = Retroclient
                    .getInstance()
                    .getapi()
                    .mycourses(mycourses, header);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            Toast.makeText(Coursedetail.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        try {
                            // Toast.makeText(getContext(),header,Toast.LENGTH_LONG).show();
                            String s = response.body().string();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("data");

                                JSONObject obj = array.getJSONObject(pos);
                                JSONObject objrating = obj.getJSONObject("rating");
                                double rating = objrating.getDouble("ratingFinal");
                                String title = obj.getString("title");

                                String imgurl = obj.getString("imageurl");
                                imgurl = "https://shelp-webapp.herokuapp.com/" + imgurl;

                                String description = obj.getString("discription");

                                String req = obj.getString("requirement");
                                String longdes = obj.getString("discriptionLong");
                               String outcome = obj.getString("willLearn");

                            ratebar1.setRating((float) rating);
                            String starrate = Double.toString(rating);
                            title1.setText(title);
                            Picasso.get()
                                    .load(imgurl)
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .into(cimg1);
                            teacher1.setText(name);
                            rate1.setText(starrate);
                            requirement1.setText(req);
                            description1.setText(description);
                           longdes1.setText(longdes);
                            outcome1.setText(outcome);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Coursedetail.this, t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });


        }
}
package com.users.shelp_teacher.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.users.shelp_teacher.Adapter;
import com.users.shelp_teacher.Api.Retroclient;
import com.users.shelp_teacher.Model;
import com.users.shelp_teacher.R;
import com.users.shelp_teacher.Request.Mycourses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Homefragment extends Fragment{

    RecyclerView recyclerView;
    List<Model> itemlist;
    String id;
    String name;
    Button btn_createcourse;
    ProgressBar progressBar;
    boolean c= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        SharedPreferences preferences = getContext().getSharedPreferences("Id", 0);
        id = preferences.getString("Id", null);

        SharedPreferences preferences1 = getContext().getSharedPreferences("Name", 0);
        name = preferences1.getString("Name", null);
        SharedPreferences preference = getContext().getSharedPreferences("Token", 0);
        final String header = "Bearer " + preference.getString("Token", null);
        progressBar=view.findViewById(R.id.progbar1);

        btn_createcourse= view.findViewById(R.id.btn_directcreate);
        btn_createcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Createfragment();
                FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
            }
        });
        recyclerView = view.findViewById(R.id.recyclerv);
        itemlist = new ArrayList<>();
        checkinternet();

        if(c) {
            Mycourses mycourses = new Mycourses(id);
            Call<ResponseBody> call = Retroclient
                    .getInstance()
                    .getapi()
                    .mycourses(mycourses, header);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        try {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            // Toast.makeText(getContext(),header,Toast.LENGTH_LONG).show();
                            String s = response.body().string();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                JSONObject objrating = obj.getJSONObject("rating");
                                double rating = objrating.getDouble("ratingFinal");

                                String title = obj.getString("title");

                                String imgurl = obj.getString("imageurl");
                                imgurl = "https://shelp-webapp.herokuapp.com/" + imgurl;

                                itemlist.add(new Model(imgurl, name, title, rating));

                                // String description= obj.getString("discription");
                                // String req = obj.getString("requirement");
                           /* recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                            Adapter adapter = new Adapter(itemlist);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();*/
                            }
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            Adapter adapter = new Adapter(itemlist, getContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return view;
    }
    private void checkinternet() {
        ConnectivityManager manager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE || activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                c = true;
            }
        } else {

            dialogboxfun();

        }
    }

        private void dialogboxfun() {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("No internet Connection");
            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            checkinternet();
                        }
                    });
            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog alert = builder1.create();
            alert.show();

        }
}
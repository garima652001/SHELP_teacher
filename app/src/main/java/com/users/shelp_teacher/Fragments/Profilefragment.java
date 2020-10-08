package com.users.shelp_teacher.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.users.shelp_teacher.LoginActivity;
import com.users.shelp_teacher.R;
import com.users.shelp_teacher.Sharedprefs;

public class Profilefragment extends Fragment {

    TextView t_name, t_email;
    Button logout;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        SharedPreferences preferences = getContext().getSharedPreferences("Name", 0);
        t_name = view.findViewById(R.id.teachername);
        t_name.setText(preferences.getString("Name", null));
        SharedPreferences preferences1 = getContext().getSharedPreferences("Email", 0);
        t_email = view.findViewById(R.id.teacheremail);
        t_email.setText(preferences1.getString("Email", null));
        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharedprefs.saveSharedsetting(getActivity(),"Clip","true");
                Sharedprefs.sharedprefsave(getContext(),"","","","");
                Intent signout = new Intent(getContext(), LoginActivity.class);
                startActivity(signout);
                getActivity().finish();
            }
        });

    }
}

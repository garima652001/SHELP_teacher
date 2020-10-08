package com.users.shelp_teacher;

import android.content.Context;
import android.content.SharedPreferences;

    public class Sharedprefs {
        private static final String SHARED_PREFS="sharedpref";


        public static String readShared(Context ctx, String setname, String defvalue)
        {
            SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
            return sharedPreferences.getString(setname,defvalue);
        }

        public static void saveSharedsetting(Context ctx,String setname, String setvalue){
            SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString(setname,setvalue);
            editor.apply();
        }
        public static void sharedprefsave(Context ctx, String name,String token,String userid,String email){
            SharedPreferences prefs= ctx.getSharedPreferences("Name",0);
            SharedPreferences.Editor prefedit= prefs.edit();
            prefedit.putString("Name", name);
            prefedit.commit();
            SharedPreferences prefs_token= ctx.getSharedPreferences("Token",0);
            SharedPreferences.Editor prefedit_token= prefs_token.edit();
            prefedit_token.putString("Token", token);
            prefedit_token.commit();
            SharedPreferences prefs_id= ctx.getSharedPreferences("Id",0);
            SharedPreferences.Editor prefedit_id= prefs_id.edit();
            prefedit_id.putString("Id", userid);
            prefedit_id.commit();
            SharedPreferences prefs_id1= ctx.getSharedPreferences("Email",0);
            SharedPreferences.Editor prefedit_id1= prefs_id1.edit();
            prefedit_id1.putString("Email", email);
            prefedit_id1.commit();

        }
    }


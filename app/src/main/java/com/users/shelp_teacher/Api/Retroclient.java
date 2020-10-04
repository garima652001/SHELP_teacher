package com.users.shelp_teacher.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroclient {
        private static final String baseurl = "http://192.168.43.146:8080/";

        private static Retroclient m_instance;
        private Retrofit retrofit;

        private Retroclient() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static synchronized Retroclient getInstance() {
            if (m_instance == null)
                m_instance = new Retroclient();

            return m_instance;
        }

        public api getapi() {
            return retrofit.create(api.class);

        }
    }


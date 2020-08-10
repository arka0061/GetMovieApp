package com.example.movieinfoapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MovieSingletonClass {

    private static MovieSingletonClass mInstance;
    private RequestQueue mRequestQueue;

    public MovieSingletonClass(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized MovieSingletonClass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MovieSingletonClass(context);
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

}

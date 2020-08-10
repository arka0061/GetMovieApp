package com.example.movieinfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String mBaseUrl1 = "https://api.themoviedb.org/3/search/movie?api_key=6d27a877fa78f90fefd0ffd6d380878b&language=en-US&query=";
    private final String mBaseUrl2 = "&page=1&include_adult=true";
    private Button mButton;
    private EditText mMovieName;
    private TextView mGenre, mReleaseDate, mRating, mOverview, mTitle;
    private String mFinalUrl;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.idGoGoGo);
        mTitle = findViewById(R.id.idTitle);
        mMovieName = findViewById(R.id.idEnterMovie);
        mGenre = findViewById(R.id.idGenre);
        mReleaseDate = findViewById(R.id.idReleaseDate);
        mRating = findViewById(R.id.idRating);
        mOverview = findViewById(R.id.idOverview);

        requestQueue = MovieSingletonClass.getInstance(this).getmRequestQueue();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = mMovieName.getText().toString();
                if (movieName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter movie name.", Toast.LENGTH_SHORT).show();
                } else {
                    mFinalUrl = mBaseUrl1 + movieName + mBaseUrl2;
                    sendApiRequest();
                }
            }
        });
    }

    public void sendApiRequest() {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mFinalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Request sent", Toast.LENGTH_SHORT).show();

                String rating = null, title = null, releaseDate = null, overview = null, poster = null;
                List<Integer> genreIdList = new ArrayList<>();
                try {
                    String result = response.getString("results");
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    rating = jsonObject.getString("vote_average");
                    title = jsonObject.getString("title");
                    releaseDate = jsonObject.getString("release_date");
                    overview = jsonObject.getString("overview");
                    //poster = jsonObject.getString("poster_path");
                    //Log.d("poster",poster);
                    JSONArray jArray = jsonObject.getJSONArray("genre_ids");

                    for (int i = 0; i < jArray.length(); i++) {
                        genreIdList.add(jArray.getInt(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //sending data to method- setData() to load the data
                setData(rating, title, genreIdList, releaseDate, overview, poster);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void setData(String rating, String title, List<Integer> genreIdList, String releaseDate, String overview, String poster) {
        mTitle.setText(title);
        mRating.setText(rating);
        mReleaseDate.setText(releaseDate);
        mOverview.setText(overview);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < genreIdList.size(); i++) {
            switch (genreIdList.get(i)) {
                case 28:
                    sb.append("Action ");
                    break;
                case 12:
                    sb.append("Adventure ");
                    break;
                case 16:
                    sb.append("Animation ");
                    break;
                case 35:
                    sb.append("Comedy ");
                    break;
                case 80:
                    sb.append("Crime ");
                    break;
                case 99:
                    sb.append("Documentary ");
                    break;
                case 18:
                    sb.append("Drama ");
                    break;
                case 10751:
                    sb.append("Family ");
                    break;
                case 14:
                    sb.append("Fantasy ");
                    break;
                case 36:
                    sb.append("History ");
                    break;
                case 27:
                    sb.append("Horror ");
                    break;
                case 10402:
                    sb.append("Music ");
                    break;
                case 9648:
                    sb.append("Mystery ");
                    break;
                case 10749:
                    sb.append("Romance ");
                    break;
                case 878:
                    sb.append("Science Fiction ");
                    break;
                case 10770:
                    sb.append("TV Movie ");
                    break;
                case 53:
                    sb.append("Thriller ");
                    break;
                case 10752:
                    sb.append("War ");
                    break;
                case 37:
                    sb.append("Western ");
                    break;
                default:
                    break;
            }
            String genre = sb.toString();
            mGenre.setText(genre);
        }
    }
}

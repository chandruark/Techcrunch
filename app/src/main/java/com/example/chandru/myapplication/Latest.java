package com.example.chandru.myapplication;

/**
 * Created by chandru on 8/22/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Latest extends Fragment {
    JSONArray articles = new JSONArray();
    JSONObject article = new JSONObject();
    Activity activity;
    public Latest(){

    }

    public Latest(Activity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.latest, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    public void initViews(View v){
        final RecyclerView recyclerView1 = (RecyclerView) v.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=c3adcc66d869449b88dddcf98d928211";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    articles = response.getJSONArray("articles");
                    NewsAdapter adapter = new NewsAdapter(getActivity(),activity,articles);

                    recyclerView1.setAdapter(adapter);
                    recyclerView1.setItemViewCacheSize(20);
                    recyclerView1.setDrawingCacheEnabled(true);
                    recyclerView1.setDrawingCacheQuality(recyclerView1.DRAWING_CACHE_QUALITY_HIGH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "can't access internet,Please check your internet settings", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

    }

    public void callNewIntent(Intent i){

        startActivity(i);
    }

}



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.latest, container, false);
//
//        return rootView;
//    }


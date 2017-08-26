package com.example.chandru.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;

public class Top extends Fragment {

    JSONArray articles = new JSONArray();
    JSONObject article = new JSONObject();
    Activity activity;
    SwipeRefreshLayout mSwiperefreshlayout;
    RecyclerView recyclerView;
    public Top(){

    }

    public Top(Activity activity, JSONArray articles){
        this.activity = activity;
        Log.i("zxTop",String.valueOf(articles));
        this.articles = articles;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.top, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwiperefreshlayout.setRefreshing(true);
                recyclerView.setVisibility(View.GONE);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=c3adcc66d869449b88dddcf98d928211";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            articles = response.getJSONArray("articles");
                            NewsAdapter adapter = new NewsAdapter(getActivity(),activity,articles);
                            recyclerView.setAdapter(adapter);
                            mSwiperefreshlayout.setRefreshing(false);
                            recyclerView.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Oops something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }
    public void initViews(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter adapter = new NewsAdapter(getActivity(),activity,articles);
        recyclerView.setAdapter(adapter);
        mSwiperefreshlayout = (SwipeRefreshLayout) v.findViewById(R.id.campaignsSwipeRefresh);
    }

    public void callNewIntent(Intent i){
        startActivity(i);
    }

}
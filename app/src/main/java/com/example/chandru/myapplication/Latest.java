package com.example.chandru.myapplication;

/**
 * Created by chandru on 8/22/2017.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
    private ProgressDialog progress1;
    JSONArray articles1 = new JSONArray();
    JSONObject article = new JSONObject();
    SwipeRefreshLayout swipeRefreshLayout1;
    RecyclerView recyclerView1;
    Activity activity;

    public Latest(){

    }

    public Latest(Activity activity,JSONArray articles1){
        this.activity = activity;
        this.articles1=articles1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.latest, container, false);
//        progress1=new ProgressDialog(getContext());
//        String str="fetching ...Data";
//        SpannableString sp=new SpannableString(str);
//        sp.setSpan(new ForegroundColorSpan(Color.BLUE),0,str.length(),0);
//        progress1=ProgressDialog.show(getContext(),"",str);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // progress1.show();
        //  progress1.dismiss();

        initViews(view);
        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout1.setRefreshing(true);
                recyclerView1.setVisibility(View.GONE);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url1 = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=c3adcc66d869449b88dddcf98d928211";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            articles1 = response.getJSONArray("articles");
                            NewsAdapter adapter1 = new NewsAdapter(getActivity(), activity, articles1);
                            recyclerView1.setAdapter(adapter1);
                         swipeRefreshLayout1.setRefreshing(false);
                            recyclerView1.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });
    }




    public void initViews(View v) {
        recyclerView1 = (RecyclerView) v.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter adapter1 = new NewsAdapter(getActivity(), activity, articles1);
        recyclerView1.setAdapter(adapter1);
        swipeRefreshLayout1= (SwipeRefreshLayout) v.findViewById(R.id.campaignsSwipeRefresh1);
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


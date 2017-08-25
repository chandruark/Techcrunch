package com.example.chandru.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.chandru.myapplication.R;
import com.example.chandru.myapplication.Latest;
import com.example.chandru.myapplication.WebViewActivity1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ag on 22-08-2017.
 */

public class NewsAdapter1 extends RecyclerView.Adapter<NewsAdapter1.myNewsViewHolder> {


    JSONArray articles = new JSONArray();
    JSONObject article = new JSONObject();
    Context context;
    Activity activity;
    NewsAdapter1(Context context, Activity activity, JSONArray articles){
        this.articles = articles;
        this.context = context;
        this.activity=activity;
    }

    @Override
    public myNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_view1, parent, false);
        return new myNewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myNewsViewHolder holder, final int position) {
        try {
            article = articles.getJSONObject(position);
            holder.title1.setText(article.getString("title"));
            holder.desc1.setText(article.getString("description"));
            holder.author1.setText(article.getString("author"));
            try {
                String dtc1=article.getString("publishedAt");
                //Toast.makeText(activity, dtc.toString(), Toast.LENGTH_SHORT).show();

                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date1=sdf1.parse(dtc1);
                SimpleDateFormat writeDate1=new SimpleDateFormat("dd,MMM hh:mm a");
                writeDate1.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
                String s1=writeDate1.format(date1);
                holder.time1.setText(s1.toString());
                // System.out.println(""+s);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            holder.time1.setText(article.getString("publishedAt"));

            Picasso.with(context).load(article.getString("urlToImage")).into(holder.coverImage1);
            holder.mLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i1 = new Intent(activity, WebViewActivity1.class);
                    try {
                        Toast.makeText(activity, "process ongoing !...", Toast.LENGTH_SHORT).show();
                        i1.putExtra("url1", articles.getJSONObject(position).getString("url"));
                        context.startActivity(i1);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return articles.length();
    }


    class myNewsViewHolder extends RecyclerView.ViewHolder{
        TextView title1,desc1,author1,time1;
        ImageView coverImage1;
        LinearLayout mLayout1;
        public myNewsViewHolder(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.title1);
            desc1 = (TextView) itemView.findViewById(R.id.desc1);
            author1 = (TextView) itemView.findViewById(R.id.author1);
            time1 = (TextView) itemView.findViewById(R.id.time1);
            coverImage1 = (ImageView) itemView.findViewById(R.id.coverImage1);
            mLayout1 = (LinearLayout) itemView.findViewById(R.id.mLayout1);
        }
    }

}
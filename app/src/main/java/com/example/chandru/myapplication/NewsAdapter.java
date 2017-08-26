package com.example.chandru.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chandru.myapplication.R;
import com.example.chandru.myapplication.Top;
import com.example.chandru.myapplication.WebViewActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ag on 22-08-2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.myNewsViewHolder> {

    JSONArray articles = new JSONArray();
    JSONObject article = new JSONObject();
    Context context;
    Activity activity;
    Top t = new Top();

    public NewsAdapter(Context context, Activity activity, JSONArray articles){
        this.articles = articles;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public myNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_view, parent, false);
        return new myNewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myNewsViewHolder holder, final int position) {
        try {
            article = articles.getJSONObject(position);
            holder.title.setText(article.getString("title"));
            holder.desc.setText(article.getString("description"));
            holder.author.setText(article.getString("author"));

            try {
                String dtc=article.getString("publishedAt");
                //Toast.makeText(activity, dtc.toString(), Toast.LENGTH_SHORT).show();

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date=sdf.parse(dtc);
                SimpleDateFormat writeDate=new SimpleDateFormat("dd,MMM hh:mm a");
                writeDate.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
                String s=writeDate.format(date);
                holder.time.setText(s.toString());
               // System.out.println(""+s);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Picasso.with(context).load(article.getString("urlToImage")).into(holder.coverImage);
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(activity, WebViewActivity.class);
                    try {
                        Toast.makeText(activity, "process ongoing !...", Toast.LENGTH_SHORT).show();
                        i.putExtra("url", articles.getJSONObject(position).getString("url"));
                        context.startActivity(i);
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
        TextView title,desc,author,time;
        ImageView coverImage;
        LinearLayout mLayout;
        public myNewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            author = (TextView) itemView.findViewById(R.id.author);
            time = (TextView) itemView.findViewById(R.id.time);
            coverImage = (ImageView) itemView.findViewById(R.id.coverImage);
            mLayout = (LinearLayout) itemView.findViewById(R.id.mLayout);


        }
    }

}
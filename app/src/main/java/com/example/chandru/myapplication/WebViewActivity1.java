package com.example.chandru.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

public class WebViewActivity1 extends AppCompatActivity {

    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view1);
        Intent i1 = getIntent();
        String ur = i1.getStringExtra("url1");
        Log.i("ZUrl", ur);
        webView1= (WebView) findViewById(R.id.web1);
        webView1.loadUrl(ur);
    }
}
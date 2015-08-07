package com.example.rama.IndiaBuzzNews;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class RssNewsItem extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_news_item);
        WebView browser = (WebView)findViewById(R.id.webview_newsItem);
        String url = getIntent().getStringExtra("url");
        setTitle("");
        browser.getSettings().setJavaScriptEnabled(true);

        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl(url);



    }



}

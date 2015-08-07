package com.example.rama.IndiaBuzzNews;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class ThirdActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        WebView browser = (WebView)findViewById(R.id.webView_news);
        String newspaper = getIntent().getStringExtra("newsPaper");
        browser.getSettings().setJavaScriptEnabled(true);
        if(newspaper.equals("TheHindu"))
            browser.loadUrl("http://www.thehindu.com");
        else
            browser.loadUrl("www.google.com");

    }


}

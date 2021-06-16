package com.example.apcentece;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.apcentece.util.GenericUtils;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);

        if(!GenericUtils.isNullOrEmpty(getIntent().getExtras().getString("articleURL"))){
            webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(getIntent().getExtras().getString("articleURL"));
        }
        else{
            finish();
        }
    }
}

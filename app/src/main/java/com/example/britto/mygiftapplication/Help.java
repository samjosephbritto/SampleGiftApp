package com.example.britto.mygiftapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

/**
 * Created by sambritto on 3/24/2018.
 */

public class Help extends Activity {
    private WebView webView;
    private ImageButton imageButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        imageButton=(ImageButton)findViewById(R.id.help);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,MainActivity.class);
                startActivity(intent);
            }
        });

        webView = (WebView)findViewById(R.id.helpweb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new Help.WebBrowser());
        webView.loadUrl(GiftingUrls.helpurl);
    }

    public class WebBrowser extends WebViewClient {

        private ProgressDialog progressDialog = new ProgressDialog(Help.this);
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if(progressDialog.isShowing()) progressDialog.dismiss();
        }
    }


}

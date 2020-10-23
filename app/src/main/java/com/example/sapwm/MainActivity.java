package com.example.sapwm;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private WebView webView;
    private WebSettings webSettings;
    private ProgressBar mProgressBar;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Button botonlogout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
  /*      try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
    /*    new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                *//* Create an Intent that will start the Menu-Activity. *//*
                Intent mainIntent = new Intent(MainActivity.this, Menu.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/



        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        //mmanejo de cookies

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        cookieManager.setAcceptCookie(true);

        webView = (WebView)findViewById(R.id.webView);
        webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);

        webView.setWebViewClient(new MyWebViewClientGPV());
        webView.loadUrl(getString(R.string.url_sap));
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        webSettings.getCacheMode();
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        //mmanejo de cookies

/*        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        cookieManager.setAcceptCookie(true);

       String coki =  cookieManager.getCookie("SAP_SESSIONID_QS1_200");

               Toast.makeText(this, coki, Toast.LENGTH_SHORT).show();


        android.webkit.CookieManager.getInstance().setCookie(".vhpirqs1ci.hec.grupopiero.com", "SAP_SESSIONID_QS1_200=");*/
        webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new MyWebViewClientGPV());
        webView.loadUrl(getString(R.string.url_sap));
        webSettings.setSupportZoom(true);
              webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(true);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    public void onBackPressed() {

    }
    public class MyWebViewClientGPV extends WebViewClient{
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);


            /*            view.loadData(customHtml, "text/html", "UTF-8");*/
            view.loadUrl("file:///android_asset/error.html");

        }
        public void onProgressChanged(WebView view, int progress) {
            if(progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE){
                mProgressBar.setVisibility(ProgressBar.VISIBLE);

            }

            mProgressBar.setProgress(progress);
            if(progress == 100) {
                mProgressBar.setVisibility(ProgressBar.GONE);

            }
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('content', 'initial-scale=1.0,maximum-scale=10.0');";
            String javascript2="javascript: document.getElementByName('sap-user')[0].value = 'Joh';";
            String javascript3="javascript:document.getElementsByID('sap-user').value = 'augusto';";

            view.loadUrl(javascript);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    }

}

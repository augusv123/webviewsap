package com.example.sapwm;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import static com.example.sapwm.MainActivity.*;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.ConnectionChangeCallback {
    private WebView webView;
    private WebSettings webSettings;
    private ProgressBar mProgressBar;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Button botonlogout;
    private String errorUrl;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

        registerReceiver(networkChangeReceiver, intentFilter);

        networkChangeReceiver.setConnectionChangeCallback(this);

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
 /*     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
   /*       webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new MyWebViewClientGPV());
        webView.loadUrl(getString(R.string.url_sap));
        webSettings.setSupportZoom(true);
              webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(true);

    } */
    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        if(isConnected) {
            // will be called when internet is back
            Toast.makeText(this, "se conecto!", Toast.LENGTH_SHORT).show();
            /*    webView.loadUrl(errorUrl);*/
            Toast.makeText(this, "url: " + errorUrl, Toast.LENGTH_LONG).show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            webView.setVisibility(View.VISIBLE);

        }
        else{
            // will be called when internet is gone.

            Toast.makeText(this, "se desconecto!", Toast.LENGTH_SHORT).show();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            //hide element

            webView.setVisibility(View.GONE);//makes it disappear
        }
    }


    public class MyWebViewClientGPV extends WebViewClient{


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


        @Override

        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

               /*     view.loadUrl("file:///android_asset/error.html");*/
            Toast.makeText(MainActivity.this, request.getUrl().toString() , Toast.LENGTH_LONG).show();
            errorUrl=   request.getUrl().toString();

            Log.e("tag",errorUrl);
            /*            view.loadData(customHtml, "text/html", "UTF-8");*/



        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String s  = "false";
            Log.e("entro","siii entro");

            if (!DetectConnection.checkInternetConnection(MainActivity.this)) {
                s = "true";
                Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_LONG).show();
            } else {
                view.loadUrl(url);
            }
            Log.e("conection",s);

            return true;
        }

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);

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

        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    }


}

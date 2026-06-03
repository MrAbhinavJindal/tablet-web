package com.abhinavjindal.tabletweb;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.view.View;
import android.view.WindowManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private WebView webView;
    private static final String LAPTOP_IP = "192.168.1.45";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        webView = findViewById(R.id.webview);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        
        webView.setWebViewClient(new WebViewClient());
        
        // Auto-detect: try localhost first, then laptop IP
        new Thread(() -> {
            String baseUrl = isReachable("http://localhost:8000") ? 
                "http://localhost:8000" : 
                "http://" + LAPTOP_IP + ":8000";
            
            // Fetch orientation from server
            applyOrientation(baseUrl);
            
            runOnUiThread(() -> webView.loadUrl(baseUrl + "/frontend/main.html"));
        }).start();
    }
    
    private void applyOrientation(String baseUrl) {
        try {
            URL url = new URL(baseUrl + "/orientation");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            conn.disconnect();
            
            JSONObject json = new JSONObject(sb.toString());
            String orientation = json.optString("orientation", "reversePortrait");
            
            runOnUiThread(() -> {
                switch (orientation) {
                    case "portrait":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                    case "reversePortrait":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                        break;
                    case "landscape":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                    case "reverseLandscape":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        break;
                }
            });
        } catch (Exception e) {
            // Default to reversePortrait if server unreachable
            runOnUiThread(() -> setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT));
        }
    }
    
    private boolean isReachable(String urlString) {
        try {
            URL url = new URL(urlString + "/weather");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            connection.disconnect();
            return code >= 200 && code < 400;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

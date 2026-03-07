package com.abhinavjindal.tabletweb;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class MainActivity extends Activity {
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        webView = findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        
        webView.setWebViewClient(new WebViewClient());
        
        // Auto-discover server
        new Thread(() -> {
            String baseUrl = discoverServer();
            if (baseUrl != null) {
                runOnUiThread(() -> {
                    webView.loadUrl(baseUrl + "/frontend/main.html");
                    Toast.makeText(this, "Connected to: " + baseUrl, Toast.LENGTH_SHORT).show();
                });
            } else {
                runOnUiThread(() -> 
                    Toast.makeText(this, "Server not found. Connect via USB or check WiFi.", Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
    
    private String discoverServer() {
        // Try localhost first (wired mode)
        if (isReachable("http://localhost:8000")) {
            return "http://localhost:8000";
        }
        
        // Try to find server on local network
        try {
            String myIP = InetAddress.getLocalHost().getHostAddress();
            String subnet = myIP.substring(0, myIP.lastIndexOf('.'));
            
            // Scan common IP range (last 50 IPs)
            for (int i = 1; i <= 50; i++) {
                String testIP = subnet + "." + i;
                String testUrl = "http://" + testIP + ":8000";
                if (isReachable(testUrl)) {
                    return testUrl;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    private boolean isReachable(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);
            connection.connect();
            int code = connection.getResponseCode();
            connection.disconnect();
            return code == 200;
        } catch (IOException e) {
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

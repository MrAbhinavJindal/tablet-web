package com.abhinavjindal.tabletweb;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.view.WindowManager;
import android.widget.Toast;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;

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
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        
        webView.setWebViewClient(new WebViewClient());
        
        runOnUiThread(() -> Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show());
        
        new Thread(() -> {
            String baseUrl = discoverServer();
            if (baseUrl != null) {
                runOnUiThread(() -> {
                    webView.loadUrl(baseUrl + "/frontend/main.html");
                    Toast.makeText(this, "Connected: " + baseUrl, Toast.LENGTH_SHORT).show();
                });
            } else {
                runOnUiThread(() -> 
                    Toast.makeText(this, "Server not found", Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
    
    private String discoverServer() {
        // Try localhost
        if (isReachable("http://localhost:8000")) {
            return "http://localhost:8000";
        }
        
        // Try mDNS discovery
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_http._tcp.local.", "dashboard._http._tcp.local.", 3000);
            if (serviceInfo != null) {
                String host = serviceInfo.getHostAddresses()[0];
                int port = serviceInfo.getPort();
                jmdns.close();
                return "http://" + host + ":" + port;
            }
            jmdns.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    private boolean isReachable(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
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

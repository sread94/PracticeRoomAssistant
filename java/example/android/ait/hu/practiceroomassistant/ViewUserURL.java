package example.android.ait.hu.practiceroomassistant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Sarah Read on 12/4/2014.
 */
public class ViewUserURL extends Activity {

    //key to get the url of the page
    public static final String KEY_USER_URL = "this is the users url";

    //instance of a webView
    private WebView webView;

    //instance of a progress bar
    private ProgressBar progressBarWebLoad;

    /**
     * set the initial values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up layout
        setContentView(R.layout.webview_simple);

        //get the url user linked to
        String theURL = getIntent().getStringExtra(KEY_USER_URL);

        //set the progress bar
        progressBarWebLoad = (ProgressBar) findViewById(R.id.progressBarWebLoad);
        progressBarWebLoad.setMax(100);

        //set up the web view
        webView = (WebView) findViewById(R.id.webViewContent);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadSite(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarWebLoad.setProgress(100);
                progressBarWebLoad.setVisibility(View.GONE);
            }
        });

        webView.getSettings().setBuiltInZoomControls(true);

        //set the url of the webView
        loadSite(theURL);
    }

    /**
     * Set the url of the webView
     * @param url
     */
    private void loadSite(String url) {
        progressBarWebLoad.setVisibility(View.VISIBLE);
        progressBarWebLoad.setProgress(0);
        webView.loadUrl(url);
    }

    /**
     * define what happens when the back button is pressed
     */
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}

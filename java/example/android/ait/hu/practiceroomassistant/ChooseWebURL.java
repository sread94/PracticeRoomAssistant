package example.android.ait.hu.practiceroomassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by Sarah Read on 12/4/2014.
 *
 * Activity where the user selects the web page to bookmark
 */
public class ChooseWebURL extends Activity {

    //key to return the link
    public static final String KEY_URL = "this is the url";

    //reference to the WebView
    private WebView webView;

    //reference to the ProgressBar
    private ProgressBar progressBarWebLoad;

    /**
     * Override onCreate
     *
     * sets up UI and button actions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        final Intent i = getIntent();

        progressBarWebLoad = (ProgressBar) findViewById(R.id.progressBarWebLoad);
        progressBarWebLoad.setMax(100);

        //set up the WebView
        webView = (WebView) findViewById(R.id.wvWeb);
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

        //load default page
        loadSite("https://www.youtube.com");

        //setup cancel button
        //on cancel return to the view pager (song details)
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(KEY_URL, "");
                setResult(WebFragment.REQUEST_CODE_URL, i);
                finish();
            }
        });

        //setup submit button
        //on submit return the url of the current web page
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curURL = webView.getUrl();
                i.putExtra(KEY_URL, curURL);
                setResult(WebFragment.REQUEST_CODE_URL, i);
                finish();
            }
        });
    }

    /**
     * loads the given url
     * @param url
     */
    private void loadSite(String url) {
        progressBarWebLoad.setVisibility(View.VISIBLE);
        progressBarWebLoad.setProgress(0);
        webView.loadUrl(url);
    }

    /**
     * handles back action in web browser
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

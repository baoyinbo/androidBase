package com.gxb.gxbcompanyintegrity.ui.common;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gxb.gxbcompanyintegrity.R;
import com.gxb.gxbcompanyintegrity.ui.base.BaseActivityFragment;


/**
 * Created by baoyb on 2017/3/30.
 */

public class GSWebViewFragment extends BaseActivityFragment {
    private WebView webView;
    private GSDefaultLoadingView loadingView;
    private String url;
    @Override
    public int getLayoutId() {
        return R.layout.gs_fra_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        loadingView = (GSDefaultLoadingView) findViewById(R.id.loadingView);
        webView = (WebView) findViewById(R.id.webview);
        confing();
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
        webView.loadUrl(url);
        getCurrentPage().setSwipeEdgePercent(0.05f);
    }


    /**
     * web的一些配置
     */
    public void confing() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.getSettings().setAllowContentAccess(true);
        }
//        if (WJAppConfing.IS_DEBUG) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                WebView.setWebContentsDebuggingEnabled(true);
//            }
//        }
        // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setSupportMultipleWindows(false);
//
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        //强制调整字体大小，单行显示，不会因为系统字体大小设置改变页面布局
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingView.loading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingView.content();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                webView.requestFocus();
                return false;
            }
        });
//        mWebView.addJavascriptInterface(new HtmlObject(), "jsObj");
    }
}

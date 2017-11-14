package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

class web : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val intent = intent
        val bd = intent.extras
        var add: String? = null
        if (bd != null) {
            add = bd.get("web_add") as String
        }


        val browser = findViewById<WebView>(R.id.webView) as WebView
        browser.settings.javaScriptEnabled = true
        browser.webViewClient = MyBrowser()
        browser.settings.loadWithOverviewMode = true
        browser.settings.builtInZoomControls = true
        browser.settings.setSupportMultipleWindows(true)
        browser.settings.useWideViewPort = true
        browser.isHorizontalScrollBarEnabled = true
        browser.loadUrl("http://www.google.com")
    }

    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }


    }
}

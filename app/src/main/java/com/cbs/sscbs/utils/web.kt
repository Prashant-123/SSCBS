package com.cbs.sscbs.utils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import com.cbs.sscbs.R

class web : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val browser = findViewById<WebView>(R.id.webView) as WebView
        browser.settings.javaScriptEnabled = true
        browser.webViewClient = MyBrowser()
        browser.settings.loadWithOverviewMode = true
        browser.settings.builtInZoomControls = true
        browser.settings.setSupportMultipleWindows(true)
        browser.settings.useWideViewPort = true
        browser.isHorizontalScrollBarEnabled = true
        browser.loadUrl("http://sscbs.bestbookbuddies.com/cgi-bin/koha/question-papers.pl")
    }


    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }


    }
}

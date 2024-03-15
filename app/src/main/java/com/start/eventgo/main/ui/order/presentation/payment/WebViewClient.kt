package com.start.eventgo.main.ui.order.presentation.payment

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView

class WebViewClient(
    private val context: Context,
    private val listener: Listener,
) : android.webkit.WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
        val newUrl = url ?: return false
        return listener.onUrlOverride(view, newUrl)
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val newUrl = request.url.toString()
        return listener.onUrlOverride(view, newUrl)
    }

    override fun onPageFinished(view: WebView, url: String) {
        listener.onPageFinished(view, url)
    }

    interface Listener {
        fun onUrlOverride(webView: WebView, url: String): Boolean
        fun onPageFinished(view: WebView, url: String)
    }
}

package com.start.eventgo.main.ui.order.presentation.payment

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.start.eventgo.R
import com.start.eventgo.databinding.ActivityPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentActivity : AppCompatActivity(), WebViewClient.Listener {

    private val binding: ActivityPaymentBinding by viewBinding()
    private val viewModel: PaymentViewModel by viewModel()

    private val webViewClient: WebViewClient by lazy { WebViewClient(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        bindWeb()
    }

    private fun bindWeb() {
        with(binding.webView) {
            with(settings) {
                loadWithOverviewMode = true
                useWideViewPort = true
                javaScriptEnabled = true
                loadsImagesAutomatically = true
            }
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webViewClient = this.webViewClient
            loadUrl(viewModel.url)
        }
    }

    override fun onPageFinished(view: WebView, url: String) {
//        viewModel.handlePageFinished(url)
    }

    override fun onUrlOverride(webView: WebView, url: String): Boolean {
//        viewModel.handleUrls(this, webView, url)
        return false
    }
}

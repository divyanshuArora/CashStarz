package com.developers.CrbClub

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.developers.a24mpower.activities.activity.utils.MyProgressDialog
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    var url = ""
    var mDialog: MyProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()
    }

    private fun init() {
        url = "https://www.cashstarz.com/trade/andriod"
        mDialog = MyProgressDialog(this)
        loadWebView()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        mDialog!!.show()
        webView.webViewClient = LensesWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(url)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mDialog!!.dismiss()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val startsWith = url?.take(5)
                Log.d("DashboardAct","startWith===> $startsWith")
                if (url?.startsWith("tel:")!! || url?.startsWith("whatsapp:")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    webView.goBack()
                    return true
                }
                else if (url?.startsWith("tg:ms")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    webView.goBack()
                    return true
                }
                else if (url?.startsWith("facebook:")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    webView.goBack()
                    return true
                }
                else if (url?.startsWith("mailto:")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    webView.goBack()
                    return true
                }
                return false

            }

        }
    }

    private inner class LensesWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (event!!.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }

        }
        return super.onKeyDown(keyCode, event)
        //  return super.onKeyDown(keyCode, event)
    }
}

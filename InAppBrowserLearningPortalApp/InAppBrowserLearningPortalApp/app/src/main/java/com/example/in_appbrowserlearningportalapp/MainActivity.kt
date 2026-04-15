

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var etAddressBar: EditText
    private lateinit var progressBar: ProgressBar
    private val homeUrl = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        webView = findViewById(R.id.webView)
        etAddressBar = findViewById(R.id.etAddressBar)
        progressBar = findViewById(R.id.progressBar)

        setupWebView()
        setupNavigation()
        setupShortcuts()

        // Load Default Page
        webView.loadUrl(homeUrl)
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                etAddressBar.setText(url) // Update address bar with the current URL
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                // Load local offline page from assets folder on error
                webView.loadUrl("file:///android_asset/offline.html")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // Update the horizontal progress bar
                progressBar.progress = newProgress
            }
        }
    }

    private fun setupNavigation() {
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnForward).setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            webView.reload()
        }

        findViewById<Button>(R.id.btnHome).setOnClickListener {
            webView.loadUrl(homeUrl)
        }

        findViewById<Button>(R.id.btnGo).setOnClickListener {
            loadFromAddressBar()
        }

        // Handle 'Enter' key on the soft keyboard
        etAddressBar.setOnEditorActionListener { _, _, _ ->
            loadFromAddressBar()
            true
        }
    }

    private fun loadFromAddressBar() {
        var url = etAddressBar.text.toString().trim()
        if (url.isNotEmpty()) {
            // Helper to ensure URL starts with protocol
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }
            webView.loadUrl(url)
        }
    }

    private fun setupShortcuts() {
        findViewById<Button>(R.id.btnGoogle).setOnClickListener { webView.loadUrl("https://www.google.com") }
        findViewById<Button>(R.id.btnYoutube).setOnClickListener { webView.loadUrl("https://www.youtube.com") }
        findViewById<Button>(R.id.btnWiki).setOnClickListener { webView.loadUrl("https://www.wikipedia.org") }
        findViewById<Button>(R.id.btnKhan).setOnClickListener { webView.loadUrl("https://www.khanacademy.org") }
    }

    // Handles physical back button to navigate web history
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
package com.mmfsin.grisaceo.presentation.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.mmfsin.grisaceo.R
import com.mmfsin.grisaceo.base.BaseFragment
import com.mmfsin.grisaceo.databinding.FragmentWebviewBinding
import com.mmfsin.grisaceo.domain.models.Navigation
import com.mmfsin.grisaceo.domain.models.Navigation.*
import com.mmfsin.grisaceo.utils.showErrorDialog
import com.mmfsin.grisaceo.utils.showNetworkErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebviewBinding, WebViewViewModel>() {

    override val viewModel: WebViewViewModel by viewModels()

    private lateinit var mContext: Context
    private var networkErrorShowed = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentWebviewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUrls()
//        setWebView(url = R.string.url_main)
    }

    override fun setUI() {
        binding.apply {
            loading.root.visibility = View.VISIBLE
//            selectNavItem(HOME)
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is WebViewEvent.Urls -> {
                    Toast.makeText(mContext, "ey", Toast.LENGTH_SHORT).show()
                }

                is WebViewEvent.SWW -> error()
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> setButtonClick(R.string.url_main)
                    R.id.nav_tshirt -> setButtonClick(R.string.url_tshirts)
                    R.id.nav_complements -> setButtonClick(R.string.url_complements)
                    R.id.nav_designs -> setButtonClick(R.string.url_designs)
                }
                true
            }

            activity?.let {
                it.onBackPressedDispatcher.addCallback(it, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (webview.canGoBack()) webview.goBack()
                        else it.finish()
                    }
                })
            }
        }
    }

    private fun setButtonClick(url: Int) = setWebView(url = url)

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: Int? = null, urlStr: String? = null) {
        binding.apply {
            loading.root.visibility = View.VISIBLE
            webview.apply {
                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        loading.root.visibility = View.VISIBLE
                    }

                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                        url?.let {
                            when (url) {
                                getString(R.string.url_main) -> selectNavItem(HOME)
                                getString(R.string.url_tshirts) -> selectNavItem(TSHIRTS)
                                getString(R.string.url_complements) -> selectNavItem(COMPLEMENTS)
                                getString(R.string.url_designs) -> selectNavItem(DESIGNS)
                            }
                            Log.i("URL", "Url loaded -> $url")
                            binding.loading.root.visibility = View.GONE
                        }
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        /** net::ERR_INTERNET_DISCONNECTED */
                        error?.let {
                            if (it.errorCode == -2 && !networkErrorShowed) {
                                networkErrorShowed = true
                                activity?.showNetworkErrorDialog {
                                    request?.let { rq ->
                                        networkErrorShowed = false
                                        setWebView(urlStr = rq.url.toString())
                                    }
                                }
                            }
                        }
                    }
                }
                settings.javaScriptEnabled = true
                url?.let { loadUrl(getString(it)) }
                urlStr?.let { loadUrl(it) }
            }
        }
    }

    private fun selectNavItem(navigation: Navigation) {
        val item = when (navigation) {
            HOME -> R.id.nav_home
            TSHIRTS -> R.id.nav_tshirt
            COMPLEMENTS -> R.id.nav_complements
            DESIGNS -> R.id.nav_designs
        }
        binding.bottomNav.menu.findItem(item).isChecked = true
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
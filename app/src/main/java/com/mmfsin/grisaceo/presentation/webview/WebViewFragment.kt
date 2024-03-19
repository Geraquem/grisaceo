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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.mmfsin.grisaceo.R
import com.mmfsin.grisaceo.base.BaseFragment
import com.mmfsin.grisaceo.databinding.FragmentWebviewBinding
import com.mmfsin.grisaceo.domain.models.Navigation
import com.mmfsin.grisaceo.domain.models.Navigation.*
import com.mmfsin.grisaceo.domain.models.Urls
import com.mmfsin.grisaceo.utils.showErrorDialog
import com.mmfsin.grisaceo.utils.showNetworkErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebviewBinding, WebViewViewModel>() {

    override val viewModel: WebViewViewModel by viewModels()

    private lateinit var mContext: Context
    private var urls: Urls? = null
    private var networkErrorShowed = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentWebviewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUrls()
    }

    override fun setUI() {
        binding.apply {
            loading.root.visibility = View.VISIBLE
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is WebViewEvent.GetUrls -> {
                    urls = event.urls
                    setWebView(url = event.urls.urlMain)
                    selectNavItem(HOME)
                    binding.loading.root.visibility = View.GONE
                }

                is WebViewEvent.SWW -> error()
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            bottomNav.setOnItemSelectedListener { item ->
                urls?.let { url ->
                    when (item.itemId) {
                        R.id.nav_home -> setButtonClick(url.urlMain)
                        R.id.nav_tshirt -> setButtonClick(url.urlTshirts)
                        R.id.nav_complements -> setButtonClick(url.urlComplements)
                        R.id.nav_designs -> setButtonClick(url.urlDesigns)
                    }
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

    private fun setButtonClick(url: String) = setWebView(url = url)

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: String) {
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
                                urls?.urlMain -> selectNavItem(HOME)
                                urls?.urlTshirts -> selectNavItem(TSHIRTS)
                                urls?.urlComplements -> selectNavItem(COMPLEMENTS)
                                urls?.urlDesigns -> selectNavItem(DESIGNS)
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
                                        setWebView(rq.url.toString())
                                    }
                                }
                            }
                        }
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
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
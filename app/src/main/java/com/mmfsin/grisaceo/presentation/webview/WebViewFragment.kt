package com.mmfsin.grisaceo.presentation.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.mmfsin.grisaceo.R
import com.mmfsin.grisaceo.base.BaseFragmentNoVM
import com.mmfsin.grisaceo.databinding.FragmentWebviewBinding
import com.mmfsin.grisaceo.domain.models.Navigation
import com.mmfsin.grisaceo.domain.models.Navigation.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragmentNoVM<FragmentWebviewBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentWebviewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebView(R.string.url_main)
    }

    override fun setUI() {
        binding.apply {
            loading.root.visibility = View.VISIBLE
            selectNavItem(HOME)
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

    private fun setButtonClick(url: Int) = setWebView(url)

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: Int) {
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
                }
                settings.javaScriptEnabled = true
                loadUrl(getString(url))
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
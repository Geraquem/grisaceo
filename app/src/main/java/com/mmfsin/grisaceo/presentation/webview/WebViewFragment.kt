package com.mmfsin.grisaceo.presentation.webview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.mmfsin.grisaceo.databinding.ItemButtonBinding
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
            handleNavButtons(HOME)
            btnHome.apply {
                ivIcon.tag = HOME
                ivIcon.setImageResource(R.drawable.ic_error)
                tvTitle.text = getString(R.string.btn_home)
            }
            btnTshirts.apply {
                ivIcon.tag = TSHIRTS
                ivIcon.setImageResource(R.drawable.ic_error)
                tvTitle.text = getString(R.string.btn_tshirts)
            }
            btnDesigns.apply {
                ivIcon.tag = DESIGNS
                ivIcon.setImageResource(R.drawable.ic_error)
                tvTitle.text = getText(R.string.btn_designs)
            }
        }
    }

    override fun setListeners() {
        binding.apply {

            btnHome.root.setOnClickListener {
                handleNavButtons(HOME)
                setWebView(R.string.url_main)
            }

            btnTshirts.root.setOnClickListener {
                handleNavButtons(TSHIRTS)
                setWebView(R.string.url_tshirts)
            }

            btnDesigns.root.setOnClickListener {
                handleNavButtons(DESIGNS)
                setWebView(R.string.url_designs)
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: Int) {
        binding.webview.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    url?.let {
                        when (url) {
                            getString(R.string.url_main) -> handleNavButtons(HOME)
                            getString(R.string.url_tshirts) -> handleNavButtons(TSHIRTS)
                            getString(R.string.url_designs) -> handleNavButtons(DESIGNS)
                        }
                        Log.i("URL", "Url loaded -> $url")
                    }
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(getString(url))
        }
    }

    private fun handleNavButtons(item: Navigation) {
        binding.apply {
            when (item) {
                HOME -> {
                    setOnButton(btnHome)
                    setOffButton(listOf(btnTshirts, btnDesigns))
                }

                TSHIRTS -> {
                    setOnButton(btnTshirts)
                    setOffButton(listOf(btnHome, btnDesigns))
                }

                DESIGNS -> {
                    setOnButton(btnDesigns)
                    setOffButton(listOf(btnHome, btnTshirts))
                }
            }
        }
    }

    private fun setOnButton(button: ItemButtonBinding) {
        button.ivIcon.setColorFilter(getColor(mContext, R.color.bg_web))

    }

    private fun setOffButton(buttons: List<ItemButtonBinding>) {
        buttons.forEach { button ->
            button.ivIcon.setColorFilter(getColor(mContext, R.color.white))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
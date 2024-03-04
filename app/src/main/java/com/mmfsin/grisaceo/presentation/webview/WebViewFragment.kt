package com.mmfsin.grisaceo.presentation.webview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.mmfsin.grisaceo.base.BaseFragmentNoVM
import com.mmfsin.grisaceo.databinding.FragmentWebviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragmentNoVM<FragmentWebviewBinding>() {

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentWebviewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebView()
    }

    override fun setUI() {
        binding.apply {}
    }

    override fun setListeners() {
        binding.apply {
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
    private fun setWebView() {
        binding.webview.apply {
            webViewClient = object : WebViewClient() {}
            settings.javaScriptEnabled = true
            loadUrl("https://grisaceo.com")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
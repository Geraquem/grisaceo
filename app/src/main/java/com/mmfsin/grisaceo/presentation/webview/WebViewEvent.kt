package com.mmfsin.grisaceo.presentation.webview

import com.mmfsin.grisaceo.domain.models.Urls

sealed class WebViewEvent {
    class GetUrls(val urls: Urls) : WebViewEvent()
    object SWW : WebViewEvent()
}
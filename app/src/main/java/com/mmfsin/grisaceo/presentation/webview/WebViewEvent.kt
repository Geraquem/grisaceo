package com.mmfsin.grisaceo.presentation.webview

sealed class WebViewEvent {
    class Urls(val urls: String) : WebViewEvent()
    object SWW : WebViewEvent()
}
package com.mmfsin.grisaceo.presentation.webview

import com.mmfsin.grisaceo.base.BaseViewModel
import com.mmfsin.grisaceo.domain.usecases.GetUrlsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val getUrlsUseCase: GetUrlsUseCase
) : BaseViewModel<WebViewEvent>() {

    fun getUrls() {
        executeUseCase(
            { getUrlsUseCase.execute() },
            { result ->
                _event.value =
                    result?.let { WebViewEvent.GetUrls(result) } ?: run { WebViewEvent.SWW }
            },
            { _event.value = WebViewEvent.SWW }
        )
    }
}
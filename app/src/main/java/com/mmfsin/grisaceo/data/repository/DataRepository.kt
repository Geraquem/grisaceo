package com.mmfsin.grisaceo.data.repository

import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.mmfsin.grisaceo.domain.interfaces.IDataRepository
import com.mmfsin.grisaceo.domain.models.Gist
import com.mmfsin.grisaceo.domain.models.Urls
import com.mmfsin.grisaceo.utils.GIST_ID
import com.mmfsin.grisaceo.utils.GIST_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class DataRepository @Inject constructor() : IDataRepository {

    override suspend fun getUrls(): Urls? {
        var urls: Urls? = null
        val latch = CountDownLatch(1)
        val gistUrl = GIST_URL + GIST_ID
        gistUrl.httpGet().responseString { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val gist = Gson().fromJson(result.get(), Gist::class.java)
                    urls = parseUrls(gist.files.urls.content)
                    Log.i("->", "Gist url ${gist.url}")
                }

                is Result.Failure -> Log.e("ERROR", "Error getting Gist")
            }
            latch.countDown()
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        return urls
    }

    private fun parseUrls(urls: String): Urls? = Gson().fromJson(urls, Urls::class.java)
}

package com.mmfsin.grisaceo.domain.usecases

import android.content.Context
import com.mmfsin.grisaceo.R
import com.mmfsin.grisaceo.base.BaseUseCaseNoParams
import com.mmfsin.grisaceo.domain.interfaces.IDataRepository
import com.mmfsin.grisaceo.domain.models.Urls
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetUrlsUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IDataRepository
) :
    BaseUseCaseNoParams<Urls>() {

    override suspend fun execute(): Urls {
        val urls = repository.getUrls()
        urls?.let { return it } ?: run {
            return Urls(
                urlMain = context.getString(R.string.url_main),
                urlTshirts = context.getString(R.string.url_tshirts),
                urlComplements = context.getString(R.string.url_complements),
                urlDesigns = context.getString(R.string.url_designs)
            )
        }
    }
}
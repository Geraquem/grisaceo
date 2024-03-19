package com.mmfsin.grisaceo.domain.usecases

import com.mmfsin.grisaceo.base.BaseUseCaseNoParams
import com.mmfsin.grisaceo.domain.interfaces.IDataRepository
import com.mmfsin.grisaceo.domain.models.Urls
import javax.inject.Inject

class GetUrlsUseCase @Inject constructor(private val repository: IDataRepository) :
    BaseUseCaseNoParams<Urls?>() {

    override suspend fun execute(): Urls? = repository.getUrls()
}
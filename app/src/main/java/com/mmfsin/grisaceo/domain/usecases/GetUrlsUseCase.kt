package com.mmfsin.grisaceo.domain.usecases

import com.mmfsin.grisaceo.base.BaseUseCaseNoParams
import com.mmfsin.grisaceo.domain.interfaces.IDataRepository
import javax.inject.Inject

class GetUrlsUseCase @Inject constructor(private val repository: IDataRepository) :
    BaseUseCaseNoParams<Unit>() {

    override suspend fun execute() {
    }
}
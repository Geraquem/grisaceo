package com.mmfsin.grisaceo.domain.interfaces

import com.mmfsin.grisaceo.domain.models.Urls

interface IDataRepository {
    suspend fun getUrls(): Urls?
}
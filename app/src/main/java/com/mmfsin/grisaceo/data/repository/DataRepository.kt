package com.mmfsin.grisaceo.data.repository

import android.content.Context
import com.mmfsin.grisaceo.domain.interfaces.IDataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    @ApplicationContext val context: Context,
) : IDataRepository {

    override suspend fun getUrls() {

    }
}

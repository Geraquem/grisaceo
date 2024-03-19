package com.mmfsin.grisaceo.di

import com.mmfsin.grisaceo.data.repository.DataRepository
import com.mmfsin.grisaceo.domain.interfaces.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataRepositoryModule {
    @Binds
    fun bind(repository: DataRepository): IDataRepository
}
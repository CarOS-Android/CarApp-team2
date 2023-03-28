package com.thoughtworks.carapp.di

import com.thoughtworks.carapp.data.DefaultSampleDataSource
import com.thoughtworks.carapp.data.SampleDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface DataSourceModule {
    @Binds
    @ViewModelScoped
    fun bindSampleDataSource(sampleDataSource: DefaultSampleDataSource): SampleDataSource
}

package com.thoughtworks.carapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultSampleDataSource @Inject constructor() : SampleDataSource {
    override fun getSampleData(): Flow<String> = flow {
        emit("Hello world")
    }
}
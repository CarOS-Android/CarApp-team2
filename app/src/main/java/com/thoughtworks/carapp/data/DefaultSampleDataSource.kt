package com.thoughtworks.carapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DefaultSampleDataSource @Inject constructor() : SampleDataSource {
    override fun getSampleData(): Flow<String> = flow {
        delay(3000)
        emit("Too much words......much words...words.....")
    }.flowOn(Dispatchers.IO)

    override fun thumpUp(isLiked: Boolean): Flow<Boolean> = flow {
        delay(500)
        emit(!isLiked)
    }.flowOn(Dispatchers.IO)
}
package com.thoughtworks.carapp.data

import kotlinx.coroutines.flow.Flow

interface SampleDataSource {
    fun getSampleData(): Flow<String>

    fun thumpUp(isLiked: Boolean): Flow<Boolean>
}

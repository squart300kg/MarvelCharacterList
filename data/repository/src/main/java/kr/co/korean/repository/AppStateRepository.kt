package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {

    val appFirstStatedState: Flow<Boolean>

    suspend fun startApp()
}
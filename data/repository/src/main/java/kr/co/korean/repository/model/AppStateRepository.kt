package kr.co.korean.repository.model

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {

    val appFirstStatedState: Flow<Boolean>
}
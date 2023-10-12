package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.korean.datastore.AppStateDataStore
import javax.inject.Inject

class AppStateRepositoryImpl @Inject constructor(
    private val appStateDataStore: AppStateDataStore
): AppStateRepository {

    override val appFirstStatedState: Flow<Boolean> =
        appStateDataStore.appState.map { it.appFirstStated }

    override suspend fun startApp() {
        appStateDataStore.startApp()
    }
}
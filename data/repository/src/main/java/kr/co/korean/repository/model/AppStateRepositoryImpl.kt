package kr.co.korean.repository.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.korean.datastore.AppStateDataStore
import javax.inject.Inject

class AppStateRepositoryImpl @Inject constructor(
    private val appStateDataStore: AppStateDataStore
): AppStateRepository {

    override val appFirstStatedState: Flow<Boolean> =
        flow {
//            appStateDataStore.setFirstStartedState()
            emit(false)
        }
}
package kr.co.korean.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class AppStateDataStore @Inject constructor(
    private val preference: DataStore<AppState>
) {

    val appState: Flow<AppState> =
        preference.data

    suspend fun startApp() {
        try {
            preference.updateData { appState ->
                appState.toBuilder()
                    .setAppFirstStated(true)
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("appStateProtoDataStore", "Failed to update marvel data to local", ioException)
        }
    }
}
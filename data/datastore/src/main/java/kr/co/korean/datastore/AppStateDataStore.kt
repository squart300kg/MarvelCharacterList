package kr.co.korean.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import javax.inject.Inject

class AppStateDataStore @Inject constructor(
    private val preference: DataStore<AppState>
) {

    suspend fun setFirstStartedState(id: Int) {
        try {
            preference.updateData {
                it.toBuilder()
//                    .apply {
//                        if (idsList.contains(id)) {
//                            idsList
//                                .apply { remove(id) }
//                                .forEachIndexed { index, id ->
//                                    setIds(index, id)
//                                }
//                        } else { addIds(id) }
//                    }
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("marvelProtoDataStore", "Failed to update marvel data to local", ioException)
        }
    }
}
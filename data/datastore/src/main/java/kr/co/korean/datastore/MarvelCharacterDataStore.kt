package kr.co.korean.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import javax.inject.Inject

class MarvelCharacterDataStore @Inject constructor(
    private val preference: DataStore<MarvelCharacters>
) {
    val savedCharacters = preference.data

    suspend fun setCharacterSaved(id: MarvelCharacters) {
        try {
            preference.updateData {
                it.toBuilder()
                    .apply {
                        this.defaultInstanceForType.newBuilderForType().apply {
                            this.charactersList.
                        }.build()
                        val searchedCharacter = charactersList.contains(id)
                        if (searchedCharacter != null) {
                            val searchedIndex = charactersList.indexOf(searchedCharacter)
                            removeCharacters(searchedIndex)
                        } else {
                            addCharacters()
                        }
                    }
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("marvelProtoDataStore", "Failed to update marvel data to local", ioException)
        }
    }
}
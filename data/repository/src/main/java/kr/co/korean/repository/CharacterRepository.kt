package kr.co.korean.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.CharacterDataModel

interface CharacterRepository {

    val localCharacters: Flow<List<CharacterDataModel>>

    val remoteCharacters: Flow<PagingData<CharacterDataModel>>

    suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel, saved: Boolean)

    fun downloadThumbnail(url: String)
}
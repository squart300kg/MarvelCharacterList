package kr.co.korean.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.korean.database.entity.MarvelCharacter
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.repository.model.SavedIdsDataModel

interface CharacterRepository {

    val localCharacters: Flow<List<CharacterDataModel>>

    val remoteCharacters: Flow<PagingData<CharacterDataModel>>

    suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel, saved: Boolean)

}
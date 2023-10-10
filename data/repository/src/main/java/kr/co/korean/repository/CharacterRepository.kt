package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.repository.model.SavedIdsDataModel

interface CharacterRepository {

    val localCharacters: Flow<SavedIdsDataModel>

    val remoteCharacters: Flow<List<CharacterDataModel>>

    suspend fun modifyCharacterSavedStatus(id: Int)

}
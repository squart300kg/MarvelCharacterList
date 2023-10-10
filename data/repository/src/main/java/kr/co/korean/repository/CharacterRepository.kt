package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kr.co.korean.datastore.MarvelCharacter
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.repository.model.SavedIdsDataModel

interface CharacterRepository {


    val savedCharacters: Flow<SavedIdsDataModel>

    suspend fun setCharacterSaved(id: Int)

    fun getCharacters(): Flow<List<CharacterDataModel>>

}
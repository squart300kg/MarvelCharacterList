package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.CharacterDataModel

interface CharacterRepository {


    val savedCharacters: Flow<String>

    fun saveCharacter(id: String)

    fun getCharacters(): Flow<List<CharacterDataModel>>

}
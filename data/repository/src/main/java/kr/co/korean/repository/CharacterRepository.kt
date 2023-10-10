package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.CharacterDataModel

interface CharacterRepository {

    fun getCharacters(): Flow<List<CharacterDataModel>>

}
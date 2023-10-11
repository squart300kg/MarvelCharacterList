package kr.co.korean.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import javax.inject.Inject

class GetSyncedCharacters @Inject constructor(
    private val characterRepository: CharacterRepository
){

    operator fun invoke(): Flow<PagingData<CharacterDataModel>> =
        combine(
            characterRepository.remoteCharacters,
            characterRepository.localCharacters
        ) { remoteCharacters, localCharacters ->
            remoteCharacters
        }

}
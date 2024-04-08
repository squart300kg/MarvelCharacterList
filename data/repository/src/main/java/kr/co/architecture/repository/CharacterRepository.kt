package kr.co.architecture.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.architecture.repository.model.CharacterDataModel
import kr.co.architecture.repository.model.CharacterDetailDataModel
import kr.co.architecture.work.ImageDownLoadResult

interface CharacterRepository {

    val localCharacters: Flow<List<CharacterDataModel>>

    val remoteCharacters: Flow<PagingData<CharacterDataModel>>

    fun getRemoteSingleCharacter(id: Int): Flow<List<CharacterDetailDataModel>>

    suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel, saved: Boolean)

    val imageDownloadState: Flow<ImageDownLoadResult>

    fun downloadThumbnail(url: String)
}
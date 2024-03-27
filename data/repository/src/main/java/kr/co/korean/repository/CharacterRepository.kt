package kr.co.korean.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.work.ImageDownLoadResult

interface CharacterRepository {

    val localCharacters: Flow<List<CharacterDataModel>>

    val remoteCharacters: Flow<PagingData<CharacterDataModel>>

    suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel, saved: Boolean)

    val imageDownloadState: Flow<ImageDownLoadResult>

    fun downloadThumbnail(url: String)
}
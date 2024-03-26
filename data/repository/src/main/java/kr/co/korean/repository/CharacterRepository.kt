package kr.co.korean.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.work.ImageDownLoadResult

enum class ContentsType {
    Comics,
    Series,
    Stories,
    Events,
}

interface CharacterRepository {

    val localCharacters: Flow<List<CharacterDataModel>>

    val remoteCharacters: Flow<PagingData<CharacterDataModel>>

    suspend fun getRemoteSingleContent(id: Int, type: ContentsType): Flow<List<CharacterDataModel>>

    suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel, saved: Boolean)

    val imageDownloadState: Flow<ImageDownLoadResult>

    fun downloadThumbnail(url: String)
}
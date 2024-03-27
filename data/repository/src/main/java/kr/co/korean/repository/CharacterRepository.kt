package kr.co.korean.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.korean.repository.model.ContentsDataModel
import kr.co.korean.work.ImageDownLoadResult

enum class ContentsType {
    Characters,
    Comics,
    Series,
    Stories,
    Events,
}

interface CharacterRepository {

    val localCharacters: Flow<List<ContentsDataModel.CharacterDataModel>>

    val remoteCharacters: Flow<PagingData<ContentsDataModel.CharacterDataModel>>

    suspend fun getRemoteSingleContent(id: Int, type: ContentsType): Flow<List<ContentsDataModel>>

    suspend fun modifyCharacterSavedStatus(dataModel: ContentsDataModel.CharacterDataModel, saved: Boolean)

    val imageDownloadState: Flow<ImageDownLoadResult>

    fun downloadThumbnail(url: String)
}
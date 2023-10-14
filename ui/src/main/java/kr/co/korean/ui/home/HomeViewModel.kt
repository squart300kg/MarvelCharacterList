package kr.co.korean.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.work.WorkInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.common.model.Result
import kr.co.korean.domain.ModifyCharacterSavedStatusUseCase
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.ui.model.CharactersUiModel
import kr.co.korean.ui.model.convertDataModel
import kr.co.korean.ui.model.convertUiModel
import kr.co.korean.work.ImageDownLoadResult
import javax.inject.Inject

fun syncAndConvertUiModel(
    remoteModel: CharacterDataModel,
    localModels: List<CharacterDataModel>
): CharactersUiModel =
    CharactersUiModel(
        id = remoteModel.id,
        thumbnail = remoteModel.thumbnail,
        urlCount = remoteModel.urlCount,
        comicCount = remoteModel.comicCount,
        storyCount = remoteModel.storyCount,
        seriesCount = remoteModel.seriesCount,
        eventCount = remoteModel.eventCount,
        saved = localModels.contains(remoteModel)
    )

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val modifyCharacterSavedStatusUseCase: ModifyCharacterSavedStatusUseCase
) : ViewModel() {

    val characters: StateFlow<PagingData<CharactersUiModel>> =
        combine(
            characterRepository.remoteCharacters.cachedIn(viewModelScope),
            characterRepository.localCharacters,
        ) { remoteCharacters, localCharacters ->
            Log.e("characterLog", "vm remote : $remoteCharacters")
            Log.e("characterLog", "\n")
            Log.e("characterLog", "vm local : $localCharacters")
            remoteCharacters.map { remoteCharacter ->
                syncAndConvertUiModel(
                    remoteModel = remoteCharacter,
                    localModels = localCharacters
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PagingData.empty()
        )

    val imageDownloadState: StateFlow<ImageDownLoadResult> =
        characterRepository.imageDownloadState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ImageDownLoadResult.NoneStart
            )

    fun modifyCharacterSavedStatus(uiModel: CharactersUiModel, saved: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            modifyCharacterSavedStatusUseCase(
                dataModel = uiModel.convertDataModel(),
                saved = saved
            )
        }
    }

    fun downloadThumbnail(url: String) {
        characterRepository.downloadThumbnail(url)
    }

}
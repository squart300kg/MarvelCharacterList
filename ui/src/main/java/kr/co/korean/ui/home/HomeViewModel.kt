package kr.co.korean.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.ui.model.CharactersUiModel
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
) : ViewModel() {

    val characters: StateFlow<PagingData<CharactersUiModel>> =
        combine(
            characterRepository.remoteCharacters.cachedIn(viewModelScope),
            characterRepository.localCharacters
        ) { remoteCharacters, localCharacters ->
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

    // TODO: 유즈케이스로 분리
    fun modifyCharacterSavedStatus(uiModel: CharactersUiModel, saved: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.modifyCharacterSavedStatus(
                dataModel = CharacterDataModel(
                    id = uiModel.id,
                    thumbnail = uiModel.thumbnail,
                    urlCount = uiModel.urlCount,
                    storyCount = uiModel.storyCount,
                    seriesCount = uiModel.seriesCount,
                    eventCount = uiModel.eventCount,
                    comicCount = uiModel.comicCount
                ),
                saved = saved
            )
        }
    }

    fun downloadThumbnail(url: String) {
        characterRepository.downloadThumbnail(url)
    }

}
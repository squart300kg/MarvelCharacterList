package kr.co.korean.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.ui.home.CharactersUiModel
import javax.inject.Inject

// TODO: BookmarkViewModel, 예외처리 각각 어떻게 할지?
// TODO: BookmarkViewModel, 상태바 모델링 필요

fun convertUiModel(dataModel: CharacterDataModel): CharactersUiModel =
    CharactersUiModel(
        id = dataModel.id,
        thumbnail = dataModel.thumbnail,
        urlCount = dataModel.urlCount,
        comicCount = dataModel.comicCount,
        storyCount = dataModel.storyCount,
        seriesCount = dataModel.seriesCount,
        eventCount = dataModel.eventCount,
        saved = true
    )
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
): ViewModel() {

    val localCharacters: StateFlow<List<CharactersUiModel>> =
        characterRepository.localCharacters
            .map { it.map(::convertUiModel) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

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
}
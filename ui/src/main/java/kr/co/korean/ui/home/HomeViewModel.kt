package kr.co.korean.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import javax.inject.Inject
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map

data class CharactersUiModel(
    val id: Int,
    val thumbnail: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
    val saved: Boolean,
)

fun CharacterDataModel.convertUiModel(): CharactersUiModel =
    CharactersUiModel(
        id = id,
        thumbnail = thumbnail,
        urlCount = urlCount,
        comicCount = comicCount,
        storyCount = storyCount,
        seriesCount = seriesCount,
        eventCount = eventCount,
        saved = true
    )

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    val remoteCharacters: StateFlow<PagingData<CharactersUiModel>> =
        characterRepository.remoteCharacters
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.map { it.convertUiModel() }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PagingData.empty()
            )

//    val characters: StateFlow<PagingData<CharactersUiModel>> =
//        combine(
//            characterRepository.remoteCharacters,
//            characterRepository.localCharacters
//        ) { remoteCharacters, localCharacters ->
//            remoteCharacters.map { it.convertUiModel() }
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = PagingData.empty()
//        )

    fun modifyCharacterSavedStatus(uiModel: CharactersUiModel) {
        viewModelScope.launch {
            characterRepository.modifyCharacterSavedStatus(
                dataModel = CharacterDataModel(
                    id = uiModel.id,
                    thumbnail = uiModel.thumbnail,
                    urlCount = uiModel.urlCount,
                    storyCount = uiModel.storyCount,
                    seriesCount = uiModel.seriesCount,
                    eventCount = uiModel.eventCount,
                    comicCount = uiModel.comicCount
                )
            )
        }
    }

    fun downloadThumbnail() {
        // TODO: Implement
    }

}
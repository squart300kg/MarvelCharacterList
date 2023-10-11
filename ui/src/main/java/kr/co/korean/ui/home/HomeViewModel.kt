package kr.co.korean.ui.home

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kr.co.korean.ui.R

data class CharactersUiModel(
    val id: Int,
    val thumbnail: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
    val saved: Boolean,
) {
    @get:DrawableRes
    val bookMarkImage: Int
        get() = if (saved) R.drawable.ic_bookmark_select_filled
        else R.drawable.ic_bookmark_select
}

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

    fun downloadThumbnail() {
        // TODO: Implement
    }

}
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

sealed interface CharactersUiState {
    object Loading: CharactersUiState
    sealed interface LoadFinished: CharactersUiState {

        data class Success(
            val uiModel: CharactersUiModel
        ): LoadFinished {
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
        }

        data class Error(val throwable: Throwable): LoadFinished
    }
}



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val remoteCharacters: StateFlow<PagingData<CharacterDataModel>> =
        characterRepository.remoteCharacters
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PagingData.empty()
            )

//    val localCharacteds: StateFlow<List<Int>> =
//        characterRepository.localCharacters
//            .map { it.ids }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000L),
//                initialValue = emptyList()
//            )
//
//    val characters: StateFlow<CharactersUiModel> =
//        combine(
//            characterRepository.remoteCharacters,
//            characterRepository.localCharacters
//        ) { remoteCharacters, localCharacters ->
//            CharactersUiModel()
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000L),
//            initialValue = CharactersUiModel()
//        )

    fun modifyCharacterSavedStatus(id: Int) {
        Result
        viewModelScope.launch {
            characterRepository.modifyCharacterSavedStatus(id)
        }
    }

}
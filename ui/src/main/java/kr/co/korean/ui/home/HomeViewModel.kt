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

// TODO: UI모델 사용하도록 변경 필요
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

    fun modifyCharacterSavedStatus(id: Int) {
        viewModelScope.launch {
            characterRepository.modifyCharacterSavedStatus(id)
        }
    }

}
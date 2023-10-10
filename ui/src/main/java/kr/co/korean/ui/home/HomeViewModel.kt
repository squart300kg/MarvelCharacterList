package kr.co.korean.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.repository.model.SavedIdsDataModel
import javax.inject.Inject

// TODO: UI모델 사용하도록 변경 필요
data class CharacterUiModel(
    val id: Int,
    val thumbnail: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
    val saved: Boolean,
)

// TODO: 이미지 너비, 높이 계산 및 로딩 ContentScale방식 조정해야 함
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val remoteCharacters: StateFlow<List<CharacterDataModel>> =
        characterRepository.getCharacters()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    val localCharacteds: StateFlow<List<Int>> =
        characterRepository.savedCharacters
            .map { it.ids }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    fun modifyCharacterSavedStatus(id: Int) {
        viewModelScope.launch {
            characterRepository.modifyCharacterSavedStatus(id)
        }
    }

}
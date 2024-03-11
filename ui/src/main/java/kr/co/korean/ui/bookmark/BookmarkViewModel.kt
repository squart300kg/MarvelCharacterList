package kr.co.korean.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.common.model.UiResult
import kr.co.korean.domain.ModifyCharacterSavedStatusUseCase
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.model.convertDataModel
import kr.co.korean.model.convertUiModel
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val modifyCharacterSavedStatusUseCase: ModifyCharacterSavedStatusUseCase
): ViewModel() {

    val localCharacters: StateFlow<UiResult<List<CharactersUiModel>>> =
        characterRepository.localCharacters
            .catch { UiResult.Error(it) }
            .map { UiResult.Success(it.map(::convertUiModel)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = UiResult.Loading
            )

    fun modifyCharacterSavedStatus(uiModel: CharactersUiModel, saved: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            modifyCharacterSavedStatusUseCase(
                dataModel = uiModel.convertDataModel(),
                saved = saved
            )
        }
    }
}
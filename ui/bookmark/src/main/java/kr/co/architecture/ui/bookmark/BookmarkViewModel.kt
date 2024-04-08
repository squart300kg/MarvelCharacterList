package kr.co.architecture.ui.bookmark

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
import kr.co.architecture.common.model.UiResult
import kr.co.architecture.domain.ModifyCharacterSavedStatusUseCase
import kr.co.architecture.repository.CharacterRepository
import kr.co.architecture.model.CharactersUiModel
import kr.co.architecture.model.convertDataModel
import kr.co.architecture.model.convertUiModel
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
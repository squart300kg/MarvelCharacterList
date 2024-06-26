package kr.co.architecture.ui.detail

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.co.architecture.common.model.UiResult
import kr.co.architecture.repository.CharacterRepository
import kr.co.architecture.repository.model.CharacterDetailDataModel
import kr.co.architecture.ui.detail.navigation.DETAIL_ID_ARG
import javax.inject.Inject

@Stable
data class DetailUiModel(
    val thumbnail: String,
    val name: String,
    val description: String,
    val contents: Map<CharacterDetailDataModel.ContentsType, List<String>>
)

private fun convertUiModel(dataModel: CharacterDetailDataModel) =
    DetailUiModel(
        thumbnail = dataModel.thumbnail,
        name = dataModel.name,
        description = dataModel.description,
        contents = dataModel.contents
    )

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
): ViewModel() {

    private val id: StateFlow<String?> = savedStateHandle.getStateFlow(DETAIL_ID_ARG, null)
    val uiState: StateFlow<UiResult<DetailUiModel>> =
        id.filterNotNull()
            .flatMapLatest { characterRepository.getRemoteSingleCharacter(it.toInt()) }
            .catch { Log.e("detailErrorLog", it.stackTraceToString()) }
            .map { UiResult.Success(it.map(::convertUiModel).first()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = UiResult.Loading
            )
}
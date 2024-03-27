package kr.co.korean.ui.detail

import android.util.Log
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
import kr.co.korean.common.model.UiResult
import kr.co.korean.model.convertUiModel
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.ui.detail.navigation.DETAIL_ID_ARG
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
): ViewModel() {

    private val id: StateFlow<String?> = savedStateHandle.getStateFlow(DETAIL_ID_ARG, null)
    val uiState = id.filterNotNull()
        .flatMapLatest { characterRepository.getRemoteSingleCharacter(it.toInt()) }
        .catch { Log.e("detailErrorLog", it.stackTraceToString()) }
        .map { UiResult.Success(it.map(::convertUiModel)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UiResult.Loading
        )
}
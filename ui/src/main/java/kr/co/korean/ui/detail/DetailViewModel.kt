package kr.co.korean.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kr.co.korean.common.model.UiResult
import kr.co.korean.model.convertUiModel
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.ui.detail.navigation.DETAIL_CONTENTS_TYPE_ARG
import kr.co.korean.ui.detail.navigation.DETAIL_ID_ARG
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
): ViewModel() {

    private val id: StateFlow<String?> = savedStateHandle.getStateFlow(DETAIL_ID_ARG, null)
    private val type: StateFlow<String?> = savedStateHandle.getStateFlow(DETAIL_CONTENTS_TYPE_ARG, null)
    val uiState =
        combine(
            flow = id,
            flow2 = type
        ) { id, type ->
            checkNotNull(id) { "$DETAIL_ID_ARG must not be null" }
            checkNotNull(type) { "$DETAIL_CONTENTS_TYPE_ARG must not be null" }

            characterRepository.getRemoteSingleContent(id.toInt(), type.lowercase()).first()
        }
        .catch { Log.e("detailErrorLog", it.stackTraceToString()) }
        .map { UiResult.Success(it.map(::convertUiModel)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UiResult.Loading
        )
}
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
import kr.co.korean.repository.ContentsType
import kr.co.korean.ui.detail.navigation.DETAIL_CONTENTS_TYPE_ARG
import kr.co.korean.ui.detail.navigation.DETAIL_ID_ARG
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
): ViewModel() {


}
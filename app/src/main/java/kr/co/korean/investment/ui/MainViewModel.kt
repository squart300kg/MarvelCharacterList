package kr.co.korean.investment.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.co.korean.common.model.Result
import kr.co.korean.investment.R
import kr.co.korean.repository.AppStateRepository
import javax.inject.Inject

data class DialogUiState(
    val dialogText: String = "",
) {
    fun isDialogShown(): Boolean =
        dialogText.isNotEmpty()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appStateRepository: AppStateRepository
): ViewModel() {

    private var _dialogUiState = MutableStateFlow(DialogUiState())
    val dialogUiState = _dialogUiState.asStateFlow()

    val appFirstStartedState: StateFlow<Result<Boolean>> =
        appStateRepository.appFirstStatedState
            .catch { Result.Error(it) }
            .map { Result.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = Result.Loading
            )

    fun startApp() {
        viewModelScope.launch {
            appStateRepository.startApp()
        }
    }

    fun showPermissionNeededDialog() {
        _dialogUiState.update {
            it.copy(
                dialogText = context.getString(R.string.permissionGrantGuide)
            )
        }
    }

    fun showPermissionSettingRedirectDialog() {

    }


}
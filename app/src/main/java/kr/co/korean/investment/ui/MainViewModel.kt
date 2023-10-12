package kr.co.korean.investment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.common.model.Result
import kr.co.korean.repository.AppStateRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appStateRepository: AppStateRepository
): ViewModel() {

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

    }

    fun showPermissionSettingRedirectDialog() {

    }


}
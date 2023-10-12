package kr.co.korean.investment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.korean.repository.AppStateRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appStateRepository: AppStateRepository
): ViewModel() {

    val appFirstStartedState: StateFlow<Boolean> =
        appStateRepository.appFirstStatedState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = false
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
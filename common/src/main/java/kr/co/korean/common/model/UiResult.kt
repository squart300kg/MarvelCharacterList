package kr.co.korean.common.model

sealed interface UiResult<out T> {
    object Loading: UiResult<Nothing>
    data class Success<T>(val uiModels: T): UiResult<T>
    data class Error(val throwable: Throwable): UiResult<Nothing>
}
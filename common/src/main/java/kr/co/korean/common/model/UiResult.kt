package kr.co.korean.common.model

sealed interface UiResult<out T> {
    data object Loading: UiResult<Nothing>
    data class Success<T>(val model: T): UiResult<T>
    data class Error(val throwable: Throwable): UiResult<Nothing>
}
package kr.co.korean.common.model

sealed interface Result<out T> {
    object Loading:
        Result<Nothing>
    data class Success<T>(val model: T):
        Result<T>
    data class Error(val throwable: Throwable):
        Result<Nothing>
}
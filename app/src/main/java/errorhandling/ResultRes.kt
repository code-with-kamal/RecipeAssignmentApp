package errorhandling

sealed class ResultRes<out T> {
    data class Success<T>(val data: T) : ResultRes<T>()
    data class Error(val exception: String) : ResultRes<Nothing>()
    data object Loading : ResultRes<Nothing>()
}
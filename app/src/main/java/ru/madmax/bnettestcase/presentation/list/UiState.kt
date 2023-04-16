package ru.madmax.bnettestcase.presentation.list

data class UiState(
    val query: String = "",
    val lastQueryScrolled: String = "",
    val hasNotScrolledForCurrentSearch: Boolean = false
)

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}
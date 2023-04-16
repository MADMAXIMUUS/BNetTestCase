package ru.madmax.bnettestcase.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.madmax.bnettestcase.domain.model.Drag
import ru.madmax.bnettestcase.domain.useCase.DragUseCases
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val dragUseCases: DragUseCases
) : ViewModel() {

    val uiState: StateFlow<UiState>

    val pagingDataFlow: Flow<PagingData<Drag>>

    val accept: (UiAction) -> Unit

    private fun searchRepo(queryString: String): Flow<PagingData<Drag>> =
        dragUseCases.searchUseCase(queryString)

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = "")) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = "")) }

        pagingDataFlow = searches
            .flatMapLatest { searchRepo(queryString = it.query) }
            .cachedIn(viewModelScope)

        uiState = combine(
            searches,
            queriesScrolled,
            ::Pair
        )
            .map { (search, scroll) ->
                UiState(
                    query = search.query,
                    lastQueryScrolled = scroll.currentQuery,
                    hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }
}
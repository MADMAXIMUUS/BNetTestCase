package ru.madmax.bnettestcase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import ru.madmax.bnettestcase.domain.model.Drag
import ru.madmax.bnettestcase.domain.useCase.DragUseCases
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: DragUseCases
) : ViewModel() {

    private val uiState = MutableStateFlow("")

    var pagingFlow: Flow<PagingData<Drag>> = uiState
        .debounce(500)
        .flatMapLatest {
            useCases.searchUseCase(it)
        }
        .cachedIn(viewModelScope)


    fun updateSearchQuery(value: String) {
        this.uiState.value = value
    }
}
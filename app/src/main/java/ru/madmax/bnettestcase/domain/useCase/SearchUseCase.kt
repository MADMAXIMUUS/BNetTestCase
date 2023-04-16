package ru.madmax.bnettestcase.domain.useCase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.madmax.bnettestcase.data.repository.DragRepository
import ru.madmax.bnettestcase.domain.model.Drag

class SearchUseCase(
    private val dragRepository: DragRepository
) {
    operator fun invoke(search: String): Flow<PagingData<Drag>> {
        return dragRepository.search(search)
    }
}
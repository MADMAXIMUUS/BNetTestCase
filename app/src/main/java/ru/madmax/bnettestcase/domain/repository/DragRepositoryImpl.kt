package ru.madmax.bnettestcase.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import ru.madmax.bnettestcase.data.dataSource.DragService
import ru.madmax.bnettestcase.data.dataSource.DragPagingSource
import ru.madmax.bnettestcase.data.repository.DragRepository
import ru.madmax.bnettestcase.domain.model.Drag

class DragRepositoryImpl(
    private val dragService: DragService
) : DragRepository {

    override fun search(search: String): Flow<PagingData<Drag>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE*2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DragPagingSource(dragService, search)
            }, initialKey = 0
        ).flow
    }

    override suspend fun getById(id: Int): Drag {
        val response = dragService.getById(id)
        if (response.isSuccessful)
            return response.body() ?: Drag()
        else
            throw HttpException(response)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
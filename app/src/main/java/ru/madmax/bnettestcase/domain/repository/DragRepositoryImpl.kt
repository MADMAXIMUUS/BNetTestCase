package ru.madmax.bnettestcase.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import ru.madmax.bnettestcase.data.dataSource.DragApi
import ru.madmax.bnettestcase.data.dataSource.DragPagingSource
import ru.madmax.bnettestcase.data.repository.DragRepository
import ru.madmax.bnettestcase.domain.model.Drag

class DragRepositoryImpl(
    private val dragApi: DragApi
) : DragRepository {

    override fun search(search: String): Flow<PagingData<Drag>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { DragPagingSource(dragApi, search) }
        ).flow
    }

    override suspend fun getById(id: Int): Drag {
        val response = dragApi.getById(id)
        if (response.isSuccessful)
            return response.body() ?: Drag()
        else
            throw HttpException(response)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
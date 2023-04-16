package ru.madmax.bnettestcase.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.madmax.bnettestcase.domain.model.Drag

class DragPagingSource(
    private val dragApi: DragApi,
    private val query: String
) : PagingSource<Int, Drag>() {

    override fun getRefreshKey(state: PagingState<Int, Drag>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Drag> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        val response = dragApi.search(search = query, offset = page, limit = pageSize)
        return if (response.isSuccessful) {
            val drags = checkNotNull(response.body())
            val nextKey = if (drags.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(drags, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}
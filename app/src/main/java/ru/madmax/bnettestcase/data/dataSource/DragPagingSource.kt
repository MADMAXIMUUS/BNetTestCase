package ru.madmax.bnettestcase.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import retrofit2.HttpException
import ru.madmax.bnettestcase.domain.model.Drag

class DragPagingSource(
    private val dragService: DragService,
    private val query: String
) : PagingSource<Int, Drag>() {

    override fun getRefreshKey(state: PagingState<Int, Drag>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Drag> {
        val page: Int = params.key ?: 0
        val pageSize: Int = params.loadSize
        return try {
            val response = dragService
                .search(search = query, offset = pageSize * page + 1, limit = pageSize)
            if (response.isSuccessful) {
                val drags = checkNotNull(response.body())
                val nextKey = if (drags.size < pageSize) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                return LoadResult.Page(drags, prevKey, nextKey)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
package ru.madmax.bnettestcase.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.madmax.bnettestcase.domain.model.Drag

interface DragRepository {

    fun search(search: String): Flow<PagingData<Drag>>

    suspend fun getById(id: Int): Drag
}
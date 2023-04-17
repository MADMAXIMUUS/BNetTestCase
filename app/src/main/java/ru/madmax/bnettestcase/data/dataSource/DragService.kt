package ru.madmax.bnettestcase.data.dataSource

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import ru.madmax.bnettestcase.domain.model.Drag

interface DragService {

    @GET("index/")
    suspend fun search(
        @Query("search") search: String="",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<List<Drag>>

    @GET("item/")
    suspend fun getById(
        @Query("id") id: Int,
    ): Response<Drag>
}
package ru.madmax.bnettestcase.data.dataSource

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.madmax.bnettestcase.domain.model.Response
import ru.madmax.bnettestcase.domain.model.ResponseItem

interface DragApi {

    @GET("trending")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("rating") rating: String
    ): Response

    @GET("{id}")
    suspend fun getById(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): ResponseItem
}
package ru.madmax.bnettestcase.data.dataSource

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response
import ru.madmax.bnettestcase.domain.model.Drag

interface DragApi {

    @GET("index")
    suspend fun search(
        @Query("id") id: Int = 0,
        @Query("search") search: String? = null,
        @Query("crop_id") cropId: Int = 0,
        @Query("harmful_object_id") objectId: Int = 0,
        @Query("ingredient_id") ingredientId: Int = 0,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10
    ): Response<List<Drag>>

    @GET("item")
    suspend fun getById(
        @Path("id") id: Int,
    ): Response<Drag>
}
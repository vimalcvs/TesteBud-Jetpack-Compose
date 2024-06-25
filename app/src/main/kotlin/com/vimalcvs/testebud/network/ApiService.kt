package com.vimalcvs.testebud.network

import com.vimalcvs.testebud.model.ResponseCategory
import com.vimalcvs.testebud.model.ResponseDetail
import com.vimalcvs.testebud.model.ResponseMeal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @Headers(CACHE, AGENT)
    @GET("filter.php")
    suspend fun getRecipe(@Query("c") c: String): Response<ResponseMeal>

    @Headers(CACHE, AGENT)
    @GET("lookup.php")
    suspend fun getDetail(@Query("i") i: String): Response<ResponseDetail>

    @Headers(CACHE, AGENT)
    @GET("categories.php")
    suspend fun getCategories(): Response<ResponseCategory>

    @Headers(CACHE, AGENT)
    @GET("search.php")
    suspend fun getSearch(@Query("s") s: String): Response<ResponseMeal>

    companion object {
        const val CACHE: String = "Cache-Control: max-age=0"
        const val AGENT: String = "Data-Agent: Meal"
    }
}

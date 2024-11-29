package data

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface ApiService {
    @GET("v2/everything")
    fun getHealthArticles(
        @Query("q") query: String = "cancer",
        @Query("apiKey") apiKey: String = "e207bb8d0b4e481ca477c5032d766a7a",
        @Query("language") language: String = "en",
        @Query("pageSize") pageSize: Int = 10
    ): Call<ResponseArtikel>
}
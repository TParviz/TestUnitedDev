package tj.test.testuniteddev.data.repositories.api

import retrofit2.http.GET
import retrofit2.http.Query
import tj.test.testuniteddev.data.models.MoviesDataResponse

interface MoviesApi {
    @GET("?apikey=85aedc49")
    suspend fun getMovies(
        @Query("s") search: String,
        @Query("page") page: Int
    ): MoviesDataResponse
}
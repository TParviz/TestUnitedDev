package tj.test.testuniteddev.data.repositories

import tj.test.testuniteddev.data.models.MoviesData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor() {
    suspend fun getMovies(search: String, page : Int) : List<MoviesData> {
        return CommonConnection().moviesApi.getMovies(search = search, page = page).search
    }
}
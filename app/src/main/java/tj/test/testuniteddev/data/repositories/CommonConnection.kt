package tj.test.testuniteddev.data.repositories

import tj.test.testuniteddev.data.ConnectionFactory
import tj.test.testuniteddev.data.repositories.api.MoviesApi

class CommonConnection: ConnectionFactory() {
    val moviesApi: MoviesApi = getRetrofit().create(MoviesApi::class.java)
}
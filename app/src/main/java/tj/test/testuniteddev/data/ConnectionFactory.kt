package tj.test.testuniteddev.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ConnectionFactory {

    fun getRetrofit(baseUrl: String = BASE_URL): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    companion object {
        private const val BASE_URL = "http://www.omdbapi.com/"
    }
}
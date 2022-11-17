package tj.test.testuniteddev.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.test.testuniteddev.domain.db.AppDatabase
import tj.test.testuniteddev.domain.db.likes.MoviesInfoEntity
import tj.test.testuniteddev.domain.net.GetMoviesUseCaseImpl
import tj.test.testuniteddev.domain.net.GetMoviesUseCaseParams
import tj.test.testuniteddev.showMessage
import tj.test.testuniteddev.ui.dialog.SearchMovieParams
import tj.test.testuniteddev.ui.modelUi.MovieUi
import tj.test.testuniteddev.ui.modelUi.toMovieUi
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCaseImpl: GetMoviesUseCaseImpl,
    private val application: Application,
) : ViewModel() {

    private val _moviesList = MutableLiveData<List<MovieUi>>()
    val moviesList: LiveData<List<MovieUi>> = _moviesList

    var movieParams: SearchMovieParams? = null
    private val context = getApplication(application).applicationContext

    private val db = AppDatabase(context)

    fun getMovies(searchMovieParams: SearchMovieParams) {
        movieParams = searchMovieParams
        viewModelScope.launch {
            getMoviesUseCaseImpl(
                GetMoviesUseCaseParams(
                    search = searchMovieParams.search,
                    page = searchMovieParams.quantity.toInt()
                )
            ).collect { result ->
                result.onFailure { error ->
                    showMessage(error.message.toString(), context)
                }.onSuccess { response ->
                    getListCorrected(response.map { it.toMovieUi() })
                    response.map { addToDatabase(it.toMovieUi()) }
                }
            }
        }
    }

    private fun getListCorrected(movieList: List<MovieUi>) {
        viewModelScope.launch {
            _moviesList.value = movieList.map {
                val data = db.moviesInfoDao().findByTitle(it.imdbId)
                it.copy(isLiked = data.isLiked, commentCount = data.commentCount)
            }
        }
    }

    private fun addToDatabase(movieUi: MovieUi) {
        viewModelScope.launch {
            if (db.moviesInfoDao().findByTitle(movieUi.imdbId) == null) {
                db.moviesInfoDao().insertAll(
                    MoviesInfoEntity(
                        titleId = movieUi.imdbId,
                        isLiked = movieUi.isLiked,
                        commentCount = movieUi.commentCount
                    )
                )
            }
        }
    }

    fun updateDatabase(movieUi: MovieUi, comment: Int = 0) {
        viewModelScope.launch {
            db.moviesInfoDao().updateInfo(
                MoviesInfoEntity(
                    titleId = movieUi.imdbId,
                    isLiked = movieUi.isLiked,
                    commentCount = movieUi.commentCount + comment
                )
            )
        }
    }
}
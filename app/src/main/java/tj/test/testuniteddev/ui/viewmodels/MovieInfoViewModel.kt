package tj.test.testuniteddev.ui.viewmodels

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tj.test.testuniteddev.domain.db.AppDatabase
import tj.test.testuniteddev.domain.db.likes.MoviesInfoEntity
import tj.test.testuniteddev.ui.MovieInfoFragmentArgs
import tj.test.testuniteddev.ui.modelUi.MovieUi
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = MovieInfoFragmentArgs.fromSavedStateHandle(savedStateHandle)
    var movieItem: MovieUi = args.movieInfo
    private val context = Contexts.getApplication(application).applicationContext

    private val db = AppDatabase(context)

    init {
        getItemCorrected(movieItem)
    }

    private fun getItemCorrected(item: MovieUi) {
        viewModelScope.launch {
            val data = db.moviesInfoDao().findByTitle(item.imdbId)
            movieItem = item.copy(isLiked = data.isLiked, commentCount = data.commentCount)
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
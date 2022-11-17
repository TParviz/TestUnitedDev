package tj.test.testuniteddev.ui.modelUi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import tj.test.testuniteddev.data.models.MoviesData

@Parcelize
data class MovieUi(
    val movieName: String,
    val poster: String,
    val date: String,
    val imdbId: String,
    val type: String,
    val isLiked: Boolean,
    val commentCount: Int
) : Parcelable

fun MoviesData.toMovieUi(): MovieUi {
    return MovieUi(
        movieName = Title,
        poster = Poster,
        date = Year,
        type = Type,
        imdbId = imdbID,
        commentCount = 0,
        isLiked = false
    )
}
package tj.test.testuniteddev.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MoviesDataResponse(
    @SerializedName("Search")
    val search: List<MoviesData>
)

@Parcelize
data class MoviesData(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String,
) : Parcelable
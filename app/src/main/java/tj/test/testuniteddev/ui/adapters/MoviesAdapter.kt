package tj.test.testuniteddev.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import tj.test.testuniteddev.databinding.ItemMovieBinding
import tj.test.testuniteddev.ui.modelUi.MovieUi

class MoviesAdapter(
    private val onViewClicked: (MovieUi) -> Unit,
    private val onLikeClicked: (MovieUi) -> Unit,
) : ListAdapter<MovieUi, MoviesViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            binding = ItemMovieBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ),
            onViewClicked = onViewClicked,
            onLikeClicked = onLikeClicked,
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieUi>() {
            override fun areItemsTheSame(
                oldItem: MovieUi,
                newItem: MovieUi
            ): Boolean =
                oldItem.movieName == newItem.movieName

            override fun areContentsTheSame(
                oldItem: MovieUi,
                newItem: MovieUi
            ): Boolean =
                oldItem == newItem
        }
    }
}
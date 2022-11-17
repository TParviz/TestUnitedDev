package tj.test.testuniteddev.ui.adapters

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.test.testuniteddev.databinding.ItemMovieBinding
import tj.test.testuniteddev.ui.modelUi.MovieUi

class MoviesViewHolder(
    private val binding: ItemMovieBinding,
    private val onViewClicked: (MovieUi) -> Unit,
    private val onLikeClicked: (MovieUi) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieUi) = with(binding) {
        tvTitle.text = item.movieName
        tvType.text = item.type
        tvDate.text = item.date
        root.setOnClickListener {
            onViewClicked(item)
        }
        cardComment.tvComments.text = item.commentCount.toString()

        when (item.isLiked) {
            true -> isLikedVisible()
            false -> isLikeVisible()
        }

        ibLike.setOnClickListener {
            isLikedVisible()
            onLikeClicked(item.copy(isLiked = true))
        }
        ibLiked.setOnClickListener {
            isLikeVisible()
            onLikeClicked(item.copy(isLiked = false))
        }

        Glide.with(ivPoster)
            .load(item.poster)
            .into(ivPoster)
    }

    private fun isLikeVisible() {
        binding.ibLike.isVisible = true
        binding.ibLiked.isVisible = false
    }

    private fun isLikedVisible() {
        binding.ibLike.isVisible = false
        binding.ibLiked.isVisible = true
    }
}
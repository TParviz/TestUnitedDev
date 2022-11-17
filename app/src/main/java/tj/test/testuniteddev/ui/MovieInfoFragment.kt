package tj.test.testuniteddev.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import tj.test.testuniteddev.R
import tj.test.testuniteddev.databinding.FragmentMovieInfoBinding
import tj.test.testuniteddev.showMessage
import tj.test.testuniteddev.ui.viewmodels.MovieInfoViewModel


@AndroidEntryPoint
class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() = with(binding) {
        appToolBar.topAppBar.setNavigationIcon(R.drawable.arrow)
    }

    private fun initListeners() = with(binding) {
        appToolBar.topAppBar.setOnClickListener {
            findNavController().popBackStack()
        }
        ibLike.setOnClickListener {
            isLikedVisible()
            viewModel.updateDatabase(viewModel.movieItem.copy(isLiked = true))
        }
        ibLiked.setOnClickListener {
            isLikeVisible()
            viewModel.updateDatabase(viewModel.movieItem.copy(isLiked = false))
        }
        btnLeaveComment.setOnClickListener {
            etComments.setText("")
            showMessage(getString(R.string.comment_added), requireContext())
            viewModel.updateDatabase(movieUi = viewModel.movieItem, comment = 1)
        }
        cardComment.itemParentLayout.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("$IMDB_URL/${viewModel.movieItem.imdbId}"))
            startActivity(browserIntent)
        }
    }

    private fun initObservers() = with(viewModel) {
        binding.appToolBar.topAppBar.title = "${movieItem.type} Info"
        binding.cardComment.tvComments.text = getString(R.string.imdb)
        binding.tvTitle.text = movieItem.movieName
        binding.tvType.text = movieItem.type
        binding.tvDate.text = movieItem.date

        when (movieItem.isLiked) {
            true -> isLikedVisible()
            false -> isLikeVisible()
        }

        Glide.with(binding.ivPoster)
            .load(movieItem.poster)
            .into(binding.ivPoster)
    }

    private fun isLikeVisible() {
        binding.ibLike.isVisible = true
        binding.ibLiked.isVisible = false
    }

    private fun isLikedVisible() {
        binding.ibLike.isVisible = false
        binding.ibLiked.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMDB_URL = "https://www.imdb.com/title"
    }
}
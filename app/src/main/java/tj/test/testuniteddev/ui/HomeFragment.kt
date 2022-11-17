package tj.test.testuniteddev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tj.test.testuniteddev.databinding.FragmentHomeBinding
import tj.test.testuniteddev.ui.adapters.MoviesAdapter
import tj.test.testuniteddev.ui.dialog.SearchMovieDialogFragment
import tj.test.testuniteddev.ui.dialog.SearchMovieDialogFragment.Companion.TAG_SEARCH_MOVIE_DIALOG
import tj.test.testuniteddev.ui.dialog.SearchMovieParams
import tj.test.testuniteddev.ui.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(),
    SearchMovieDialogFragment.SearchMovieActions {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapterMovies = MoviesAdapter(
        onViewClicked = { item ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMovieInfoFragment(item)
            )
        },
        onLikeClicked = { item ->
            viewModel.updateDatabase(item)
        },
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        rvMoviesList.adapter = adapterMovies
        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            viewModel.movieParams?.let { viewModel.getMovies(it) }
        }
        appToolBar.topAppBar.setOnClickListener {
            openUserSimpleInfoDialog()
        }
    }

    private fun initObservers() = with(viewModel) {
        moviesList.observe(viewLifecycleOwner) { list ->
            binding.btnClick.isVisible = false
            binding.rvMoviesList.isVisible = true
            adapterMovies.submitList(list)
        }
    }

    private fun openUserSimpleInfoDialog() {
        SearchMovieDialogFragment().show(childFragmentManager, TAG_SEARCH_MOVIE_DIALOG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onApplySearchMovieParams(searchMovieParams: SearchMovieParams) {
        viewModel.getMovies(searchMovieParams)
        binding.appToolBar.topAppBar.title = searchMovieParams.search
    }
}
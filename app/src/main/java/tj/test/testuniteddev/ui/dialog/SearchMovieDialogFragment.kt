package tj.test.testuniteddev.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import tj.test.testuniteddev.databinding.DialogSearchMovieBinding

@AndroidEntryPoint
class SearchMovieDialogFragment : BottomSheetDialogFragment() {

    private var listener: SearchMovieActions? = null
    lateinit var binding: DialogSearchMovieBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? SearchMovieActions
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                BottomSheetBehavior.from(parent).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                }
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        btnApply.setOnClickListener {
            listener?.onApplySearchMovieParams(
                SearchMovieParams(
                    search = etTitleInput.text.toString(),
                    quantity = etQuantityInput.text.toString()
                )
            )
            dismiss()
        }
    }


    interface SearchMovieActions {
        fun onApplySearchMovieParams(searchMovieParams: SearchMovieParams)
    }

    companion object {
        fun newInstance(): SearchMovieDialogFragment {
            return SearchMovieDialogFragment()
        }

        const val TAG_SEARCH_MOVIE_DIALOG = "SearchMovieDialogFragment"
    }
}
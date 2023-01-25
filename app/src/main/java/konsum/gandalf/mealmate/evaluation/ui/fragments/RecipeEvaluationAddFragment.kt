package konsum.gandalf.mealmate.evaluation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.databinding.FragmentRecipeEvaluationAddBinding
import konsum.gandalf.mealmate.evaluation.ui.viewmodels.RecipeEvaluationAddViewModel
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeEvaluationAddFragment : Fragment() {

    private lateinit var _binding: FragmentRecipeEvaluationAddBinding
    private val binding
        get() = _binding

    private val viewModel: RecipeEvaluationAddViewModel by activityViewModels()

    private val navArgs: RecipeEvaluationAddFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEvaluationAddBinding.inflate(inflater, container, false)
        initButton()
        listenToChannels()
        return binding.root
    }

    private fun initButton() {
        with(binding) {
            recipeEvaluationAddBtn.setOnClickListener {
                viewModel.validateEvaluation(
                    navArgs.recipe.recipeId,
                    recipeEvaluationAddRating.rating,
                    (recipeEvaluationAddDifficulty.progress * .5f),
                    recipeEvaluationAddComment.text.toString(),
                    navArgs.user
                )
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        val action = RecipeEvaluationAddFragmentDirections
                            .actionRecipeEvaluationAddFragmentToRecipeEvaluationFragment(navArgs.recipe)
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                    is CustomEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                    is CustomEvent.ErrorCode -> {
                        binding.recipeEvaluationProgress.progressBar.isVisible = false
                        when (event.code) {
                            1 -> {
                                binding.apply { recipeEvaluationAddCommentContainer.error = "comment should not be empty" }
                            }
                        }
                    }
                }
            }
        }
    }
}

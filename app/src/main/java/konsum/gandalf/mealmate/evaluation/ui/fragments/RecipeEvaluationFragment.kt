package konsum.gandalf.mealmate.evaluation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.databinding.FragmentRecipeEvaluationBinding
import konsum.gandalf.mealmate.evaluation.ui.adapters.RecipeEvaluationAdapter
import konsum.gandalf.mealmate.evaluation.ui.viewmodels.RecipeEvaluationViewModel
import konsum.gandalf.mealmate.user.domain.models.User

@AndroidEntryPoint
class RecipeEvaluationFragment : Fragment() {

    private lateinit var _binding: FragmentRecipeEvaluationBinding
    private val binding
        get() = _binding

    private val navArgs: RecipeEvaluationFragmentArgs by navArgs()
    private val viewModel: RecipeEvaluationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEvaluationBinding.inflate(inflater, container, false)

        fillContent()
        registerEvaluations()
        registerUserProfile()

        return binding.root
    }

    private fun fillContent() {
        with(binding) {
            Picasso.get().load(navArgs.recipe.imageUrl).into(
                recipeEvaluationIv,
                object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        recipeEvalutaionProgress.isVisible = false
                    }
                    override fun onError(e: java.lang.Exception?) {
                    }
                }
            )
        }
    }

    private fun registerUserProfile() {
        viewModel.loadCurrentUser()
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            Picasso.get().load(user?.imageUrl).into(
                binding.recipeEvaluationAddCard.recipeEvaluationPreviewIv,
                object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        binding.recipeEvalutaionProgress.isVisible = false
                    }
                    override fun onError(e: java.lang.Exception?) {
                    }
                }
            )

            if (user != null) {
                initButtons(user)
            }
        }
    }

    private fun registerEvaluations() {
        viewModel.getEvaluations(navArgs.recipe.recipeId)
        viewModel.recipeEvaluations.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                binding.recipeEvaluationRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.recipeEvaluationRv.adapter = RecipeEvaluationAdapter(requireContext(), it)
            }
        }
    }

    private fun initButtons(user: User) {
        with(binding) {
            recipeEvaluationCardContainer.setOnClickListener {
                val action = RecipeEvaluationFragmentDirections
                    .actionRecipeEvaluationFragmentToRecipeEvaluationAddFragment(
                        navArgs.recipe,
                        user
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }
}

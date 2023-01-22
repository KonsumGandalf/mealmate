package konsum.gandalf.mealmate.recipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.databinding.FragmentRecipeSearchResultBinding
import konsum.gandalf.mealmate.recipe.ui.adapter.RecipeAdapter
import konsum.gandalf.mealmate.recipe.ui.viewmodels.RecipeSearchResultViewModel

@AndroidEntryPoint
class RecipeSearchResultFragment : Fragment() {
    private lateinit var binding: FragmentRecipeSearchResultBinding
    private val recipeViewModel by viewModels<RecipeSearchResultViewModel>()
    private val navArgs: RecipeSearchResultFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel.getFilteredRecipes(
            navArgs.recipeName,
            navArgs.areaArray.toList(),
            navArgs.categoryArray.toList()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeSearchResultBinding.inflate(inflater, container, false)
        registerFilteredRecipes()
        return binding.root
    }

    private fun registerFilteredRecipes() {
        recipeViewModel.currentFilteredRecipes.observe(viewLifecycleOwner) { recipes ->
            with(binding) {
                recipes?.let {
                    recipeSearchResultContainerRv.layoutManager =
                        LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    recipeSearchResultContainerRv.adapter = RecipeAdapter(it)
                }
            }
        }
    }


}

package konsum.gandalf.mealmate.recipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentRecipeSearchBinding
import konsum.gandalf.mealmate.recipe.ui.adapter.AreaAdapter
import konsum.gandalf.mealmate.recipe.ui.adapter.CategoryAdapter
import konsum.gandalf.mealmate.recipe.ui.adapter.RecipeAdapter
import konsum.gandalf.mealmate.recipe.ui.viewmodels.RecipeSearchResultViewModel
import konsum.gandalf.mealmate.recipe.ui.viewmodels.RecipeSearchViewModel
import konsum.gandalf.mealmate.utils.ui.ChipsGridBuilder

@AndroidEntryPoint
class RecipeSearchFragment : Fragment() {
    private lateinit var binding: FragmentRecipeSearchBinding

    private val recipeViewModel by viewModels<RecipeSearchViewModel>()
    private val recipeResultViewModel by viewModels<RecipeSearchResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel.getCategories()
        recipeViewModel.getRandomRecipes()
        recipeViewModel.getAreas()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipeSearchBinding.inflate(inflater, container, false)

        registerAreas()
        registerCategories()
        registerRandomRecipes()
        registerSearchView()

        return binding.root
    }

    private fun registerAreas() {
        recipeViewModel.currentAreas.observe(viewLifecycleOwner) { areas ->
            areas?.let {
                binding.recipeSearchAreaContainer.layoutManager =
                    ChipsGridBuilder.buildLayout(requireContext())
                binding.recipeSearchAreaContainer.adapter =
                    AreaAdapter(it, recipeViewModel.currentSelectedAreas)
            }
        }
    }

    private fun registerCategories() {
        recipeViewModel.currentCategories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                binding.recipeSearchCategoriesRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.recipeSearchCategoriesRv.adapter =
                    CategoryAdapter(requireContext(), it, recipeViewModel.currentSelectedCategories)
            }
        }
    }

    private fun registerRandomRecipes() {
        recipeViewModel.currentRandomRecipes.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                binding.recipeSearchRandomRecipeRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.recipeSearchRandomRecipeRv.adapter = RecipeAdapter(it)
            }
        }
    }

    private fun registerSearchView() {
        binding.recipeSearchSearchView
            .editText
            .setOnEditorActionListener { v, actionId, event ->
                binding.recipeSearchSearchBar.setText(binding.recipeSearchSearchView.text)
                binding.recipeSearchSearchView.hide()
                false
            }


    }
}

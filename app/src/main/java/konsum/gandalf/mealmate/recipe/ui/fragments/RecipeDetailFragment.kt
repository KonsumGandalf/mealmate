package konsum.gandalf.mealmate.recipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.databinding.FragmentRecipeDetailBinding
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.ui.adapter.IngredientAdapter

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private lateinit var _binding: FragmentRecipeDetailBinding
    private val binding
        get() = _binding

    private val navArgs: RecipeDetailFragmentArgs by navArgs()

    private lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        recipe = navArgs.argRecipe
        fillContent(recipe.ingredients)
        return binding.root
    }

    private fun fillContent(ingredientsPar: List<Ingredient>) {
        with(binding) {
            recipe.apply {
                Picasso.get().load(imageUrl).into(recipeDetailIv)

                recipeDetailArea.areaChip.text = area
                recipeDetailCategory.areaChip.text = category

                recipeDetailIngredientRv.layoutManager =
                    LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                recipeDetailIngredientRv.adapter = IngredientAdapter(ingredientsPar)

                recipeDetailInstructions.text = instructions
            }
        }
    }
}

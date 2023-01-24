package konsum.gandalf.mealmate.recipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentRecipeDetailBinding
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.ui.adapter.IngredientAdapter
import konsum.gandalf.mealmate.user.ui.viewmodels.RecipeDetailViewModel

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private lateinit var _binding: FragmentRecipeDetailBinding
    private val binding
        get() = _binding

    private val navArgs: RecipeDetailFragmentArgs by navArgs()
    private val viewModel: RecipeDetailViewModel by activityViewModels()

    private lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        recipe = navArgs.argRecipe
        viewModel.loadCurrentUser()

        fillContent(recipe.ingredients)
        initButtons()
        return binding.root
    }

    private fun fillContent(ingredientsPar: List<Ingredient>) {
        with(binding) {
            recipe.apply {
                Picasso.get().load(imageUrl).into(recipeDetailIv)

                recipeDetailCard.recipeDetailTitle.text = recipe.title
                recipe.owner?.username.let {
                    recipeDetailCard.recipeDetailOwner.text = it
                }

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

    private fun initButtons() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            with(binding.recipeDetailCard.recipeDetailInteraction) {
                cardInteractionEdit.setOnClickListener { editBtn ->
                    if (user != null) {
                        if (user.uid != recipe.owner?.uid) {
                            val alertDialog = AlertDialog.Builder(requireContext())
                            alertDialog.setTitle("Ownership difference")
                                .setMessage(
                                    "You are not the owner of this recipe, therefor you can not edit this element. " +
                                        "But you can edit a copy of it and use it as a blueprint. " +
                                        "Do you wish to proceed?"
                                )
                                .setIcon(R.drawable.ph_identification)
                                .setCancelable(false)
                                .setNegativeButton(
                                    "No"
                                ) { dialogInterface, _ ->
                                    dialogInterface.cancel()
                                }
                                .setPositiveButton(
                                    "Yes"
                                ) { _, _ ->
                                    val copyRecipe = recipe
                                    copyRecipe.owner = user
                                    val action =
                                        RecipeDetailFragmentDirections.actionRecipeDetailFragmentToRecipeAddFragment(
                                            copyRecipe,
                                            true
                                        )
                                    Navigation.findNavController(binding.root).navigate(action)
                                }

                            alertDialog.create().show()
                        } else if (user.uid == recipe.owner?.uid) {
                            val action =
                                RecipeDetailFragmentDirections.actionRecipeDetailFragmentToRecipeAddFragment(
                                    recipe,
                                    false
                                )
                            Navigation.findNavController(binding.root).navigate(action)
                        }
                    }
                }
            }
        }
    }
}

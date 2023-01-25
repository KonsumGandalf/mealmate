package konsum.gandalf.mealmate.user.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.databinding.CardRecipeOverviewBinding
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.user.ui.fragments.UserProfileFragmentDirections
import konsum.gandalf.mealmate.utils.models.DifRat

class RecipeUserAdapter(
    private var recipes: List<Recipe> = ArrayList<Recipe>(),
    private var difRats: List<DifRat> = ArrayList<DifRat>()
) : RecyclerView.Adapter<RecipeUserAdapter.RecipeAdapterHolder>() {

    inner class RecipeAdapterHolder(val binding: CardRecipeOverviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapterHolder {
        val binding = CardRecipeOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeAdapterHolder, position: Int) {
        with(holder) {
            with(recipes[position]) {
                binding.recipeNameCv.text = title
                Picasso.get().load(imageUrl).into(binding.cardRecipeIv)

                var difRat: DifRat? = null
                if (difRats.isNotEmpty() && position < difRats.size) {
                    binding.recipeRating.text = difRats[position].rating.toString()
                    binding.recipeDifficulty.text = difRats[position].difficulty.toString()
                    difRat = difRats[position]
                }

                binding.root.setOnClickListener {
                    val action = UserProfileFragmentDirections.actionUserProfileFragmentToRecipeDetailFragment(this, difRat)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}
